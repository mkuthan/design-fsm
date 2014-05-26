package design.fsm;

public class OrderLineBuilder {

    private OrderLineIdentifier identifier = new OrderLineIdentifier("any identifier");

    public OrderLineBuilder withIdentifier(OrderLineIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public OrderLine build() {
        return new OrderLine(identifier);
    }

}