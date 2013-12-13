package design.fsm;

import static java.util.Objects.requireNonNull;

public class OrderIllegalStateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Order order;

	private String operation;

	public OrderIllegalStateException(Order order, String operation) {
		this.order = requireNonNull(order);
		this.operation = requireNonNull(operation);
	}

	@Override
	public String getMessage() {
		return "Cannot " + operation + " order '" + order.getNumber() + "' with status '" + order.getStatus() + "'.";
	}
}
