package design.fsm;

import org.testng.annotations.Test;

@Test
public class OrderTest {

	private Order.Builder orderBuilder;

	private Order order;

	public void shouldBeNew() {
		givenOrder();
	}

	private Order.Builder givenOrder() {
		orderBuilder = new Order.Builder();
		return orderBuilder;
	}

	private Order whenOrder() {
		order = orderBuilder.build();
		return order;
	}

	private OrderAssert thenOrder() {
		return new OrderAssert(order);
	}

}
