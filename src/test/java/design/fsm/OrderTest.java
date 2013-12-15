package design.fsm;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import design.ddd.EventPublisher;
import design.ddd.DomainEventPublisherAssert;
import design.fsm.commands.AmendOrderLineCommand;
import design.fsm.events.OrderCancelledEvent;
import design.fsm.events.OrderClosedEvent;
import design.fsm.events.OrderEventFactory;
import design.fsm.events.OrderLineAmendedEvent;
import design.fsm.events.OrderOpenedEvent;
import design.fsm.events.OrderResumedEvent;
import design.fsm.events.OrderSuspendedEvent;
import design.fsm.events.OrderUpdatedEvent;

@Test
public class OrderTest {

	@Mock
	private EventPublisher eventPublisher;

	@Mock(answer = Answers.RETURNS_SMART_NULLS)
	private OrderEventFactory orderEventFactory;

	@InjectMocks
	private Order order;

	private Order.Builder orderBuilder;

	public void shouldBeNew() {
		givenOrder().newOrder();

		whenOrder();

		thenOrder().isNew();
	}

	public void shouldBeOpened() {
		givenOrder().newOrder();

		whenOrder().open();

		thenOrder().isOpened();
		thenDomainEventPublisher().published(OrderOpenedEvent.class);
	}

	public void shouldBeClosed() {
		givenOrder().openedOrder();

		whenOrder().close();

		thenOrder().isClosed();
		thenDomainEventPublisher().published(OrderClosedEvent.class);
	}

	public void shouldBeSuspended() {
		givenOrder().openedOrder();

		String message = "any message";
		whenOrder().suspend(message);

		thenOrder().isSuspended();
		thenDomainEventPublisher().published(OrderSuspendedEvent.class);
	}

	public void shouldBeResumed() {
		givenOrder().suspendedOrder();

		whenOrder().resume();

		thenOrder().isOpened();
		thenDomainEventPublisher().published(OrderResumedEvent.class);
	}

	public void shouldBeCancelled() {
		givenOrder().openedOrder();

		String message = "any message";
		whenOrder().cancel(message);

		thenOrder().isCancelled();
		thenDomainEventPublisher().published(OrderCancelledEvent.class);
	}

	public void shouldBeUpdated() {
		givenOrder().newOrder();

		OrderDetails newDetails = givenOrderDetails().withOrderLine(
				givenOrderLine().withIdentifier("new order line").build()).build();
		whenOrder().update(newDetails);

		thenOrder().hasDetails(newDetails);
		thenDomainEventPublisher().published(OrderUpdatedEvent.class);
	}

	public void shouldNotBeUpdatedWhenEqualDetails() {
		givenOrder().newOrder();

		OrderDetails newDetails = givenOrderDetails().build();
		whenOrder().update(newDetails);

		thenOrder().hasDetails(newDetails);
		thenDomainEventPublisher().didNotPublish(OrderUpdatedEvent.class);
	}

	public void shouldChangeOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("changed line");
		givenOrder().openedOrder()
				.withDetails(
						givenOrderDetails().withOrderLine(givenOrderLine().withIdentifier(orderLineIdentifier).build())
								.build());

		OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
		whenOrder().amendOrderLine(
				AmendOrderLineCommand.changeOrderLineCommand(orderLineIdentifier,
						givenOrderLine().withIdentifier(newOrderLineIdentifier).build()));

		thenOrder().doesNotContainOrderLine(orderLineIdentifier).containsOrderLine(newOrderLineIdentifier);
		thenDomainEventPublisher().published(OrderLineAmendedEvent.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldChangeFailForNotExistingOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("changed line");
		givenOrder().openedOrder();

		OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
		whenOrder().amendOrderLine(
				AmendOrderLineCommand.changeOrderLineCommand(orderLineIdentifier,
						givenOrderLine().withIdentifier(newOrderLineIdentifier).build()));
	}

	public void shouldAddOrderLine() {
		givenOrder().openedOrder();

		OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
		whenOrder().amendOrderLine(
				AmendOrderLineCommand.addOrderLineCommand(givenOrderLine().withIdentifier(newOrderLineIdentifier)
						.build()));

		thenOrder().containsOrderLine(newOrderLineIdentifier);
		thenDomainEventPublisher().published(OrderLineAmendedEvent.class);
	}

	public void shouldRemoveOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("removed line");
		givenOrder().openedOrder()
				.withDetails(
						givenOrderDetails().withOrderLine(givenOrderLine().withIdentifier(orderLineIdentifier).build())
								.build());

		whenOrder().amendOrderLine(AmendOrderLineCommand.removeOrderLineCommand(orderLineIdentifier));

		thenOrder().doesNotContainOrderLine(orderLineIdentifier);
		thenDomainEventPublisher().published(OrderLineAmendedEvent.class);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void shouldRemoveFailForNotExistingOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("removed line");
		givenOrder().openedOrder();

		whenOrder().amendOrderLine(AmendOrderLineCommand.removeOrderLineCommand(orderLineIdentifier));
	}

	private Order.Builder givenOrder() {
		orderBuilder = new Order.Builder().withIdentifier("any order").withDetails(new OrderDetails.Builder().build());
		return orderBuilder;
	}

	private OrderDetails.Builder givenOrderDetails() {
		return new OrderDetails.Builder();
	}

	private OrderLine.Builder givenOrderLine() {
		return new OrderLine.Builder();
	}

	private Order whenOrder() {
		order = orderBuilder.build();
		MockitoAnnotations.initMocks(this);
		return order;
	}

	private OrderAssert thenOrder() {
		return new OrderAssert(order);
	}

	private DomainEventPublisherAssert thenDomainEventPublisher() {
		return new DomainEventPublisherAssert(eventPublisher);
	}

}
