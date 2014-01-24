package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;

public enum OrderStatus implements OrderState {

	NEW(new NewOrderState()), //
	OPENED(new OpenedOrderState()), //
	SUSPENDED(new SuspendedOrderState()), //
	CLOSED(new ClosedOrderState()), //
	CANCELLED(new CancelledOrderState());

	private OrderState state;

	private OrderStatus(OrderState state) {
		this.state = state;
	}

	@Override
	public void open(Order order) {
		state.open(order);
	}

	@Override
	public void close(Order order) {
		state.close(order);
	}

	@Override
	public void suspend(Order order, String reason) {
		state.suspend(order, reason);
	}

	@Override
	public void resume(Order order) {
		state.resume(order);
	}

	@Override
	public void cancel(Order order, String reason) {
		state.cancel(order, reason);
	}

	@Override
	public boolean canUpdate() {
		return state.canUpdate();
	}

	@Override
	public void update(Order order, OrderDetails details) {
		state.update(order, details);
	}

	@Override
	public boolean canAmendOrderLine() {
		return state.canAmendOrderLine();
	}

	@Override
	public void amendOrderLine(Order order, AmendOrderLineCommand command) {
		state.amendOrderLine(order, command);
	}

}
