package design.fsm.events;

import design.fsm.OrderIdentifier;
import design.fsm.OrderLine;
import design.fsm.OrderLineIdentifier;
import design.fsm.OrderStatus;

import static java.util.Objects.requireNonNull;

public class OrderLineChangedEvent extends AbstractOrderLineEvent {

    private static final long serialVersionUID = 1L;

    private OrderLine oldOrderLine;

    private OrderLine newOrderLine;

    public OrderLineChangedEvent(OrderIdentifier identifier, OrderStatus status, OrderLineIdentifier orderLineIdentifier, OrderLine oldOrderLine, OrderLine newOrderLine) {
        super(identifier, status, orderLineIdentifier);
        this.oldOrderLine = requireNonNull(oldOrderLine);
        this.newOrderLine = requireNonNull(newOrderLine);
    }

    public OrderLine getOldOrderLine() {
        return oldOrderLine;
    }

    public OrderLine getNewOrderLine() {
        return newOrderLine;
    }

}
