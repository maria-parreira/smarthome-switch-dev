package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.SR001Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The SR001ValueTest class consists of tests for the SR001Value class.
 * It checks if the SR001Value class can be instantiated with valid and invalid capacity values (percentage).
 * The following tests were performed:
 * - validMeasurementsResults_ShouldCreateNewInstance - Creating a SR001Value with valid measurements.
 * - invalidMeasurementsResults_ShouldThrowException - Creating a SR001Value with invalid measurements.
 * - nullMeasurementsResults_ShouldThrowException - Creating a SR001Value with null measurements.
 */
class SR001ValueTest {

    /**
     * The test verifies that a new instance of SR001Value is created successfully when provided with valid measurements.
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.9, 152.0, 15324.6})
    void validMeasurementsResults_ShouldCreateNewInstance(Double input) {

        // Act
        SR001Value value = new SR001Value(input);

        // Assert
        assertEquals(input.toString(), value.toString());
    }

    /**
     * The test verifies that an IllegalArgumentException is thrown when provided with invalid measurements.
     */
    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {-0.1, -1273.8, -24.5})
    public void invalidMeasurementsResults_ShouldThrowException(Double input) throws IllegalArgumentException {

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SR001Value(input)
        );

        // Assert
        String expectedMessage = "The measurement value is not valid: " + input;
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * The test verifies that an IllegalArgumentException is thrown when provided with a null measurement.
     */

    @Test
    void nullMeasurementsResults_ShouldThrowException() throws IllegalArgumentException {

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SR001Value(null)
        );

        // Assert
        String expectedMessage = "The measurement value is not valid: null";
        assertEquals(expectedMessage, exception.getMessage());
    }


}