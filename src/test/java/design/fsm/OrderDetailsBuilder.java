package design.fsm;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBuilder {

    private List<OrderLine> orderLines = new ArrayList<>();

    public OrderDetailsBuilder addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        return this;
    }

    public OrderDetails build() {
        return new OrderDetails(orderLines);
    }
}