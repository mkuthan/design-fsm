package design.fsm.events;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;
import design.shared.User;

public class OrderSuspendedEvent extends AbstractOrderEvent {

	private static final long serialVersionUID = 1L;

	private String reason;

	public OrderSuspendedEvent(Date createdAt, User createdBy, OrderIdentifier identifier, OrderStatus oldStatus,
			OrderStatus newStatus, String reason) {
		super(createdAt, createdBy, identifier, oldStatus, newStatus);
		this.reason = requireNonNull(reason);
	}

	public String getReason() {
		return reason;
	}

}
