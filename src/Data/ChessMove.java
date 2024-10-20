package Data;

public class ChessMove {
    private char fromFile; // 'e'
    private int fromRank;  // 2
    private char toFile;   // 'e'
    private int toRank;    // 4

    public ChessMove(char fromFile, int fromRank, char toFile, int toRank) {
        this.fromFile = fromFile;
        this.fromRank = fromRank;
        this.toFile = toFile;
        this.toRank = toRank;
    }

    public char getFromFile() {
        return fromFile;
    }

    public int getFromRank() {
        return fromRank;
    }

    public int getToFile() {
        return toFile;
    }

    public int getToRank() {
        return toRank;
    }
}
