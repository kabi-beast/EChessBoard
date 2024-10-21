package Helpers;

import Data.ChessMove;
import Data.PhoneCoOrdinates;

public class MoveHelper {
    public static PhoneCoOrdinates getPhoneCoOrdinates(ChessMove move) {
        System.out.println("Finding the phone co-ordinates");
        return new PhoneCoOrdinates(
                (float) (67.5 + 135.0 * (move.getFromFile() - 'a')),
                (float) (1756.5 - (135.0 * (move.getFromRank() - 1))),
                (float) (67.5 + 135.0 * (move.getToFile() - 'a')),
                (float) (1756.5 - (135.0 * (move.getToRank() - 1))));
    }
}
