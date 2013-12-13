package design.fsm;

public class NewOrderState extends OrderStateAdapter implements OrderState {

	@Override
	public void open(Order order) {
		order.doOpen();
	}

	@Override
	public void update(Order order, OrderDetails details) {
		order.doUpdate(details);
	}

}
