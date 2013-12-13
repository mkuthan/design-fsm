package design.fsm;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Order {

	private OrderStatus status = OrderStatus.NEW;

	private OrderIdentifier identifier;

	private OrderDetails details;

	private Set<AmendOrderLineCommand> orderLineAmendments = new HashSet<>();

	Order(Builder builder) {
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

	public Set<AmendOrderLineCommand> getOrderLineAmendments() {
		return Collections.unmodifiableSet(orderLineAmendments);
	}

	public void open() {
		status.open(this);
	}

	void doOpen() {
		status = OrderStatus.OPENED;
	}

	public void close() {
		status.close(this);
	}

	void doClose() {
		status = OrderStatus.CLOSED;
	}

	public void cancel() {
		status.cancel(this);
	}

	void doCancel() {
		status = OrderStatus.CANCELLED;
	}

	public void update(OrderDetails details) {
		status.update(this, details);
	}

	void doUpdate(OrderDetails details) {
		this.details = requireNonNull(details);
	}

	public void amendOrderLine(AmendOrderLineCommand command) {
		status.amendOrderLine(this, command);
	}

	void doAmendOrderLine(AmendOrderLineCommand command) {
		requireNonNull(command);
		
		if (command.isChanging()) {
			// TODO
		}
		
		if (command.isAdding()) {
			// TODO
		}
		
		if (command.isRemoving()) {
			// TODO
		}
		
		orderLineAmendments.add(command);
	}

	public static class Builder {

		private OrderIdentifier identifier;

		private OrderDetails details;

		public Builder withIdentifier(OrderIdentifier identifier) {
			this.identifier = identifier;
			return this;
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
