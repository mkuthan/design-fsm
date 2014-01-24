package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;

public interface OrderState {

	void open(Order order);

	void close(Order order);

	void suspend(Order order, String reason);

	void resume(Order order);

	void cancel(Order order, String reason);

	boolean canUpdate();
	
	void update(Order order, OrderDetails details);

	boolean canAmendOrderLine();

	void amendOrderLine(Order order, AmendOrderLineCommand command);

}
