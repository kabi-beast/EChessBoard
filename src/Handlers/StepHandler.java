package Handlers;

import Data.ChessMove;

public interface StepHandler {
    void setNext(StepHandler nextHandler);  // Method to set the next handler in the chain
    void handle(ChessMove move);
}
