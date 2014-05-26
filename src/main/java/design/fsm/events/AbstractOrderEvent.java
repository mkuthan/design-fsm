package design.fsm.events;

import design.ddd.Event;
import design.fsm.OrderIdentifier;
import design.fsm.OrderStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static java.util.Objects.requireNonNull;

public abstract class AbstractOrderEvent implements Event {

    private OrderIdentifier identifier;

    private OrderStatus oldStatus;

    private OrderStatus newStatus;

    protected AbstractOrderEvent(OrderIdentifier identifier, OrderStatus status) {
        this(identifier, status, status);
    }

    protected AbstractOrderEvent(OrderIdentifier identifier, OrderStatus oldStatus,
                                 OrderStatus newStatus) {
        this.identifier = requireNonNull(identifier);
        this.oldStatus = requireNonNull(oldStatus);
        this.newStatus = requireNonNull(newStatus);
    }

    public OrderIdentifier getIdentifier() {
        return identifier;
    }

    public OrderStatus getOldStatus() {
        return oldStatus;
    }

    public OrderStatus getNewStatus() {
        return newStatus;
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

}