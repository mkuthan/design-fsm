package design.fsm.events;

import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;

public class OrderClosedEvent extends AbstractOrderEvent {

    private static final long serialVersionUID = 1L;

    public OrderClosedEvent(OrderIdentifier identifier, OrderStatus oldStatus, OrderStatus newStatus) {
        super(identifier, oldStatus, newStatus);
    }

}
