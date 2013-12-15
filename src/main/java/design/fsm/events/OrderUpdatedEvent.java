package design.fsm.events;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import design.fsm.OrderDetails;
import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;
import design.shared.User;

public class OrderUpdatedEvent extends AbstractOrderEvent {

	private static final long serialVersionUID = 1L;

	private OrderDetails oldDetails;

	private OrderDetails newDetails;

	public OrderUpdatedEvent(Date createdAt, User createdBy, OrderIdentifier identifier, OrderStatus status,
			OrderDetails oldDetails, OrderDetails newDetails) {
		super(createdAt, createdBy, identifier, status);
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
