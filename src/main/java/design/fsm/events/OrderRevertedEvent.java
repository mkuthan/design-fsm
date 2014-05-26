package design.fsm.events;

import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;

public class OrderRevertedEvent extends AbstractOrderEvent {

    private static final long serialVersionUID = 1L;

    public OrderRevertedEvent(OrderIdentifier identifier, OrderStatus oldStatus, OrderStatus newStatus) {
        super(identifier, oldStatus, newStatus);
    }

}
