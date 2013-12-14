package design.fsm;

import org.testng.annotations.Test;

@Test
public class OrderTest {

	private Order order;

	private Order.Builder orderBuilder;

	private OrderDetails.Builder orderDetailsBuilder = new OrderDetails.Builder();

	private OrderLine.Builder orderLineBuilder = new OrderLine.Builder();

	public void shouldBeNew() {
		givenOrder().newOrder();

		whenOrder();

		thenOrder().isNew();
	}

	public void shouldBeOpened() {
		givenOrder().newOrder();

		whenOrder().open();

		thenOrder().isOpened();
	}

	public void shouldBeClosed() {
		givenOrder().openedOrder();

		whenOrder().close();

		thenOrder().isClosed();
	}

	public void shouldBeSuspended() {
		givenOrder().openedOrder();

		String message = "any message";
		whenOrder().suspend(message);

		thenOrder().isSuspended();
	}

	public void shouldBeCancelled() {
		givenOrder().openedOrder();

		String message = "any message";
		whenOrder().cancel(message);

		thenOrder().isCancelled();
	}

	public void shouldBeUpdated() {
		givenOrder().newOrder();

		OrderDetails newDetails = orderDetailsBuilder.build();
		whenOrder().update(newDetails);

		thenOrder().hasDetails(newDetails);
	}

	public void shouldChangeOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("changed line");
		givenOrder().openedOrder().withDetails(orderDetailsBuilder.withOrderLine(orderLineIdentifier).build());

		OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
		AmendOrderLineCommand changeOrderLineCommand = AmendOrderLineCommand.changeOrderLineCommand(
				orderLineIdentifier, orderLineBuilder.withIdentifier(newOrderLineIdentifier).build());
		whenOrder().amendOrderLine(changeOrderLineCommand);

		thenOrder().doesNotContainOrderLine(orderLineIdentifier) //
				.containsOrderLine(newOrderLineIdentifier) //
				.hasOrderLineAmendment(changeOrderLineCommand);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldChangeFailForNotExistingOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("changed line");
		givenOrder().openedOrder();

		OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
		AmendOrderLineCommand changeOrderLineCommand = AmendOrderLineCommand.changeOrderLineCommand(
				orderLineIdentifier, orderLineBuilder.withIdentifier(newOrderLineIdentifier).build());
		whenOrder().amendOrderLine(changeOrderLineCommand);
	}

	public void shouldAddOrderLine() {
		givenOrder().openedOrder();

		OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
		AmendOrderLineCommand addOrderLineCommand = AmendOrderLineCommand.addOrderLineCommand(orderLineBuilder
				.withIdentifier(newOrderLineIdentifier).build());
		whenOrder().amendOrderLine(addOrderLineCommand);

		thenOrder().containsOrderLine(newOrderLineIdentifier) //
				.hasOrderLineAmendment(addOrderLineCommand);
	}

	public void shouldRemoveOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("removed line");
		givenOrder().openedOrder().withDetails(orderDetailsBuilder.withOrderLine(orderLineIdentifier).build());

		AmendOrderLineCommand removeOrderLineCommand = AmendOrderLineCommand
				.removeOrderLineCommand(orderLineIdentifier);
		whenOrder().amendOrderLine(removeOrderLineCommand);

		thenOrder().doesNotContainOrderLine(orderLineIdentifier) //
				.hasOrderLineAmendment(removeOrderLineCommand);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldRemoveFailForNotExistingOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("removed line");
		givenOrder().openedOrder();

		AmendOrderLineCommand removeOrderLineCommand = AmendOrderLineCommand
				.removeOrderLineCommand(orderLineIdentifier);
		whenOrder().amendOrderLine(removeOrderLineCommand);
	}

	private Order.Builder givenOrder() {
		orderBuilder = new Order.Builder().withIdentifier(new OrderIdentifier("any identifier")).withDetails(
				orderDetailsBuilder.build());
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
