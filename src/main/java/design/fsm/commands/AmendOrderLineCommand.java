package design.fsm.commands;

import static java.util.Objects.requireNonNull;
import design.fsm.OrderLine;
import design.fsm.OrderLineIdentifier;

public class AmendOrderLineCommand {

	private OrderLineIdentifier identifier;

	private OrderLine newOrderLine;

	AmendOrderLineCommand(OrderLineIdentifier identifier, OrderLine newOrderLine) {
		this.identifier = identifier;
		this.newOrderLine = newOrderLine;
	}

	public static AmendOrderLineCommand changeOrderLineCommand(OrderLineIdentifier identifier, OrderLine newOrderLine) {
		return new AmendOrderLineCommand(requireNonNull(identifier), requireNonNull(newOrderLine));
	}

	public static AmendOrderLineCommand addOrderLineCommand(OrderLine newOrderLine) {
		return new AmendOrderLineCommand(null, requireNonNull(newOrderLine));
	}

	public static AmendOrderLineCommand removeOrderLineCommand(OrderLineIdentifier identifier) {
		return new AmendOrderLineCommand(requireNonNull(identifier), null);
	}

	public OrderLineIdentifier getIdentifier() {
		return identifier;
	}

	public OrderLine getNewOrderLine() {
		return newOrderLine;
	}

	public boolean isChanging() {
		return identifier != null && newOrderLine != null;
	}

	public boolean isAdding() {
		return identifier == null && newOrderLine != null;
	}

	public boolean isRemoving() {
		return identifier != null && newOrderLine == null;
	}

}
