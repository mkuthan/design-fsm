package design.fsm;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class OrderLineIdentifier {

    private String identifier;

    public OrderLineIdentifier(String identifier) {
        this.identifier = requireNonNull(identifier);
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderLineIdentifier that = (OrderLineIdentifier) obj;
        return Objects.equals(this.identifier, that.identifier);
    }

    @Override
    public String toString() {
        return identifier;
    }

}
