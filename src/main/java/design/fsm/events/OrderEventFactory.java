package design.fsm.events;

import java.util.Date;

import design.fsm.OrderDetails;
import design.fsm.OrderIdentifier;
import design.fsm.OrderLine;
import design.fsm.OrderLineIdentifier;
import design.fsm.OrderStatus;
import design.shared.CurrentDateProvider;
import design.shared.CurrentUserProvider;
import design.shared.User;

public class OrderEventFactory {

	private CurrentDateProvider currentDateProvider;

	private CurrentUserProvider currentUserProvider;

	public OrderEventFactory(CurrentDateProvider currentDateProvider, CurrentUserProvider currentUserProvider) {
		this.currentDateProvider = currentDateProvider;
		this.currentUserProvider = currentUserProvider;
	}

	public OrderOpenedEvent createOpenedEvent(OrderIdentifier identifier, OrderStatus oldStatus, OrderStatus newStatus) {
		return new OrderOpenedEvent(currentDate(), currentUser(), identifier, oldStatus, newStatus);
	}

	public OrderClosedEvent createClosedEvent(OrderIdentifier identifier, OrderStatus oldStatus, OrderStatus newStatus) {
		return new OrderClosedEvent(currentDate(), currentUser(), identifier, oldStatus, newStatus);
	}

	public OrderSuspendedEvent createSuspendedEvent(OrderIdentifier identifier, OrderStatus oldStatus,
			OrderStatus newStatus, String reason) {
		return new OrderSuspendedEvent(currentDate(), currentUser(), identifier, oldStatus, newStatus, reason);
	}

	public OrderResumedEvent createResumedEvent(OrderIdentifier identifier, OrderStatus oldStatus, OrderStatus newStatus) {
		return new OrderResumedEvent(currentDate(), currentUser(), identifier, oldStatus, newStatus);
	}

	public OrderCancelledEvent createCancelledEvent(OrderIdentifier identifier, OrderStatus oldStatus,
			OrderStatus newStatus, String reason) {
		return new OrderCancelledEvent(currentDate(), currentUser(), identifier, oldStatus, newStatus, reason);
	}

	public OrderUpdatedEvent createUpdateEven(OrderIdentifier identifier, OrderStatus status, OrderDetails oldDetails,
			OrderDetails newDetails) {
		return new OrderUpdatedEvent(currentDate(), currentUser(), identifier, status, oldDetails, newDetails);
	}

	public OrderLineAmendedEvent createOrderLineAmendedEvent(OrderIdentifier identifier, OrderStatus status,
			OrderLineIdentifier orderLineIdentifier, OrderLine orderLine) {
		return new OrderLineAmendedEvent(currentDate(), currentUser(), identifier, status, orderLineIdentifier,
				orderLine);
	}

	private Date currentDate() {
		return currentDateProvider.currentDate();
	}

	private User currentUser() {
		return currentUserProvider.currentUser();
	}
}
