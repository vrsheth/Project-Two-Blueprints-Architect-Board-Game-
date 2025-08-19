package main.space;

import main.exceptions.runtime.InvalidColException;

/**
 * Represents the column component of a Blueprint space.
 * 
 * Columns must have a value in [1, 2].
 */
public class Col extends Position {
    private static final int MIN_COL_VAL = 1;
    private static final int MAX_COL_VAL = 2;

    /**
     * We don't allow a Col to be made directly; it must go through
     * the at(colVal) static creation method.
     */
    public Col(int colVal) {
        super(colVal - 1);
    }

    /**
     * Returns a Col at the provided colVal, which should
     * be in [1, 2].
     * 
     * If the provided value is not in the necessary range,
     * then an InvalidColException is thrown.
     * 
     * @param colVal the value the created Col should have
     * @return a Col with the provided value
     * @throws InvalidColException If colVal is not in [1, 2].
     */
    public static Col at(int colVal) {
        if (colVal < MIN_COL_VAL || colVal > MAX_COL_VAL) {
            throw new InvalidColException(String.format("Can't create a Col with value %d.", colVal));
        }

        return new Col(colVal);
    }
}
