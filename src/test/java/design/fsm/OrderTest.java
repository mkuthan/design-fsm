package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;
import design.fsm.events.*;
import org.testng.annotations.Test;

@Test
public class OrderTest {

    private Order order;

    private OrderBuilder orderBuilder;

    public void shouldBeNew() {
        OrderDetails details = givenOrderDetails().build();
        givenOrder().newOrder().withDetails(details);

        whenOrder();

        thenOrder()
                .isNew()
                .published(new OrderPlacedEvent(order.getIdentifier(), OrderStatus.NEW, details));
    }

    public void shouldBeOpened() {
        givenOrder().newOrder();

        whenOrder().open();

        thenOrder()
                .isOpened()
                .published(new OrderOpenedEvent(order.getIdentifier(), OrderStatus.NEW, OrderStatus.OPENED));
    }

    public void shouldBeClosed() {
        givenOrder().openedOrder();

        whenOrder().close();

        thenOrder()
                .isClosed()
                .published(new OrderClosedEvent(order.getIdentifier(), OrderStatus.OPENED, OrderStatus.CLOSED));
    }

    public void shouldBeSuspended() {
        givenOrder().openedOrder();

        String message = "any message";
        whenOrder().suspend(message);

        thenOrder()
                .isSuspended()
                .published(new OrderSuspendedEvent(order.getIdentifier(), OrderStatus.OPENED, OrderStatus.SUSPENDED, message));
    }

    public void shouldBeResumed() {
        givenOrder().suspendedOrder();

        whenOrder().resume();

        thenOrder()
                .isOpened()
                .published(new OrderResumedEvent(order.getIdentifier(), OrderStatus.SUSPENDED, OrderStatus.OPENED));
    }

    public void shouldBeCancelled() {
        givenOrder().openedOrder();

        String message = "any message";
        whenOrder().cancel(message);

        thenOrder()
                .isCancelled()
                .published(new OrderCancelledEvent(order.getIdentifier(), OrderStatus.OPENED, OrderStatus.CANCELLED, message));
    }

    public void shouldUpdate() {
        OrderDetails oldDetails = givenOrderDetails()
                .addOrderLine(givenOrderLine()
                        .withIdentifier(new OrderLineIdentifier("old line"))
                        .build())
                .build();

        givenOrder().newOrder().withDetails(oldDetails);

        OrderDetails newDetails = givenOrderDetails()
                .addOrderLine(givenOrderLine()
                        .withIdentifier(new OrderLineIdentifier("new line"))
                        .build())
                .build();

        whenOrder().update(newDetails);

        thenOrder()
                .hasDetails(newDetails)
                .published(new OrderUpdatedEvent(order.getIdentifier(), OrderStatus.NEW, oldDetails, newDetails));
    }

    public void shouldNotUpdateWhenNoChanges() {
        OrderDetails details = givenOrderDetails().build();
        givenOrder().newOrder().withDetails(details);

        whenOrder().update(details);

        thenOrder()
                .hasDetails(details)
                .notPublished(OrderUpdatedEvent.class);
    }

    public void shouldRevert() {
        givenOrder().openedOrder();

        whenOrder().revert();

        thenOrder()
                .isNew()
                .published(new OrderRevertedEvent(order.getIdentifier(), OrderStatus.OPENED, OrderStatus.NEW));
    }

    public void shouldRequestForInformation() {
        givenOrder().openedOrder();

        String request = "any request";
        whenOrder().requestForInformation(request);

        thenOrder()
                .isNew()
                .published(new OrderRequestedForInformationEvent(order.getIdentifier(), OrderStatus.OPENED, OrderStatus.NEW, request));
    }

    public void shouldChangeOrderLine() {
        OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("changed line");
        OrderLine orderLine = givenOrderLine().withIdentifier(orderLineIdentifier).build();

        givenOrder().openedOrder()
                .withDetails(
                        givenOrderDetails().addOrderLine(orderLine).build()
                );

        OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
        OrderLine newOrderLine = givenOrderLine().withIdentifier(newOrderLineIdentifier).build();

        whenOrder().amendOrderLine(AmendOrderLineCommand.changeOrderLineCommand(orderLineIdentifier, newOrderLine));

        thenOrder()
                .doesNotContainOrderLine(orderLineIdentifier)
                .containsOrderLine(newOrderLineIdentifier)
                .published(new OrderLineChangedEvent(order.getIdentifier(), OrderStatus.OPENED, orderLineIdentifier, orderLine, newOrderLine));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldChangeFailForNotExistingOrderLine() {
        OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("changed line");
        givenOrder().openedOrder();

        OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
        whenOrder().amendOrderLine(
                AmendOrderLineCommand.changeOrderLineCommand(orderLineIdentifier,
                        givenOrderLine().withIdentifier(newOrderLineIdentifier).build())
        );
    }

    public void shouldAddOrderLine() {
        givenOrder().openedOrder();

        OrderLineIdentifier newOrderLineIdentifier = new OrderLineIdentifier("new line");
        OrderLine newOrderLine = givenOrderLine().withIdentifier(newOrderLineIdentifier).build();

        whenOrder().amendOrderLine(AmendOrderLineCommand.addOrderLineCommand(newOrderLine));

        thenOrder()
                .containsOrderLine(newOrderLineIdentifier)
                .published(new OrderLineAddedEvent(order.getIdentifier(), OrderStatus.OPENED, newOrderLineIdentifier, newOrderLine));
    }

    public void shouldRemoveOrderLine() {
        OrderLineIdentifier orderLineIdentifier = new OrderLineIdentifier("removed line");
        OrderLine orderLine = givenOrderLine().withIdentifier(orderLineIdentifier).build();

        givenOrder().openedOrder()
                .withDetails(
                        givenOrderDetails()
                                .addOrderLine(orderLine)
                                .build()
                );

        whenOrder().amendOrderLine(AmendOrderLineCommand.removeOrderLineCommand(orderLineIdentifier));

        thenOrder()
                .doesNotContainOrderLine(orderLineIdentifier)
                .published(new OrderLineRemovedEvent(order.getIdentifier(), OrderStatus.OPENED, orderLineIdentifier, orderLine));
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
        return order;
    }

    private OrderAssert thenOrder() {
        return new OrderAssert(order);
    }

}
