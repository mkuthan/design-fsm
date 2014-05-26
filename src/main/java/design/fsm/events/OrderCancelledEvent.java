package design.fsm.events;

import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;

import static java.util.Objects.requireNonNull;

public class OrderCancelledEvent extends AbstractOrderEvent {

    private static final long serialVersionUID = 1L;

    private String reason;

    public OrderCancelledEvent(OrderIdentifier identifier, OrderStatus oldStatus, OrderStatus newStatus, String reason) {
        super(identifier, oldStatus, newStatus);
        this.reason = requireNonNull(reason);
    }

    public String getReason() {
        return reason;
    }

}
