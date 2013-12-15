package design.fsm.events;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import design.fsm.OrderIdentifier;
import design.fsm.OrderLine;
import design.fsm.OrderLineIdentifier;
import design.fsm.OrderStatus;
import design.shared.User;

public class OrderLineAmendedEvent extends AbstractOrderEvent {

	private static final long serialVersionUID = 1L;

	private OrderLineIdentifier orderLineIdentifier;

	private OrderLine newOrderLine;

	public OrderLineAmendedEvent(Date createdAt, User createdBy, OrderIdentifier identifier, OrderStatus status,
			OrderLineIdentifier orderLineIdentifier, OrderLine orderLine) {
		super(createdAt, createdBy, identifier, status);
		this.orderLineIdentifier = requireNonNull(orderLineIdentifier);
		this.newOrderLine = requireNonNull(newOrderLine);
	}

	public OrderLineIdentifier getOrderLineIdentifier() {
		return orderLineIdentifier;
	}

	public OrderLine getNewOrderLine() {
		return newOrderLine;
	}

}
