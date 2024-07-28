package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.TSY01Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 * Test class for TSY01Value.
 * It tests the following scenarios:
 * - instantiating a TSY01Value with the minimum value
 * - instantiating a TSY01Value with the maximum value
 * - instantiating a TSY01Value with a value below the minimum value
 * - instantiating a TSY01Value with a value above the maximum value
 */
class TSY01ValueTest {

    /**
     * Tests the constructor of TSY01Value when the value is the minimum value.
     * It should create a new instance of TSY01Value.
     */
    @Test
    void minValueTSY01Value_ShouldCreateNewInstance() {
        // Arrange
        Integer expectedValue = 0;

        // Act
        TSY01Value tsy01Value = new TSY01Value(expectedValue);

        // Assert
        assertEquals(expectedValue.toString(), tsy01Value.toString());
    }

    /**
     * Tests the constructor of TSY01Value when the value is the maximum value.
     * It should create a new instance of TSY01Value.
     */
    @Test
    void maxValueTSY01Value_ShouldCreateNewInstance() {
        // Arrange
        Integer expectedValue = 100;

        // Act
        TSY01Value tsy01Value = new TSY01Value(expectedValue);

        // Assert
        assertEquals(expectedValue.toString(), tsy01Value.toString());
    }

    /**
     * Tests the constructor of TSY01Value when the value is below the minimum value.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void invalidMinTSY01Value_ShouldThrowException() {
        // Arrange
        Integer expectedValue = -1;
        String expectedMessage = "Value out of range";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new TSY01Value(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Tests the constructor of TSY01Value when the value is above the maximum value.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void invalidMaxTSY01Value_ShouldThrowException() {
        // Arrange
        Integer expectedValue = 101;
        String expectedMessage = "Value out of range";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new TSY01Value(expectedValue)
        );
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }


}
