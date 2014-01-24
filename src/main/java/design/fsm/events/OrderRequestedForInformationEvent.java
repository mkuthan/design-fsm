package design.fsm.events;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;
import design.shared.User;

public class OrderRequestedForInformationEvent extends AbstractOrderEvent {

	private static final long serialVersionUID = 1L;

	private String request;

	public OrderRequestedForInformationEvent(Date createdAt, User createdBy, OrderIdentifier identifier,
			OrderStatus oldStatus, OrderStatus newStatus, String request) {
		super(createdAt, createdBy, identifier, oldStatus, newStatus);
		this.request = requireNonNull(request);
	}

	public String getRequest() {
		return request;
	}

}
