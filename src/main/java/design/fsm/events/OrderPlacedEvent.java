package design.fsm.events;

import design.fsm.OrderDetails;
import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;

import static java.util.Objects.requireNonNull;

public class OrderPlacedEvent extends AbstractOrderEvent {

    private static final long serialVersionUID = 1L;

    private OrderDetails details;

    public OrderPlacedEvent(OrderIdentifier identifier, OrderStatus status, OrderDetails details) {
        super(identifier, status, status);
        this.details = requireNonNull(details);
    }

    public OrderDetails getDetails() {
        return details;
    }

}
