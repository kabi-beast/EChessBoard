package Data;

public class PhoneCoOrdinates {
    private float fromX;
    private float fromY;
    private float toX;
    private float toY;

    public PhoneCoOrdinates(float fromX, float fromY, float toX, float toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public float getFromX() {
        return fromX;
    }

    public float getFromY() {
        return fromY;
    }

    public float getToX() {
        return toX;
    }

    public float getToY() {
        return toY;
    }
}
