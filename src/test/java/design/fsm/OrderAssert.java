package design.fsm;

import static org.fest.assertions.api.Assertions.assertThat;

import org.fest.assertions.api.AbstractAssert;

public class OrderAssert extends AbstractAssert<OrderAssert, Order> {

	public OrderAssert(Order actual) {
		super(actual, OrderAssert.class);
	}
	
	public OrderAssert isNew() {
		assertThat(actual.getStatus()).isEqualTo(OrderStatus.NEW);
		return this;
	}

	public OrderAssert isOpened() {
		assertThat(actual.getStatus()).isEqualTo(OrderStatus.OPENED);
		return this;
	}

	public OrderAssert isClosed() {
		assertThat(actual.getStatus()).isEqualTo(OrderStatus.CLOSED);
		return this;
	}

	public OrderAssert isCancelled() {
		assertThat(actual.getStatus()).isEqualTo(OrderStatus.CANCELLED);
		return this;
	}

	public OrderAssert hasDetails(OrderDetails details) {
		assertThat(actual.getDetails()).isEqualTo(details);
		return this;
	}

}
