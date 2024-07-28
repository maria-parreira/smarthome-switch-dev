package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.GA100KValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The GA100KValueTest class consists of tests for the GA100KValue class.
 * It checks if the GA100KValue class can be instantiated with valid and invalid capacity values (percentage).
 * The following tests were performed:
 * - validMeasurementsResults_ShouldCreateNewInstance - Creating a GA100KValue with valid measurements.
 * - nullMeasurementsResults_ShouldThrowException - Creating a GA100KValue with null measurements.
 */
class GA100KValueTest {

    /**
     * The test verifies that a new instance of GA100KValue is created successfully when provided with valid measurements.
     *
     * @throws IllegalArgumentException if the provided measurement is invalid
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 100, -100,50})
    public void validMeasurementsResults_ShouldCreateNewInstance(Integer input) throws IllegalArgumentException {

        // Act
        GA100KValue value = new GA100KValue(input);

        // Assert
        assertEquals(input.toString(), value.toString());
    }

    /**
     * The test verifies that an IllegalArgumentException is thrown when provided with a null measurement.
     *
     * @throws IllegalArgumentException if the provided value is null
     */

    @Test
    public void nullMeasurementsResults_ShouldThrowException() throws IllegalArgumentException {

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new GA100KValue(null)
        );

        // Assert
        String expectedMessage = "Value cannot be null";
        assertEquals(expectedMessage, exception.getMessage());
    }


}