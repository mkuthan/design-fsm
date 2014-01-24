package design.fsm;

import static java.util.Objects.requireNonNull;
import design.ddd.EventPublisher;
import design.fsm.commands.AmendOrderLineCommand;
import design.fsm.events.OrderEventFactory;

public class Order {

	private EventPublisher eventPublisher;

	private OrderEventFactory orderEventFactory;

	private OrderStatus status;

	private OrderIdentifier identifier;

	private OrderDetails details;

	public Order(OrderStatus status, OrderIdentifier identifier, OrderDetails details) {
		this.status = requireNonNull(status);
		this.identifier = requireNonNull(identifier);
		this.details = requireNonNull(details);
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

		eventPublisher.publish(orderEventFactory.createOpenedEvent(identifier, oldStatus, status));
	}

	public void close() {
		status.close(this);
	}

	void doClose() {
		OrderStatus oldStatus = status;
		status = OrderStatus.CLOSED;

		eventPublisher.publish(orderEventFactory.createClosedEvent(identifier, oldStatus, status));
	}

	public void suspend(String reason) {
		status.suspend(this, reason);
	}

	void doSuspend(String reason) {
		requireNonNull(reason);

		OrderStatus oldStatus = status;
		status = OrderStatus.SUSPENDED;

		eventPublisher.publish(orderEventFactory.createSuspendedEvent(identifier, oldStatus, status, reason));
	}

	public void resume() {
		status.resume(this);
	}

	void doResume() {
		OrderStatus oldStatus = status;
		status = OrderStatus.OPENED;

		eventPublisher.publish(orderEventFactory.createResumedEvent(identifier, oldStatus, status));
	}

	public void cancel(String reason) {
		status.cancel(this, reason);
	}

	void doCancel(String reason) {
		requireNonNull(reason);

		OrderStatus oldStatus = status;
		status = OrderStatus.CANCELLED;

		eventPublisher.publish(orderEventFactory.createCancelledEvent(identifier, oldStatus, status, reason));
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

		eventPublisher.publish(orderEventFactory.createUpdateEven(identifier, status, oldDetails, details));
	}

	public void revert() {
		status.revert(this);
	}

	void doRevert() {
		OrderStatus oldStatus = status;
		status = OrderStatus.NEW;

		eventPublisher.publish(orderEventFactory.createRevertedEvent(identifier, oldStatus, status));
	}

	public void requestForInformation(String request) {
		status.requestForInformation(this, request);
	}

	void doRequestForInformation(String request) {
		requireNonNull(request);

		OrderStatus oldStatus = status;
		status = OrderStatus.NEW;

		eventPublisher.publish(orderEventFactory.createRequestedForInformationEvent(identifier, oldStatus, status,
				request));
	}

	public void amendOrderLine(AmendOrderLineCommand command) {
		status.amendOrderLine(this, command);
	}

	void doAmendOrderLine(AmendOrderLineCommand command) {
		requireNonNull(command);

		if (command.isChanging()) {
			details.changeOrderLine(command.getIdentifier(), command.getNewOrderLine());
		}

		if (command.isAdding()) {
			details.addOrderLine(command.getNewOrderLine());
		}

		if (command.isRemoving()) {
			details.removeOrderLine(command.getIdentifier());
		}

		eventPublisher.publish(orderEventFactory.createOrderLineAmendedEvent(identifier, status,
				command.getIdentifier(), command.getNewOrderLine()));
	}

}
