package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;

public enum OrderStatus implements OrderState {

	// @formatter:off
	NEW(new NewOrderState()), 
	OPENED(new OpenedOrderState()),
	SUSPENDED(new SuspendedOrderState()),
	CLOSED(new ClosedOrderState()),
	CANCELLED(new CancelledOrderState());
	// @formatter:on

	private OrderState state;

	private OrderStatus(OrderState state) {
		this.state = state;
	}

	@Override
	public boolean canOpen() {
		return state.canOpen();
	}

	@Override
	public void open(Order order) {
		state.open(order);
	}

	@Override
	public boolean canClose() {
		return state.canClose();
	}

	@Override
	public void close(Order order) {
		state.close(order);
	}

	@Override
	public boolean canSuspend() {
		return state.canSuspend();
	}

	@Override
	public void suspend(Order order, String reason) {
		state.suspend(order, reason);
	}

	@Override
	public boolean canResume() {
		return state.canResume();
	}

	@Override
	public void resume(Order order) {
		state.resume(order);
	}

	@Override
	public boolean canCancel() {
		return state.canCancel();
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
	public boolean canRevert() {
		return state.canRevert();
	}

	@Override
	public void revert(Order order) {
		state.revert(order);
	}

	@Override
	public boolean canRequestForInformation() {
		return state.canRequestForInformation();
	}

	@Override
	public void requestForInformation(Order order, String request) {
		state.requestForInformation(order, request);
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
