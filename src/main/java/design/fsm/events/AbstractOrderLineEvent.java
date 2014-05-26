package design.fsm.events;

import design.fsm.OrderIdentifier;
import design.fsm.OrderLine;
import design.fsm.OrderLineIdentifier;
import design.fsm.OrderStatus;

import static java.util.Objects.requireNonNull;

public abstract class AbstractOrderLineEvent extends AbstractOrderEvent {

    private static final long serialVersionUID = 1L;

    private OrderLineIdentifier orderLineIdentifier;

    public AbstractOrderLineEvent(OrderIdentifier identifier, OrderStatus status, OrderLineIdentifier orderLineIdentifier) {
        super(identifier, status);
        this.orderLineIdentifier = requireNonNull(orderLineIdentifier);
    }

    public OrderLineIdentifier getOrderLineIdentifier() {
        return orderLineIdentifier;
    }

}
