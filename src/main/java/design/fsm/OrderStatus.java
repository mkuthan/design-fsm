package design.fsm;

public enum OrderStatus implements OrderState {

	NEW(new NewOrderState()), //
	OPENED(new OpenedOrderState()), //
	CLOSED(new ClosedOrderState()), //
	CANCELLED(new CancelledOrderState());

	private OrderState state;

	private OrderStatus(OrderState state) {
		this.state = state;
	}

	@Override
	public void open(Order order) {
		this.state.open(order);
	}

	@Override
	public void close(Order order) {
		this.state.close(order);
	}

	@Override
	public void cancel(Order order) {
		this.state.cancel(order);

	}

	@Override
	public void update(Order order, OrderDetails details) {
		this.state.update(order, details);
	}

}
