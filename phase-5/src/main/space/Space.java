package main.space;

/**
 * Represents a space on a Blueprint. Spaces are located at the
 * intersection of a Row and a Col.
 * 
 * Since attempting to create a Row or Col with an invalid value
 * is impossible, a Space is guaranteed to be valid!
 * 
 */
public class Space {
    private final Row row;
    private final Col col;

    public Space(Row row, Col col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns a Space located at a given Row and Col.
     * 
     * @param row the Row this Space is on
     * @param col the Col this Space is on
     * @return a Space located at (row,col)
     */
    public static Space from(Row row, Col col) {
        return new Space(row, col);
    }

    /**
     * Returns a Space located at a given Col and Row.
     * 
     * @param col the Col this Space is on
     * @param row the Row this Space is on
     * @return a Space located at (row,col)
     */
    public static Space from(Col col, Row row) {
        return new Space(row, col);
    }

    public int rowVal() {
        return row.getVal();
    }

    public int colVal() {
        return col.getVal();
    }

    @Override
    public String toString() {
        return "(" + rowVal() + ", " + colVal() + ")";
    }

}
