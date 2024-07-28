package smartHomeDDD.domain.domain.valueobject;


import smartHomeDDD.domain.valueobject.ONF01AValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the ONF01AValue class, which represents a value that can be either 'ON' or 'OFF'.
 * It contains the following tests:
 * - Creation of ONF01AValue instances with various inputs, including valid and invalid values, case-insensitive values,
 * null, empty, and whitespace values.
 * The tests are based on the following requirements:
 * - The value must be 'ON' or 'OFF'.
 * - The value is case-insensitive.
 * - The value cannot be null, empty, or whitespace.
 */

class ONF01AValueTest {

    /**
     * Tests the constructor with 'ON' value. The instance should be created successfully.
     *
     */
    @Test
    void testConstructor_ONValue() {
        // Arrange
        String sValue = "ON";
        // Act
        ONF01AValue ONF01AValue = new ONF01AValue(sValue);
        // Assert
        assertEquals(sValue, ONF01AValue.toString());
    }

    /**
     * Tests the constructor with 'OFF' value. The instance should be created successfully.
     *
     */
    @Test
    void testConstructor_OFFValue() {
        // Arrange
        String sValue = "OFF";
        // Act
        ONF01AValue ONF01AValue = new ONF01AValue(sValue);
        // Assert
        assertEquals(sValue, ONF01AValue.toString());
    }

    /**
     * Tests the constructor with case-insensitive 'on' value. The instance should be created successfully.
     *
     */
    @ParameterizedTest
    @ValueSource(strings = {"on", "ON", "oN", "On"})
    void testConstructor_ONValueCaseInsensitive(String sValue) {
        // Arrange
        ONF01AValue value = new ONF01AValue(sValue);

        // Act + Assert
        assertEquals(sValue, value.toString());
    }

    /**
     * Tests the constructor with case-insensitive 'off' value. The instance should be created successfully.
     *
     */
    @ParameterizedTest
    @ValueSource(strings = {"off", "OFF", "oFf", "OfF", "Off", "OFf"})
     void testConstructor_OFFValueCaseInsensitive(String sValue) {
        // Arrange
        ONF01AValue value = new ONF01AValue(sValue);
        // Act
        ONF01AValue ONF01AValue = new ONF01AValue(sValue);
        // Assert
        assertEquals(sValue,value.toString());
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when attempting to create a ONF01AValue with invalid input.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "\r"})
    void shouldThrowException_ONF01AValue(String sValue) {
        Exception exception = assertThrows(Exception.class, () -> new ONF01AValue(sValue));

        String expectedMessage = "Invalid value for ONF01AValue. Must be 'ON' or 'OFF'.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test to verify that the getValue method correctly retrieves the value of an ONF01AValue object.
     */
    @Test
    void getValue_withONF01AValue_shouldReturnONF01AValue() {
        // Arrange
        String expectedValue = "ON";
        ONF01AValue onf01AValue = new ONF01AValue(expectedValue);

        // Act
        String actualValue = onf01AValue.getValue();

        // Assert
        assertEquals(expectedValue, actualValue);
    }
}
