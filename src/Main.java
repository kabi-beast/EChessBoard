import Data.ChessMove;
import Handlers.StepHandler;
import Steps.TextToMoveHandler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StepHandler textToMoveHandler = new TextToMoveHandler();

        System.out.println("Welcome to kabilan's e-ChessBoard");

        System.out.println("Enter your color");
        System.out.println("(W)hite or (B)lack?");
        String color = scanner.nextLine();

        while (true) {
            // Get input for fromFile
            System.out.println("Enter the starting file (a-h): ");
            char fromFile = scanner.next().charAt(0);

            // Get input for fromRank
            System.out.println("Enter the starting rank (1-8): ");
            int fromRank = scanner.nextInt();

            // Get input for toFile
            System.out.println("Enter the destination file (a-h): ");
            char toFile = scanner.next().charAt(0);

            // Get input for toRank
            System.out.println("Enter the destination rank (1-8): ");
            int toRank = scanner.nextInt();

            // Create a new ChessMove object
            ChessMove move = new ChessMove(fromFile, fromRank, toFile, toRank);
            textToMoveHandler.handle(move, color);

            // Ask if the user wants to continue
            System.out.println("Do you want to enter another move? (yes/no): ");
            String continueInput = scanner.next();

            if (continueInput.equalsIgnoreCase("no")) {
                break;  // Exit the loop if the user enters 'no'
            }

        }

        scanner.close();
    }
}