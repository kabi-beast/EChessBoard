package Helpers;

import Data.ChessMove;
import Data.PhoneCoOrdinates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MoveHelper {
    public static PhoneCoOrdinates getPhoneCoOrdinates(ChessMove move) {
        System.out.println("Finding the phone co-ordinates");
        return new PhoneCoOrdinates(
                (float) (67.5 + 135.0 * (move.getFromFile() - 'a')),
                (float) (1756.5 - (135.0 * (move.getFromRank() - 1))),
                (float) (67.5 + 135.0 * (move.getToFile() - 'a')),
                (float) (1756.5 - (135.0 * (move.getToRank() - 1))));
    }

    public static void makeMove(PhoneCoOrdinates coOrdinates) {
        String command1 = "adb shell input tap " + coOrdinates.getFromX() + " " + coOrdinates.getFromY();
        executeCommand(command1);
        // add one second sleep
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String command2 = "adb shell input tap " + coOrdinates.getToX() + " " + coOrdinates.getToY();
        executeCommand(command2);
    }

    private static void executeCommand(String command) {
        try {
            // Execute the command
            Process process = Runtime.getRuntime().exec(command);

            // Read the output from the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print output line by line
            }

            // Wait for the process to complete and get the exit code
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
