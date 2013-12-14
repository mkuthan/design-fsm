package design.fsm;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrderDetails {

	private List<OrderLine> orderLines = new ArrayList<>();

	OrderDetails(Builder builder) {
		this.orderLines.addAll(requireNonNull(builder.orderLines));
	}

	public List<OrderLine> getOrderLines() {
		return Collections.unmodifiableList(orderLines);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderLines);
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
		OrderDetails that = (OrderDetails) obj;
		return Objects.equals(this.orderLines, that.orderLines);
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

		public OrderDetails build() {
			return new OrderDetails(this);
		}
	}

}
