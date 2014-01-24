package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;

public class OpenedOrderState extends OrderStateAdapter implements OrderState {

	@Override
	public void close(Order order) {
		order.doClose();
	}

	@Override
	public void suspend(Order order, String reason) {
		order.doSuspend(reason);
	}

	@Override
	public void cancel(Order order, String reason) {
		order.doCancel(reason);
	}

	@Override
	public boolean canAmendOrderLine() {
		return true;
	}

	@Override
	public void amendOrderLine(Order order, AmendOrderLineCommand command) {
		order.doAmendOrderLine(command);
	}

}
