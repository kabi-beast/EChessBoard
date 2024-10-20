package Steps;

import Data.ChessMove;
import Handlers.StepHandler;

public class TextToMoveHandler implements StepHandler {
    private StepHandler nextStepHandler;
    @Override
    public void setNext(StepHandler nextHandler) {
        this.nextStepHandler = nextHandler;
    }

    @Override
    public void handle(ChessMove move) {
        System.out.println("Moving from " + move.getFromFile() + move.getFromRank() + " to " + move.getToFile() + move.getToRank());
    }
}
