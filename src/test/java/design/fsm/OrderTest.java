package design.fsm;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import design.ddd.EventPublisher;
import design.ddd.EventPublisherAssert;
import design.fsm.commands.AmendOrderLineCommand;
import design.fsm.events.OrderCancelledEvent;
import design.fsm.events.OrderClosedEvent;
import design.fsm.events.OrderEventFactory;
import design.fsm.events.OrderLineAmendedEvent;
import design.fsm.events.OrderOpenedEvent;
import design.fsm.events.OrderRequestedForInformationEvent;
import design.fsm.events.OrderResumedEvent;
import design.fsm.events.OrderRevertedEvent;
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

	private OrderBuilder orderBuilder;

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

	public void shouldUpdatee() {
		givenOrder().newOrder();

		// @formatter:off
		OrderDetails newDetails = givenOrderDetails()
				.addOrderLine(givenOrderLine().build())
				.build();
		// @formatter:on
		whenOrder().update(newDetails);

		thenOrder().hasDetails(newDetails);
		thenDomainEventPublisher().published(OrderUpdatedEvent.class);
	}

	public void shouldNotUpdateWhenNoChanges() {
		givenOrder().newOrder();

		OrderDetails newDetails = givenOrderDetails().build();
		whenOrder().update(newDetails);

		thenOrder().hasDetails(newDetails);
		thenDomainEventPublisher().notPublished(OrderUpdatedEvent.class);
	}

	public void shouldRevert() {
		givenOrder().openedOrder();

		whenOrder().revert();

		thenOrder().isNew();
		thenDomainEventPublisher().published(OrderRevertedEvent.class);
	}

	public void shouldRequestForInformation() {
		givenOrder().openedOrder();

		whenOrder().requestForInformation("any request");

		thenOrder().isNew();
		thenDomainEventPublisher().published(OrderRequestedForInformationEvent.class);
	}

	public void shouldChangeOrderLine() {
		OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("changed line");
		// @formatter:off
		givenOrder().openedOrder()
			.withDetails(givenOrderDetails()
				.addOrderLine(givenOrderLine().withIdentifier(orderLineIdentifier).build())
			.build());
		// @formatter:on

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
		givenOrder().openedOrder().withDetails(
				givenOrderDetails().addOrderLine(givenOrderLine().withIdentifier(orderLineIdentifier).build()).build());

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

	private OrderBuilder givenOrder() {
		orderBuilder = new OrderBuilder();
		return orderBuilder;
	}

	private OrderDetailsBuilder givenOrderDetails() {
		return new OrderDetailsBuilder();
	}

	private OrderLineBuilder givenOrderLine() {
		return new OrderLineBuilder();
	}

	private Order whenOrder() {
		order = orderBuilder.build();
		MockitoAnnotations.initMocks(this);
		return order;
	}

	private OrderAssert thenOrder() {
		return new OrderAssert(order);
	}

	private EventPublisherAssert thenDomainEventPublisher() {
		return new EventPublisherAssert(eventPublisher);
	}

}
