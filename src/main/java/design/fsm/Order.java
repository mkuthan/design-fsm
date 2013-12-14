package design.fsm;

import static java.util.Objects.requireNonNull;
import design.ddd.DomainEventPublisher;
import design.fsm.commands.AmendOrderLineCommand;
import design.fsm.events.OrderEventFactory;

public class Order {

	private DomainEventPublisher domainEventPublisher;

	private OrderEventFactory orderEventFactory;

	private OrderStatus status;

	private OrderIdentifier identifier;

	private OrderDetails details;

	Order(Builder builder) {
		this.status = requireNonNull(builder.status);
		this.identifier = requireNonNull(builder.identifier);
		this.details = requireNonNull(builder.details);
	}

	public OrderStatus getStatus() {
		return status;
	}

	public OrderIdentifier getIdentifier() {
		return identifier;
	}

	public OrderDetails getDetails() {
		return details;
	}

	public void open() {
		status.open(this);
	}

	void doOpen() {
		OrderStatus oldStatus = status;
		status = OrderStatus.OPENED;

		domainEventPublisher.publish(orderEventFactory.createOpenedEvent(identifier, oldStatus, status));
	}

	public void close() {
		status.close(this);
	}

	void doClose() {
		OrderStatus oldStatus = status;
		status = OrderStatus.CLOSED;

		domainEventPublisher.publish(orderEventFactory.createClosedEvent(identifier, oldStatus, status));
	}

	public void suspend(String reason) {
		status.suspend(this, reason);
	}

	void doSuspend(String reason) {
		requireNonNull(reason);

		OrderStatus oldStatus = status;
		status = OrderStatus.SUSPENDED;

		domainEventPublisher.publish(orderEventFactory.createSuspendedEvent(identifier, oldStatus, status, reason));
	}

	public void resume() {
		status.resume(this);
	}

	void doResume() {
		OrderStatus oldStatus = status;
		status = OrderStatus.OPENED;

		domainEventPublisher.publish(orderEventFactory.createResumedEvent(identifier, oldStatus, status));
	}

	public void cancel(String reason) {
		status.cancel(this, reason);
	}

	void doCancel(String reason) {
		requireNonNull(reason);

		OrderStatus oldStatus = status;
		status = OrderStatus.CANCELLED;

		domainEventPublisher.publish(orderEventFactory.createCancelledEvent(identifier, oldStatus, status, reason));
	}

	public void update(OrderDetails details) {
		status.update(this, details);
	}

	void doUpdate(OrderDetails details) {
		if (this.details.equals(details)) {
			return;
		}

		OrderDetails oldDetails = this.details;
		this.details = requireNonNull(details);

		domainEventPublisher.publish(orderEventFactory.createUpdateEven(identifier, status, oldDetails, details));
	}

	public void amendOrderLine(AmendOrderLineCommand command) {
		status.amendOrderLine(this, command);
	}

	void doAmendOrderLine(AmendOrderLineCommand command) {
		requireNonNull(command);

		if (command.isChanging()) {
			details.changeOrderLine(command.getIdentifier(), command.getNewOrderLine());
		}

		if (command.isAdding()) {
			details.addOrderLine(command.getNewOrderLine());
		}

		if (command.isRemoving()) {
			details.removeOrderLine(command.getIdentifier());
		}

		domainEventPublisher.publish(orderEventFactory.createOrderLineAmendedEvent(identifier, status,
				command.getIdentifier(), command.getNewOrderLine()));
	}

	public static class Builder {

		public OrderStatus status;

		private OrderIdentifier identifier;

		private OrderDetails details;

		public Builder withStatus(OrderStatus status) {
			this.status = status;
			return this;
		}

		public Builder newOrder() {
			return withStatus(OrderStatus.NEW);
		}

		public Builder openedOrder() {
			return withStatus(OrderStatus.OPENED);
		}

		public Builder suspendedOrder() {
			return withStatus(OrderStatus.SUSPENDED);
		}

		public Builder withIdentifier(OrderIdentifier identifier) {
			this.identifier = identifier;
			return this;
		}

		public Builder withIdentifier(String identifier) {
			return withIdentifier(new OrderIdentifier(identifier));
		}

		public Builder withDetails(OrderDetails details) {
			this.details = details;
			return this;
		}

		public Order build() {
			return new Order(this);
		}

	}

}
