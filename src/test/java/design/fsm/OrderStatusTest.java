package design.fsm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.EnumSet;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import design.fsm.commands.AmendOrderLineCommand;

@Test
public class OrderStatusTest {

	private static final OrderDetails ANY_ORDER_DETAILS = Mockito.mock(OrderDetails.class);

	private static final AmendOrderLineCommand ANY_AMEND_ORDER_LINE_COMMAND = Mockito.mock(AmendOrderLineCommand.class);

	private static final String ANY_SUSPEND_REASON = "any suspend reason";

	private static final String ANY_CANCEL_REASON = "any cancel reason";

	private static final String ANY_REQUEST_FOR_INFORMATION = "any request for information";

	@Mock
	private Order order;

	@Test(dataProvider = "toOpen")
	public void shouldOpenOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canOpen()).isTrue();

		// when
		status.open(order);

		// then
		verify(order).doOpen();
	}

	@Test(dataProvider = "complementToOpen", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* open .*")
	public void shouldNotOpenOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canOpen()).isFalse();

		// when
		status.open(order);
	}

	@Test(dataProvider = "toClose")
	public void shouldCloseOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canClose()).isTrue();

		// when
		status.close(order);

		// then
		verify(order).doClose();
	}

	@Test(dataProvider = "complementToClose", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* close .*")
	public void shouldNotCloseOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canClose()).isFalse();

		// when
		status.close(order);
	}

	@Test(dataProvider = "toSuspend")
	public void shouldSuspendOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canSuspend()).isTrue();

		// when
		status.suspend(order, ANY_SUSPEND_REASON);

		// then
		verify(order).doSuspend(eq(ANY_SUSPEND_REASON));
	}

	@Test(dataProvider = "complementToSuspend", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* suspend .*")
	public void shouldNotSuspendOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canSuspend()).isFalse();

		// when
		status.suspend(order, ANY_SUSPEND_REASON);
	}

	@Test(dataProvider = "toResume")
	public void shouldResumeOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canResume()).isTrue();

		// when
		status.resume(order);

		// then
		verify(order).doResume();
	}

	@Test(dataProvider = "complementToResume", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* resume .*")
	public void shouldNotResumeOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canResume()).isFalse();

		// when
		status.resume(order);
	}

	@Test(dataProvider = "toCancel")
	public void shouldCancelOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canCancel()).isTrue();

		// when
		status.cancel(order, ANY_CANCEL_REASON);

		// then
		verify(order).doCancel(eq(ANY_CANCEL_REASON));
	}

	@Test(dataProvider = "complementToCancel", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* cancel .*")
	public void shouldNotCancelOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canCancel()).isFalse();

		// when
		status.cancel(order, ANY_CANCEL_REASON);
	}

	@Test(dataProvider = "toUpdate")
	public void shouldUpdateOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canUpdate()).isTrue();

		// when
		status.update(order, ANY_ORDER_DETAILS);

		// then
		verify(order).doUpdate(eq(ANY_ORDER_DETAILS));
	}

	@Test(dataProvider = "complementToUpdate", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* update .*")
	public void shouldNotUpdateOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canUpdate()).isFalse();

		// when
		status.update(order, ANY_ORDER_DETAILS);
	}

	@Test(dataProvider = "toRevert")
	public void shouldRevertOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canRevert()).isTrue();

		// when
		status.revert(order);

		// then
		verify(order).doRevert();
	}

	@Test(dataProvider = "complementToRevert", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* revert .*")
	public void shouldNotRevertOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canRevert()).isFalse();

		// when
		status.revert(order);
	}

	@Test(dataProvider = "toRequestForInformation")
	public void shouldRequestForInformationOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canRequestForInformation()).isTrue();

		// when
		status.requestForInformation(order, ANY_REQUEST_FOR_INFORMATION);

		// then
		verify(order).doRequestForInformation(eq(ANY_REQUEST_FOR_INFORMATION));
	}

	@Test(dataProvider = "complementToRequestForInformation", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* request for information .*")
	public void shouldNotRequestForInformationOrderWhenStatusIs(OrderStatus status) {
		assertThat(status.canRequestForInformation()).isFalse();

		// when
		status.requestForInformation(order, ANY_REQUEST_FOR_INFORMATION);
	}

	@Test(dataProvider = "toAmend")
	public void shouldAmendOrderLineWhenStatusIs(OrderStatus status) {
		assertThat(status.canAmendOrderLine()).isTrue();

		// when
		status.amendOrderLine(order, ANY_AMEND_ORDER_LINE_COMMAND);

		// then
		verify(order).doAmendOrderLine(eq(ANY_AMEND_ORDER_LINE_COMMAND));
	}

	@Test(dataProvider = "complementToAmend", expectedExceptions = IllegalOrderStateException.class, expectedExceptionsMessageRegExp = ".* amend order line .*")
	public void shouldNotAmendOrderLineWhenStatusIs(OrderStatus status) {
		assertThat(status.canAmendOrderLine()).isFalse();

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
		return new Object[][] { { OrderStatus.OPENED }, { OrderStatus.SUSPENDED } };
	}

	@DataProvider
	Object[][] complementToClose() {
		return complement(toClose());
	}

	@DataProvider
	Object[][] toSuspend() {
		return new Object[][] { { OrderStatus.OPENED } };
	}

	@DataProvider
	Object[][] complementToSuspend() {
		return complement(toSuspend());
	}

	@DataProvider
	Object[][] toResume() {
		return new Object[][] { { OrderStatus.SUSPENDED } };
	}

	@DataProvider
	Object[][] complementToResume() {
		return complement(toResume());
	}

	@DataProvider
	Object[][] toCancel() {
		return new Object[][] { { OrderStatus.OPENED }, { OrderStatus.SUSPENDED } };
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
	Object[][] toRevert() {
		return new Object[][] { { OrderStatus.OPENED } };
	}

	@DataProvider
	Object[][] complementToRevert() {
		return complement(toRevert());
	}

	@DataProvider
	Object[][] toRequestForInformation() {
		return new Object[][] { { OrderStatus.OPENED } };
	}

	@DataProvider
	Object[][] complementToRequestForInformation() {
		return complement(toRequestForInformation());
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
		MockitoAnnotations.initMocks(this);
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
