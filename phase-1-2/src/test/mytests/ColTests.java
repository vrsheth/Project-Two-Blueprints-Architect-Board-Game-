package test.mytests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import main.exceptions.runtime.InvalidColException;
import main.exceptions.runtime.InvalidRowException;
import main.space.Col;
import main.space.Row;

import org.junit.Test;

public class ColTests {

    @Test
    public void testColAtMinimumValue() {
        Col col = Col.at(1);
        assertEquals(0, col.getVal()); // internally stored as 0
    }

    @Test
    public void testColAtMaximumValue() {
        Col col = Col.at(2);
        assertEquals(1, col.getVal()); // internally stored as 1
    }

    @Test
    public void testColBelowMinimumThrowsException() {
        assertThrows(InvalidColException.class, () -> Col.at(0));
    }

    @Test
    public void testColAboveMaximumThrowsException() {
        assertThrows(InvalidColException.class, () -> Col.at(3));
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
