package design.fsm.events;

import design.fsm.OrderIdentifier;
import design.fsm.OrderLine;
import design.fsm.OrderLineIdentifier;
import design.fsm.OrderStatus;

import static java.util.Objects.requireNonNull;

public class OrderLineRemovedEvent extends AbstractOrderLineEvent {

    private static final long serialVersionUID = 1L;

    private OrderLine removedOrderLine;

    public OrderLineRemovedEvent(OrderIdentifier identifier, OrderStatus status, OrderLineIdentifier orderLineIdentifier, OrderLine removedOrderLine) {
        super(identifier, status, orderLineIdentifier);
        this.removedOrderLine = requireNonNull(removedOrderLine);
    }

    public OrderLine getRemovedOrderLine() {
        return removedOrderLine;
    }

}
