package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.CAP200Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The CAP200ValueTest class consists of tests for the CAP200Value class.
 * It checks if the CAP200Value class can be instantiated with valid and invalid capacity values (percentage).
 * The following tests were performed:
 * - validMeasurementsResults_ShouldCreateNewInstance - Creating a CAP200Value with valid measurements.
 * - invalidMeasurementsResults_ShouldThrowException - Creating a CAP200Value with invalid measurements.
 * - nullMeasurementsResults_ShouldThrowException - Creating a CAP200Value with null measurements.
 */
class CAP200ValueTest {

    /**
     * The test verifies that a new instance of CAP200Value is created successfully when provided with valid measurements.
     *
     * @throws IllegalArgumentException if the provided measurement is invalid
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 100, 1})
    public void validMeasurementsResults_ShouldCreateNewInstance(Integer input) throws IllegalArgumentException {

        // Act
        CAP200Value value = new CAP200Value(input);

        // Assert
        assertEquals(input.toString(), value.toString());
    }

    /**
     * The test verifies that an InstantiationException is thrown when provided with invalid measurements.
     *
     * @throws IllegalArgumentException if the provided value is not within the range of 0 and 100
     */
    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1, 101})
    public void invalidMeasurementsResults_ShouldThrowException(Integer input) throws IllegalArgumentException {

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CAP200Value(input)
        );

        // Assert
        String expectedMessage = "The measurement value is not valid: " + input;
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * The test verifies that an InstantiationException is thrown when provided with a null measurement.
     *
     * @throws IllegalArgumentException if the provided value is null
     */

    @Test
    public void nullMeasurementsResults_ShouldThrowException() throws IllegalArgumentException {

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CAP200Value(null)
        );

        // Assert
        String expectedMessage = "The measurement value is not valid: null";
        assertEquals(expectedMessage, exception.getMessage());
    }


}
