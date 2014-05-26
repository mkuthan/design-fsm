package design.fsm;

import design.fsm.commands.AmendOrderLineCommand;

public class OrderStateAdapter implements OrderState {

    @Override
    public boolean canOpen() {
        return false;
    }

    @Override
    public void open(Order order) {
        throw new IllegalOrderStateException(order, "open");
    }

    @Override
    public boolean canClose() {
        return false;
    }

    @Override
    public void close(Order order) {
        throw new IllegalOrderStateException(order, "close");
    }

    @Override
    public boolean canSuspend() {
        return false;
    }

    @Override
    public void suspend(Order order, String reason) {
        throw new IllegalOrderStateException(order, "suspend");
    }

    @Override
    public boolean canResume() {
        return false;
    }

    @Override
    public void resume(Order order) {
        throw new IllegalOrderStateException(order, "resume");
    }

    @Override
    public boolean canCancel() {
        return false;
    }

    @Override
    public void cancel(Order order, String reason) {
        throw new IllegalOrderStateException(order, "cancel");
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void update(Order order, OrderDetails details) {
        throw new IllegalOrderStateException(order, "update");
    }

    @Override
    public boolean canRevert() {
        return false;
    }

    @Override
    public void revert(Order order) {
        throw new IllegalOrderStateException(order, "revert");
    }

    @Override
    public boolean canRequestForInformation() {
        return false;
    }

    @Override
    public void requestForInformation(Order order, String request) {
        throw new IllegalOrderStateException(order, "request for information");
    }

    @Override
    public boolean canAmendOrderLine() {
        return false;
    }

    @Override
    public void amendOrderLine(Order order, AmendOrderLineCommand command) {
        throw new IllegalOrderStateException(order, "amend order line");
    }

}
