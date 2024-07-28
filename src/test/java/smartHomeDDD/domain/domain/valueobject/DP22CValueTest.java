package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.DP22CValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The DP22CValueTest class provides unit tests for the DP22CValue class.
 * It encompasses the following scenarios:
 * - Creating a DP22CValue with valid measurements.
 * - Creating a DP22CValue with invalid measurements.
 */
class DP22CValueTest {

    /**
     * Test case for the DP22CValue constructor with valid measurements.
     * The test verifies that a new instance of DP22CValue is created successfully when provided with valid measurements.
     * Valid measurements include positive and negative values within the acceptable range.
     *
     * @throws IllegalArgumentException if the provided measurement is invalid
     */
    @ParameterizedTest
    @ValueSource(doubles = {30.0, -15.0})
    void validMeasurementsResults_ShouldCreateNewInstance(Double input)   {

        // act
        DP22CValue value = new DP22CValue(input);

        // assert
        assertEquals(input.toString(), value.toString());
    }

    /**
     * Test case for the DP22CValue constructor with invalid measurements.
     * The test verifies that an InstantiationException is thrown when provided with invalid measurements.
     * Invalid measurements include values outside the acceptable range and null values.
     *
     * @throws IllegalArgumentException if the provided measurement is invalid
     */
    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {300.0, -273.0})
    void invalidMeasurementsResults_ShouldThrowException(Double input) {

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new DP22CValue(input)
        );

        // assert
        String expectedMessage = "The measurement value is not valid: " + input;
        assertEquals(expectedMessage, exception.getMessage());
    }
}
