import Handlers.InputHandler;
import Handlers.TerminalInputHandler;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to kabilan's e-ChessBoard");
        // TODO: TerminalInputHandler will be changed to SensorInputHandler later
        InputHandler terminalInputHandler = new TerminalInputHandler();
        terminalInputHandler.handleInput();
    }
}