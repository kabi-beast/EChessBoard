package Steps;

import Data.ChessMove;
import Data.PhoneCoOrdinates;
import Handlers.StepHandler;
import Helpers.MoveHelper;

public class TextToMoveHandler implements StepHandler {
    private StepHandler nextStepHandler;
    @Override
    public void setNext(StepHandler nextHandler) {
        this.nextStepHandler = nextHandler;
    }

    @Override
    public void handle(ChessMove move) {
        System.out.println("Moving from " + move.getFromFile() + move.getFromRank() + " to " + move.getToFile() + move.getToRank());
        PhoneCoOrdinates phoneCoOrdinates = MoveHelper.getPhoneCoOrdinates(move);
        System.out.println("Phone Co Ordinates: " + phoneCoOrdinates.getFromX() + " " + phoneCoOrdinates.getFromY() + " to " + phoneCoOrdinates.getToX() + " " + phoneCoOrdinates.getToY());
    }
}
