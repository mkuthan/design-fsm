package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;

public interface OrderState {
	
	boolean canOpen();
	
	void open(Order order);

	boolean canClose();
	
	void close(Order order);
	
	boolean canSuspend();
	
	void suspend(Order order, String reason);
		
	boolean canResume();

	void resume(Order order);
	
	boolean canCancel();

	void cancel(Order order, String reason);

	boolean canUpdate();
	
	void update(Order order, OrderDetails details);

	boolean canRevert();
	
	void revert(Order order);
	
	boolean canRequestForInformation();
	
	void requestForInformation(Order order, String request);
	
	boolean canAmendOrderLine();

	void amendOrderLine(Order order, AmendOrderLineCommand command);

}
