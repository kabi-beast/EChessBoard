package Helpers;

import Data.ChessMove;
import Data.PhoneCoOrdinates;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MoveHelper {
    public static PhoneCoOrdinates getPhoneCoOrdinates(ChessMove move, String color) {
        System.out.println("Finding the phone co-ordinates");
        if ( color.equalsIgnoreCase("w") || color.equalsIgnoreCase("white")) {
            return new PhoneCoOrdinates(
                    (float) (67.5 + 135.0 * (move.getFromFile() - 'a')),
                    (float) (1756.5 - (135.0 * (move.getFromRank() - 1))),
                    (float) (67.5 + 135.0 * (move.getToFile() - 'a')),
                    (float) (1756.5 - (135.0 * (move.getToRank() - 1))));
        }
        return new PhoneCoOrdinates(
                (float) (67.5 + 135.0 * ('h' - move.getFromFile())),
                (float) (1756.5 - (135.0 * (8 - move.getFromRank()))),
                (float) (67.5 + 135.0 * ('h' - move.getToFile())),
                (float) (1756.5 - (135.0 * (8 - move.getToRank()))));
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

    public static void takeScreenShot(String fileName) {
        String command = "adb shell screencap /sdcard/" + fileName;
        executeCommand(command);
    }

    public static ChessMove compareBoards(String board1, String board2, String color) {
        try {
            BufferedImage boardImage1 = ImageIO.read(new File(board1));
            BufferedImage boardImage2 = ImageIO.read(new File(board2));
            System.out.println("size of images " + boardImage1.getWidth() + " " + boardImage1.getHeight());
            BufferedImage croppedImage1 = cropBoard(boardImage1);
            BufferedImage croppedImage2 = cropBoard(boardImage2);

            if (compareImages(croppedImage1, croppedImage2)) {
                return null;
            }

            char fromFile = 'z'; // 'e'
            int fromRank = 9;  // 2
            char toFile = 'z';   // 'e'
            int toRank = 9; // 4

            // Define the chessboard size (8x8 grid)
            int gridSize = 8;
            int squareWidth = croppedImage1.getWidth() / gridSize;  // Width of one square
            int squareHeight = croppedImage1.getHeight() / gridSize;  // Height of one square

            boolean changesDetected = false;

            // Loop through each square (8x8 grid)
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    // Define the area for the current square in both images
                    int x = col * squareWidth;
                    int y = row * squareHeight;

                    // Extract the squares from both images
                    BufferedImage square1 = croppedImage1.getSubimage(x, y, squareWidth, squareHeight);
                    BufferedImage square2 = croppedImage2.getSubimage(x, y, squareWidth, squareHeight);

                    // Check if the second square has a yellowish tint
                    if (containsYellowishTint(square2)) {
                        System.out.println("Yellowish tint detected at square: (" + row + ", " + col + ")");
                        changesDetected = true;

                        // Determine if there is a piece on this square
                        if (hasPiece(square2)) {
                            if (color.equalsIgnoreCase("w") || color.equalsIgnoreCase("white")) {
                                toFile = (char) (col + 'a');
                                toRank = 8 - row;
                            } else {
                                toFile = (char) ((7 - col) + 'a');
                                toRank = row + 1;
                            }
                            System.out.println("There is a piece on the square at (" + row + ", " + col + ")");
                        } else {
                            if (color.equalsIgnoreCase("w") || color.equalsIgnoreCase("white")) {
                                fromFile = (char) (col + 'a');
                                fromRank = 8 - row;
                            }
                            else {
                                fromFile = (char) ((7 - col) + 'a');
                                fromRank = row + 1;
                            }
                            System.out.println("No piece on the square at (" + row + ", " + col + ")");
                        }
                    }
                }
            }

            if (!changesDetected) {
                System.out.println("No yellowish tint detected.");
            }
            return new ChessMove(fromFile, fromRank, toFile, toRank);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to compare two images pixel by pixel
    public static boolean compareImages(BufferedImage img1, BufferedImage img2) {
        // Check if both images have the same dimensions
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false; // Images are different if they have different dimensions
        }

        // Loop through each pixel and compare RGB values
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                int pixel1 = img1.getRGB(x, y);
                int pixel2 = img2.getRGB(x, y);

                if (pixel1 != pixel2) {
                    return false; // Images are different if any pixel doesn't match
                }
            }
        }

        // Images are the same if no differences are found
        return true;
    }

    // Method to check if the square contains a yellowish tint
    public static boolean containsYellowishTint(BufferedImage img) {
        long totalRed = 0, totalGreen = 0, totalBlue = 0;
        int pixelCount = img.getWidth() * img.getHeight();

        // Sum up the RGB values of all pixels
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int rgb = img.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                totalRed += red;
                totalGreen += green;
                totalBlue += blue;
            }
        }

        // Calculate average RGB values
        long avgRed = totalRed / pixelCount;
        long avgGreen = totalGreen / pixelCount;
        long avgBlue = totalBlue / pixelCount;

        // Check if red and green are much higher than blue (indicating yellowish tint)
        return isYellowish(avgRed, avgGreen, avgBlue);
    }

    // Helper method to determine if the average color is yellowish
    private static boolean isYellowish(long avgRed, long avgGreen, long avgBlue) {
        // Adjust the threshold as needed to detect yellowish tint effectively
        int yellowThreshold = 40; // Allows some flexibility in detecting tint

        // Check if red and green are significantly greater than blue
        return avgRed > avgBlue + yellowThreshold && avgGreen > avgBlue + yellowThreshold;
    }

    // Method to detect if there is a piece on the square (based on color variance)
    private static boolean hasPiece(BufferedImage img) {
        long totalRed = 0, totalGreen = 0, totalBlue = 0;
        int pixelCount = img.getWidth() * img.getHeight();

        // Sum up RGB values
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int rgb = img.getRGB(x, y);
                totalRed += (rgb >> 16) & 0xFF;
                totalGreen += (rgb >> 8) & 0xFF;
                totalBlue += rgb & 0xFF;
            }
        }

        // Calculate the average color of the square
        long avgRed = totalRed / pixelCount;
        long avgGreen = totalGreen / pixelCount;
        long avgBlue = totalBlue / pixelCount;

        // Calculate color variance (i.e., how much the pixel values deviate from the average)
        long variance = 0;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int rgb = img.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Calculate squared differences from the average
                variance += (red - avgRed) * (red - avgRed) +
                        (green - avgGreen) * (green - avgGreen) +
                        (blue - avgBlue) * (blue - avgBlue);
            }
        }

        // Determine if there is a piece based on variance
        // If the variance is high, we assume there is a piece (as pieces introduce more color variation)
        return variance > 1000000;  // You can adjust this threshold based on experimentation
    }

    private static boolean compareSquares(BufferedImage img1, BufferedImage img2) {
        // Check dimensions first (should be the same)
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return false;
        }

        // Compare pixel-by-pixel
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static BufferedImage cropBoard(BufferedImage boardImage) {
        // Define the crop area (x, y, width, height)
        int cropX = 0;  // Starting X coordinate of the crop area
        int cropY = 744;  // Starting Y coordinate of the crop area
        int cropWidth = 135 * 8;  // Width of the crop area
        int cropHeight = 135 * 8;  // Height of the crop area

        // Crop the image using getSubimage
        BufferedImage croppedImage = boardImage.getSubimage(cropX, cropY, cropWidth, cropHeight);

        // Save the cropped image to a new file
        File output = new File("cropped_image.png");
        try {
            ImageIO.write(croppedImage, "png", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Cropped image saved successfully!");
        return croppedImage;
    }

    public static ChessMove isMoveMade(String imageFile1, String imageFile2, String color) {
        // placeHolder for code to compare two images and determine the move made by the opponent
        // pull both the images to local disk
        executeCommand("adb pull /sdcard/" + imageFile1 + " .");
        executeCommand("adb pull /sdcard/" + imageFile2 + " .");
        return compareBoards(imageFile1, imageFile2, color);
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
