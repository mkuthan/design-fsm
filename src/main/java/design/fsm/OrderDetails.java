package design.fsm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class OrderDetails {

    private List<OrderLine> orderLines = new ArrayList<>();

    public OrderDetails(List<OrderLine> orderLines) {
        this.orderLines.addAll(requireNonNull(orderLines));
    }

    public List<OrderLine> getOrderLines() {
        return Collections.unmodifiableList(orderLines);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    OrderLine changeOrderLine(OrderLineIdentifier identifier, OrderLine newOrderLine) {
        int indexOfOrderLine = indexOf(identifier);
        return orderLines.set(indexOfOrderLine, newOrderLine);
    }

    boolean addOrderLine(OrderLine newOrderLine) {
        return orderLines.add(newOrderLine);
    }

    OrderLine removeOrderLine(OrderLineIdentifier identifier) {
        int indexOfOrderLine = indexOf(identifier);
        return orderLines.remove(indexOfOrderLine);
    }

    private int indexOf(OrderLineIdentifier identifier) {
        for (int i = 0; i < orderLines.size(); i++) {
            OrderLine orderLine = orderLines.get(i);
            if (orderLine.getIdentifier().equals(identifier)) {
                return i;
            }
        }

        throw new IllegalArgumentException("Order line with identifier '" + identifier + "' not found.");
    }

}
