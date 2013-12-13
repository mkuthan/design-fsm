package design.fsm;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDetails {

	private List<OrderLine> orderLines = new ArrayList<>();

	OrderDetails(Builder builder) {
		this.orderLines.addAll(requireNonNull(builder.orderLines));
	}

	public List<OrderLine> getOrderLines() {
		return Collections.unmodifiableList(orderLines);
	}

	void changeOrderLine(OrderLineIdentifier identifier, OrderLine newOrderLine) {
		int indexOfOrderLine = indexOf(identifier);
		orderLines.set(indexOfOrderLine, newOrderLine);
	}

	void addOrderLine(OrderLine newOrderLine) {
		orderLines.add(newOrderLine);
	}

	void removeOrderLine(OrderLineIdentifier identifier) {
		int indexOfOrderLine = indexOf(identifier);
		orderLines.remove(indexOfOrderLine);
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

	public static class Builder {

		private List<OrderLine> orderLines = new ArrayList<>();

		public Builder withOrderLine(OrderLine orderLine) {
			orderLines.add(orderLine);
			return this;
		}

		public Builder withOrderLine(OrderLineIdentifier identifier) {
			return withOrderLine(new OrderLine.Builder().withIdentifier(identifier).build());
		}

		public OrderDetails build() {
			return new OrderDetails(this);
		}
	}

}
