package design.fsm;

import design.ddd.Event;
import design.fsm.commands.AmendOrderLineCommand;
import design.fsm.events.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Order {

    private OrderStatus status;

    private OrderIdentifier identifier;

    private OrderDetails details;

    private List<Event> pendingEvents = new ArrayList<>();

    public Order(OrderStatus status, OrderIdentifier identifier, OrderDetails details) {
        this.status = requireNonNull(status);
        this.identifier = requireNonNull(identifier);
        this.details = requireNonNull(details);

        pendingEvents.add(new OrderPlacedEvent(identifier, status, details));
    }

    public Iterable<Event> getPendingEvents() {
        return pendingEvents;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderIdentifier getIdentifier() {
        return identifier;
    }

    public OrderDetails getDetails() {
        return details;
    }

    public void open() {
        status.open(this);
    }

    void doOpen() {
        OrderStatus oldStatus = status;
        status = OrderStatus.OPENED;

        pendingEvents.add(new OrderOpenedEvent(identifier, oldStatus, status));
    }

    public void close() {
        status.close(this);
    }

    void doClose() {
        OrderStatus oldStatus = status;
        status = OrderStatus.CLOSED;

        pendingEvents.add(new OrderClosedEvent(identifier, oldStatus, status));
    }

    public void suspend(String reason) {
        status.suspend(this, reason);
    }

    void doSuspend(String reason) {
        requireNonNull(reason);

        OrderStatus oldStatus = status;
        status = OrderStatus.SUSPENDED;

        pendingEvents.add(new OrderSuspendedEvent(identifier, oldStatus, status, reason));
    }

    public void resume() {
        status.resume(this);
    }

    void doResume() {
        OrderStatus oldStatus = status;
        status = OrderStatus.OPENED;

        pendingEvents.add(new OrderResumedEvent(identifier, oldStatus, status));
    }

    public void cancel(String reason) {
        status.cancel(this, reason);
    }

    void doCancel(String reason) {
        requireNonNull(reason);

        OrderStatus oldStatus = status;
        status = OrderStatus.CANCELLED;

        pendingEvents.add(new OrderCancelledEvent(identifier, oldStatus, status, reason));
    }

    public void update(OrderDetails details) {
        status.update(this, details);
    }

    void doUpdate(OrderDetails details) {
        if (this.details.equals(details)) {
            return;
        }

        OrderDetails oldDetails = this.details;
        this.details = requireNonNull(details);

        pendingEvents.add(new OrderUpdatedEvent(identifier, status, oldDetails, details));
    }

    public void revert() {
        status.revert(this);
    }

    void doRevert() {
        OrderStatus oldStatus = status;
        status = OrderStatus.NEW;

        pendingEvents.add(new OrderRevertedEvent(identifier, oldStatus, status));
    }

    public void requestForInformation(String request) {
        status.requestForInformation(this, request);
    }

    void doRequestForInformation(String request) {
        requireNonNull(request);

        OrderStatus oldStatus = status;
        status = OrderStatus.NEW;

        pendingEvents.add(new OrderRequestedForInformationEvent(identifier, oldStatus, status, request));
    }

    public void amendOrderLine(AmendOrderLineCommand command) {
        status.amendOrderLine(this, command);
    }

    void doAmendOrderLine(AmendOrderLineCommand command) {
        requireNonNull(command);

        OrderLineIdentifier orderLineIdentifier = command.getIdentifier();
        OrderLine newOrderLine = command.getNewOrderLine();

        if (command.isChanging()) {
            OrderLine oldOrderLine = details.changeOrderLine(orderLineIdentifier, command.getNewOrderLine());
            pendingEvents.add(new OrderLineChangedEvent(identifier, status, orderLineIdentifier, oldOrderLine, newOrderLine));
        }

        if (command.isAdding()) {
            boolean added = details.addOrderLine(newOrderLine);

            if (added) {
                pendingEvents.add(new OrderLineAddedEvent(identifier, status, newOrderLine.getIdentifier(), newOrderLine));
            }
        }

        if (command.isRemoving()) {
            OrderLine oldOrderLine = details.removeOrderLine(orderLineIdentifier);
            pendingEvents.add(new OrderLineRemovedEvent(identifier, status, orderLineIdentifier, oldOrderLine));
        }

    }

}
