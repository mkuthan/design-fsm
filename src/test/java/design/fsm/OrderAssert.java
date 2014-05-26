package design.fsm;

import design.ddd.Event;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Condition;

import static org.assertj.core.api.Assertions.assertThat;

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

    public OrderAssert isSuspended() {
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SUSPENDED);
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

    public OrderAssert containsOrderLine(final OrderLineIdentifier orderLineIdentifier) {
        assertThat(actual.getDetails().getOrderLines()).areExactly(1, new Condition<OrderLine>() {
            @Override
            public boolean matches(OrderLine orderLine) {
                return orderLine.getIdentifier().equals(orderLineIdentifier);
            }
        });
        return this;
    }

    public OrderAssert doesNotContainOrderLine(final OrderLineIdentifier orderLineIdentifier) {
        assertThat(actual.getDetails().getOrderLines()).areExactly(0, new Condition<OrderLine>() {
            @Override
            public boolean matches(OrderLine orderLine) {
                return orderLine.getIdentifier().equals(orderLineIdentifier);
            }
        });
        return this;
    }

    public OrderAssert published(Event event) {
        assertThat(actual.getPendingEvents()).contains(event);
        return this;
    }

    public OrderAssert notPublished(Class<? extends Event> eventClass) {
        assertThat(actual.getPendingEvents()).doesNotHaveSameClassAs(eventClass);
        return this;
    }

}
