package design.fsm;

public class OrderBuilder {

    public OrderStatus status = OrderStatus.NEW;

    OrderIdentifier identifier = new OrderIdentifier("any order");

    OrderDetails details = new OrderDetailsBuilder().build();

    public OrderBuilder withStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public OrderBuilder newOrder() {
        return withStatus(OrderStatus.NEW);
    }

    public OrderBuilder openedOrder() {
        return withStatus(OrderStatus.OPENED);
    }

    public OrderBuilder suspendedOrder() {
        return withStatus(OrderStatus.SUSPENDED);
    }

    public OrderBuilder withIdentifier(OrderIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public OrderBuilder withDetails(OrderDetails details) {
        this.details = details;
        return this;
    }

    public Order build() {
        return new Order(status, identifier, details);
    }

}