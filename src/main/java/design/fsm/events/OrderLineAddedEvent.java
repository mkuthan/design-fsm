package design.fsm.events;

import design.fsm.OrderIdentifier;
import design.fsm.OrderLine;
import design.fsm.OrderLineIdentifier;
import design.fsm.OrderStatus;

import static java.util.Objects.requireNonNull;

public class OrderLineAddedEvent extends AbstractOrderLineEvent {

    private static final long serialVersionUID = 1L;

    private OrderLine addedOrderLine;

    public OrderLineAddedEvent(OrderIdentifier identifier, OrderStatus status, OrderLineIdentifier orderLineIdentifier, OrderLine addedOrderLine) {
        super(identifier, status, orderLineIdentifier);
        this.addedOrderLine = requireNonNull(addedOrderLine);
    }

    public OrderLine getAddedOrderLine() {
        return addedOrderLine;
    }

}
