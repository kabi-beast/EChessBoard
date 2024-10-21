package Steps;

import Data.ChessMove;
import Handlers.StepHandler;

public class MoveToTextHandler implements StepHandler {
    private StepHandler nextStepHandler;
    @Override
    public void setNext(StepHandler nextHandler) {
        this.nextStepHandler = nextHandler;
    }

    @Override
    public void handle(ChessMove move, String color) {
        System.out.println("will detect the move made by opponent and set it in move");
    }
}
