package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;

public class OrderStateAdapter implements OrderState {

	@Override
	public void open(Order order) {
		throw new IllegalOrderStateException(order, "open");
	}

	@Override
	public void close(Order order) {
		throw new IllegalOrderStateException(order, "close");
	}

	@Override
	public void suspend(Order order, String reason) {
		throw new IllegalOrderStateException(order, "suspend");
	}

	@Override
	public void resume(Order order) {
		throw new IllegalOrderStateException(order, "resume");
	}

	@Override
	public void cancel(Order order, String reason) {
		throw new IllegalOrderStateException(order, "cancel");
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	@Override
	public void update(Order order, OrderDetails details) {
		throw new IllegalOrderStateException(order, "update");
	}

	@Override
	public boolean canAmendOrderLine() {
		return false;
	}

	@Override
	public void amendOrderLine(Order order, AmendOrderLineCommand command) {
		throw new IllegalOrderStateException(order, "amend order line");
	}

}
