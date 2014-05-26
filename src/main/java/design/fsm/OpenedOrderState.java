package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;

public class OpenedOrderState extends OrderStateAdapter implements OrderState {

    @Override
    public boolean canClose() {
        return true;
    }

    @Override
    public void close(Order order) {
        order.doClose();
    }

    @Override
    public boolean canSuspend() {
        return true;
    }

    @Override
    public void suspend(Order order, String reason) {
        order.doSuspend(reason);
    }

    @Override
    public boolean canCancel() {
        return true;
    }

    @Override
    public void cancel(Order order, String reason) {
        order.doCancel(reason);
    }

    @Override
    public boolean canRevert() {
        return true;
    }

    @Override
    public void revert(Order order) {
        order.doRevert();
    }

    @Override
    public boolean canRequestForInformation() {
        return true;
    }

    @Override
    public void requestForInformation(Order order, String request) {
        order.doRequestForInformation(request);
    }

    @Override
    public boolean canAmendOrderLine() {
        return true;
    }

    @Override
    public void amendOrderLine(Order order, AmendOrderLineCommand command) {
        order.doAmendOrderLine(command);
    }

}
