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
