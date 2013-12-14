package design.fsm.events;

import java.util.Date;

import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;
import design.shared.User;


public class OrderOpenedEvent extends AbstractOrderEvent {

	public OrderOpenedEvent(Date createdAt, User createdBy, OrderIdentifier identifier, OrderStatus oldStatus,
			OrderStatus newStatus) {
		super(createdAt, createdBy, identifier, oldStatus, newStatus);
	}

}
