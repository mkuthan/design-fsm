package design.fsm;

public interface OrderState {

	void open(Order order);

	void close(Order order);

	void cancel(Order order);

	void update(Order order, OrderDetails details);

}
