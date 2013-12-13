package design.fsm;

import static java.util.Objects.requireNonNull;

public class OrderLine {

	private OrderLineIdentifier identifier;

	OrderLine(Builder builder) {
		this.identifier = requireNonNull(builder.identifier);
	}

	public OrderLineIdentifier getIdentifier() {
		return identifier;
	}

	public static class Builder {
		
		private OrderLineIdentifier identifier;

		public Builder withIdentifier(OrderLineIdentifier identifier) {
			this.identifier = identifier;
			return this;
		}

		public OrderLine build() {
			return new OrderLine(this);
		}

	}

}
