package design.fsm.events;

import design.fsm.OrderDetails;
import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;

import static java.util.Objects.requireNonNull;

public class OrderUpdatedEvent extends AbstractOrderEvent {

    private static final long serialVersionUID = 1L;

    private OrderDetails oldDetails;

    private OrderDetails newDetails;

    public OrderUpdatedEvent(OrderIdentifier identifier, OrderStatus status, OrderDetails oldDetails, OrderDetails newDetails) {
        super(identifier, status);
        this.oldDetails = requireNonNull(newDetails);
        this.newDetails = requireNonNull(newDetails);
    }

    public OrderDetails getOldDetails() {
        return oldDetails;
    }

    public OrderDetails getNewDetails() {
        return newDetails;
    }

}
