package design.fsm;

public class OpenedOrderState extends OrderStateAdapter implements OrderState {

	@Override
	public void close(Order order) {
		order.doClose();
	}

	@Override
	public void cancel(Order order) {
		order.doCancel();
	}

	@Override
	public void amendOrderLine(Order order, AmendOrderLineCommand command) {
		order.doAmendOrderLine(command);
	}

}
