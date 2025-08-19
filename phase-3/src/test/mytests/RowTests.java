package test.mytests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import main.exceptions.runtime.InvalidRowException;
import main.space.Row;

public class RowTests {

    @Test
    public void testRowAtMinimumValue() {
        Row row = Row.at(1);
        assertEquals(0, row.getVal()); // Because internally rowVal - 1 is stored
    }

    @Test
    public void testRowAtMaximumValue() {
        Row row = Row.at(3);
        assertEquals(2, row.getVal()); // rowVal - 1
    }

    @Test
    public void testRowBelowMinimumThrowsException() {
        assertThrows(InvalidRowException.class, () -> Row.at(0));
    }

    @Test
    public void testRowAboveMaximumThrowsException() {
        assertThrows(InvalidRowException.class, () -> Row.at(4));
    }

    @Test
    public void testRowAtZeroThrowsExceptionWithCorrectMessage() {
        InvalidRowException exception = assertThrows(InvalidRowException.class, () -> Row.at(0));
        assertEquals("Can't create a Row with value 0.", exception.getMessage());
    }

    @Test
    public void testRowAtFourThrowsExceptionWithCorrectMessage() {
        InvalidRowException exception = assertThrows(InvalidRowException.class, () -> Row.at(4));
        assertEquals("Can't create a Row with value 4.", exception.getMessage());
    }

}
