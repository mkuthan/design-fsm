package design.fsm;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.EnumSet;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class OrderStateTest {

	private static final OrderDetails ANY_ORDER_DETAILS = new OrderDetails.Builder().build();

	private static final AmendOrderLineCommand ANY_AMEND_ORDER_LINE_COMMAND = new AmendOrderLineCommand(null, null);

	private Order order;

	@Test(dataProvider = "toOpen")
	public void shouldOpenOrderWhenStatusIs(OrderStatus status) {
		// when
		status.open(order);

		// then
		verify(order).doOpen();
	}

	@Test(dataProvider = "complementToOpen", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* open .*")
	public void couldNotOpenOrderWhenStatusIs(OrderStatus status) {
		// when
		status.open(order);
	}

	@Test(dataProvider = "toClose")
	public void shouldCloseOrderWhenStatusIs(OrderStatus status) {
		// when
		status.close(order);

		// then
		verify(order).doClose();
	}

	@Test(dataProvider = "complementToClose", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* close .*")
	public void couldNotCloseOrderWhenStatusIs(OrderStatus status) {
		// when
		status.close(order);
	}

	@Test(dataProvider = "toCancel")
	public void shouldCancelOrderWhenStatusIs(OrderStatus status) {
		// when
		status.cancel(order);

		// then
		verify(order).doCancel();
	}

	@Test(dataProvider = "complementToCancel", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* cancel .*")
	public void couldNotCancelOrderWhenStatusIs(OrderStatus status) {
		// when
		status.cancel(order);
	}

	@Test(dataProvider = "toUpdate")
	public void shouldUpdateOrderWhenStatusIs(OrderStatus status) {
		// when
		status.update(order, ANY_ORDER_DETAILS);

		// then
		verify(order).doUpdate(eq(ANY_ORDER_DETAILS));
	}

	@Test(dataProvider = "complementToUpdate", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* update .*")
	public void couldNotUpdateOrderWhenStatusIs(OrderStatus status) {
		// when
		status.update(order, ANY_ORDER_DETAILS);
	}

	@Test(dataProvider = "toAmend")
	public void shouldAmendOrderLineWhenStatusIs(OrderStatus status) {
		// when
		status.amendOrderLine(order, ANY_AMEND_ORDER_LINE_COMMAND);

		// then
		verify(order).doAmendOrderLine(eq(ANY_AMEND_ORDER_LINE_COMMAND));
	}

	@Test(dataProvider = "complementToAmend", expectedExceptions = OrderIllegalStateException.class, expectedExceptionsMessageRegExp = ".* amend order line .*")
	public void couldNotAmendOrderLineWhenStatusIs(OrderStatus status) {
		// when
		status.amendOrderLine(order, ANY_AMEND_ORDER_LINE_COMMAND);
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

	@DataProvider
	Object[][] toAmend() {
		return new Object[][] { { OrderStatus.OPENED } };
	}

	@DataProvider
	Object[][] complementToAmend() {
		return complement(toAmend());
	}

	@BeforeMethod
	void givenOrder() {
		order = Mockito.mock(Order.class);
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
