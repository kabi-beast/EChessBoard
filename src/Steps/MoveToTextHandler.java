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
        ChessMove opponentMove = null;
        System.out.println("will detect the move made by opponent and set it in move");
        while(opponentMove == null) {
            MoveHelper.takeScreenShot("screenshotAfter.png");
            opponentMove = MoveHelper.isMoveMade("screenshotBefore.png", "screenshotAfter.png", color);
            if (opponentMove != null) {
                System.out.println("opponent's move " + opponentMove.getFromFile() + opponentMove.getFromRank() + " " + opponentMove.getToFile() + opponentMove.getToRank());
            }
        }
    }
}
