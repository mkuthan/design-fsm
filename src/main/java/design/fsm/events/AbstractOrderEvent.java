package design.fsm.events;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Date;

import design.ddd.Event;
import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;
import design.shared.User;

@Event
public abstract class AbstractOrderEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date createdAt;

	private User createdBy;

	private OrderIdentifier identifier;

	private OrderStatus oldStatus;

	private OrderStatus newStatus;

	protected AbstractOrderEvent(Date createdAt, User createdBy, OrderIdentifier identifier, OrderStatus status) {
		this(createdAt, createdBy, identifier, status, status);
	}

	protected AbstractOrderEvent(Date createdAt, User createdBy, OrderIdentifier identifier, OrderStatus oldStatus,
			OrderStatus newStatus) {
		this.createdAt = requireNonNull(createdAt);
		this.createdBy = requireNonNull(createdBy);
		this.identifier = requireNonNull(identifier);
		this.oldStatus = requireNonNull(oldStatus);
		this.newStatus = requireNonNull(newStatus);
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public OrderIdentifier getIdentifier() {
		return identifier;
	}

	public OrderStatus getOldStatus() {
		return oldStatus;
	}

	public OrderStatus getNewStatus() {
		return newStatus;
	}

}