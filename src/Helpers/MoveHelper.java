package Helpers;

import Data.ChessMove;
import Data.PhoneCoOrdinates;

public class MoveHelper {
    public static PhoneCoOrdinates getPhoneCoOrdinates(ChessMove move) {
        System.out.println("Finding the phone co-ordinates");
        return new PhoneCoOrdinates(1, 2, 3, 4);
    }
}
