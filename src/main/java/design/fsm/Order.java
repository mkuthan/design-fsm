package design.fsm;

import static java.util.Objects.requireNonNull;

public class Order {

	private OrderStatus status = OrderStatus.NEW;

	private OrderNumber number;

	private OrderDetails details;

	public Order(OrderNumber number, OrderDetails details) {
		this.number = requireNonNull(number);
		this.details = requireNonNull(details);
	}

	public OrderStatus getStatus() {
		return status;
	}

	public OrderNumber getNumber() {
		return number;
	}

	public OrderDetails getDetails() {
		return details;
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

}
