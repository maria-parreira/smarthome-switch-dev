package smartHomeDDD.domain.domain.valueobject;

import smartHomeDDD.domain.valueobject.ONF01SValue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The ONF01SValueTest class provides unit tests for the ONF01SValue class.
 * It encompasses the following scenarios:
 * - Creating a ONF01SValue with valid ON value (case-insensitive).
 * - Creating a ONF01SValue with valid OFF value (case-insensitive).
 * - Creating a ONF01SValue with invalid value.
 */
class ONF01SValueTest {

    /**
     * Test case for the ONF01SValue constructor with valid "ON" value.
     * The test verifies that a new instance of ONF01SValue is created successfully when provided with "ON" value.
     */
    @ParameterizedTest
    @ValueSource(strings = {"ON", "on", "On", "oN"})
    void constructor_ValidOnValue(String value) {
        // Act
        ONF01SValue onValue = new ONF01SValue(value);
        // Assert
        assertEquals("ON", onValue.toString());
    }

    /**
     * Test case for the ONF01SValue constructor with valid "OFF" value.
     * The test verifies that a new instance of ONF01SValue is created successfully when provided with "OFF" value.
     */
    @ParameterizedTest
    @ValueSource(strings = {"OFF", "off", "Off", "oFf"})
    void constructor_ValidOffValue(String value) {
        // Act
        ONF01SValue offValue = new ONF01SValue(value);
        // Assert
        assertEquals("OFF", offValue.toString());
    }

    /**
     * Test case for the ONF01SValue constructor with invalid value.
     * The test verifies that an InstantiationException is thrown when provided with an invalid value.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Invalid", "", " "})
    @NullSource
    void constructor_InvalidValue_ShouldThrowException(String value) {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ONF01SValue(value));
    }
}
