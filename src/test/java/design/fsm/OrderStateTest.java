package design.fsm;

import java.util.Arrays;
import java.util.EnumSet;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class OrderStateTest {

	private Order order;

	@Test(dataProvider = "toOpen")
	public void shouldOpenOrderWhenStatusIs(OrderStatus status) {
		givenOrder();

		whenOpenOrder(status);

		thenOrder().isOpened();
	}

	@Test(dataProvider = "complementToOpen", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* open .*")
	public void couldNotOpenOrderWhenStatusIs(OrderStatus status) {
		givenOrder();

		whenOpenOrder(status);
	}

	@Test(dataProvider = "toClose")
	public void shouldCloseOrderWhenStatusIs(OrderStatus status) {
		givenOrder();

		whenCloseOrder(status);

		thenOrder().isClosed();
	}

	@Test(dataProvider = "complementToClose", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* close .*")
	public void couldNotCloseOrderWhenStatusIs(OrderStatus status) {
		givenOrder();

		whenCloseOrder(status);
	}

	@Test(dataProvider = "toCancel")
	public void shouldCancelOrderWhenStatusIs(OrderStatus status) {
		givenOrder();

		whenCancelOrder(status);

		thenOrder().isCancelled();
	}

	@Test(dataProvider = "complementToCancel", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* cancel .*")
	public void couldNotCancelOrderWhenStatusIs(OrderStatus status) {
		givenOrder();

		whenCancelOrder(status);
	}

	@Test(dataProvider = "toUpdate")
	public void shouldUpdateOrderWhenStatusIs(OrderStatus status) {
		OrderDetails details = new OrderDetails();
		
		givenOrder();

		whenUpdateOrder(status, details);

		thenOrder().isNew().hasDetails(details);
	}

	@Test(dataProvider = "complementToUpdate", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* update .*")
	public void couldNotUpdateOrderWhenStatusIs(OrderStatus status) {
		givenOrder();

		whenUpdateOrder(status, new OrderDetails());
	}

	@DataProvider
	Object[][] toOpen() {
		return new Object[][] { { OrderStatus.NEW } };
	}

	@DataProvider
	Object[][] complementToOpen() {
		return complement(toOpen());
	}

	@DataProvider
	Object[][] toClose() {
		return new Object[][] { { OrderStatus.OPENED } };
	}

	@DataProvider
	Object[][] complementToClose() {
		return complement(toClose());
	}

	@DataProvider
	Object[][] toCancel() {
		return new Object[][] { { OrderStatus.OPENED } };
	}

	@DataProvider
	Object[][] complementToCancel() {
		return complement(toCancel());
	}

	@DataProvider
	Object[][] toUpdate() {
		return new Object[][] { { OrderStatus.NEW } };
	}

	@DataProvider
	Object[][] complementToUpdate() {
		return complement(toUpdate());
	}

	private Order givenOrder() {
		this.order = new Order(new OrderNumber("any number"), new OrderDetails());
		return this.order;
	}

	private void whenOpenOrder(OrderStatus status) {
		status.open(this.order);
	}

	private void whenCloseOrder(OrderStatus status) {
		status.close(this.order);
	}

	private void whenCancelOrder(OrderStatus status) {
		status.cancel(this.order);
	}

	private void whenUpdateOrder(OrderStatus status, OrderDetails details) {
		status.update(this.order, details);
	}

	private OrderAssert thenOrder() {
		return new OrderAssert(this.order);
	}

	private Object[][] complement(Object[][] input) {
		// extract origin statuses from first column
		OrderStatus[] origin = new OrderStatus[input.length];
		for (int i = 0; i < origin.length; i++) {
			origin[i] = (OrderStatus) input[i][0];
		}

		// calculate complemented statuses
		OrderStatus[] complemented = EnumSet.complementOf(EnumSet.copyOf(Arrays.asList(origin))).toArray(
				new OrderStatus[0]);

		// build output for complemented statuses
		OrderStatus[][] output = new OrderStatus[complemented.length][];
		for (int i = 0; i < output.length; i++) {
			output[i] = new OrderStatus[] { complemented[i] };
		}
		return output;
	}

}
