package ui;

public enum CardinalDirection {
    LEFT(-1), UP(-1), RIGHT(1), DOWN(1), NONE(0);

    private int directionValue;

    CardinalDirection(int directionValue) {
        this.directionValue = directionValue;
    }

    public int getDirectionAsInt() {
        return directionValue;
    }
}
