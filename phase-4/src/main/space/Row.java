package main.space;

import main.exceptions.runtime.InvalidRowException;

/**
 * Represents the row component of a Blueprint space.
 * 
 * Rows must have a value in [1, 3].
 */
public class Row extends Position {
    private static final int MIN_ROW_VAL = 1;
    private static final int MAX_ROW_VAL = 3;

    /**
     * We don't allow a Row to be made directly; it must go through
     * the at(rowVal) static creation method.
     */
    public Row(int rowVal) {
        super(rowVal - 1);
    }

    /**
     * Returns a Row at the provided rowVal, which should
     * be in [1, 3].
     * 
     * If the provided value is not in the necessary range,
     * then an InvalidRowException is thrown.
     * 
     * @param rowVal the value the created Row should have
     * @return a Row with the provided value
     * @throws InvalidRowException If rowVal is not in [1, 3].
     */
    public static Row at(int rowVal) {
        if (rowVal < MIN_ROW_VAL || rowVal > MAX_ROW_VAL) {
            throw new InvalidRowException(String.format("Can't create a Row with value %d.", rowVal));
        }
        return new Row(rowVal);
    }
}