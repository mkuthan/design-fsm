package design.fsm;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class OrderLine {

	private OrderLineIdentifier identifier;

	OrderLine(Builder builder) {
		this.identifier = requireNonNull(builder.identifier);
	}

	public OrderLineIdentifier getIdentifier() {
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
		OrderLine that = (OrderLine) obj;
		return Objects.equals(this.identifier, that.identifier);
	}

	public static class Builder {

		private OrderLineIdentifier identifier;

		public Builder withIdentifier(OrderLineIdentifier identifier) {
			this.identifier = identifier;
			return this;
		}

		public Builder withIdentifier(String identifier) {
			return withIdentifier(new OrderLineIdentifier(identifier));
		}

		public OrderLine build() {
			return new OrderLine(this);
		}

	}

}
