package main.space;

/**
 * Provides a superclass for Row and Col.
 */
public abstract class Position {

    private final int val;

    public Position(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
