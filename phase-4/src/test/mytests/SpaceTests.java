package test.mytests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.space.Space;
import main.space.Row;
import main.exceptions.runtime.InvalidColException;
import main.exceptions.runtime.InvalidRowException;
import main.space.Col;

public class SpaceTests {

    @Test
    public void testSpaceCoordinatesWithConstructor() {
        Row row = Row.at(2);
        Col col = Col.at(1);
        Space space = new Space(row, col);

        assertEquals(1, space.rowVal()); // Because Row stores val - 1 internally
        assertEquals(0, space.colVal()); // Same for Col
    }

    @Test
    public void testSpaceFromRowCol() {
        Row row = Row.at(1);
        Col col = Col.at(2);
        Space space = Space.from(row, col);

        assertEquals(0, space.rowVal());
        assertEquals(1, space.colVal());
    }

    @Test
    public void testSpaceFromColRow() {
        Col col = Col.at(2);
        Row row = Row.at(3);
        Space space = Space.from(col, row);

        assertEquals(2, space.rowVal());
        assertEquals(1, space.colVal());
    }

    @Test
    public void testToStringFormatting() {
        Row row = Row.at(2);
        Col col = Col.at(2);
        Space space = Space.from(row, col);

        assertEquals("(1, 1)", space.toString());
    }

    @Test
    public void testSpaceCreationWithInvalidRowThrowsException() {
        InvalidRowException exception = assertThrows(InvalidRowException.class, () -> {
            new Space(Row.at(0), Col.at(1));
        });
        assertEquals("Can't create a Row with value 0.", exception.getMessage());
    }

    @Test
    public void testSpaceCreationWithInvalidColThrowsException() {
        InvalidColException exception = assertThrows(InvalidColException.class, () -> {
            new Space(Row.at(1), Col.at(3));
        });
        assertEquals("Can't create a Col with value 3.", exception.getMessage());
    }

}
