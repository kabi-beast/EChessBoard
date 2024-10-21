package Steps;

import Data.ChessMove;
import Handlers.StepHandler;
import Helpers.MoveHelper;

public class MoveToTextHandler implements StepHandler {
    private StepHandler nextStepHandler;
    @Override
    public void setNext(StepHandler nextHandler) {
        this.nextStepHandler = nextHandler;
    }

    @Override
    public void handle(ChessMove move, String color) {
        MoveHelper.takeScreenShot("screenshotAfter.png");
        System.out.println("will detect the move made by opponent and set it in move");
    }
}
