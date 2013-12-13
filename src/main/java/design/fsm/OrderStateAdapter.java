package design.fsm;

public class OrderStateAdapter implements OrderState {

	@Override
	public void open(Order order) {
		throw new OrderIllegalStateException(order, "open");
	}

	@Override
	public void close(Order order) {
		throw new OrderIllegalStateException(order, "close");
	}

	@Override
	public void cancel(Order order) {
		throw new OrderIllegalStateException(order, "cancel");
	}

	@Override
	public void update(Order order, OrderDetails details) {
		throw new OrderIllegalStateException(order, "update");
	}

	@Override
	public void amendOrderLine(Order order, AmendOrderLineCommand command) {
		throw new OrderIllegalStateException(order, "amend order line");
	}

}
