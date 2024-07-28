package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.ONF01A;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ONF01AValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is the test class for the ONF01A actuator. It lists the following scenarios:
 * Successful instantiation of the ONF01A object with mock dependencies.
 * Two ONF01A actuator objects are considered equal when they have the same values.
 * Two ONF01A actuator objects are not considered equal when they have different values.
 * Two ONF01A actuator objects are not considered equal when they have different actuator IDs.
 * Two ONF01A actuator objects are not considered equal when they have different Device IDs.
 * Two ONF01A actuator objects are not considered equal when they have different actuatorModel IDs.
 * A successful setup of value in the actuator;
 * A unsuccessful setup of value in the actuator due to null value;
 * A unsuccessful setup value in the actuator due to value not being 'ON' or 'OFF'
 */
class ONF01ATest
{
    /**
     * Tests the instantiation of a ONF01A object with valid parameters;
     */
    @Test
    void validParameters_shouldCreateANewInstance() {
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new ONF01A(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Assert
        assertNotNull(actuator);
        assertEquals(ActuatorIDDouble, actuator.identity());
        assertEquals(actuatorModelIDDouble, actuator.getActuatorModelID());
        assertEquals(deviceIDDouble, actuator.getDeviceID());
    }
    /**
     * Tests that the equals method returns true when two ONF01A actuator objects are the same.
     */
    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new ONF01A(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        // Act
        boolean isEquals = actuator.sameAs(actuator);

        // Assert
        assertTrue(isEquals);
    }
    /**
     * Tests that two ONF01A actuator objects with different actuator IDs are not considered as the same object when using the sameAs method.
     */
    @Test
    void differentIds_shouldReturnFalse() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        ActuatorID actuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new ONF01A(actuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);

        // Act
        boolean isEquals = actuator.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }
    /**
     * Tests that two ONF01A actuator objects with different values are not considered equivalent due to different Value objects
     */
    @Test
    void differentValues_shouldReturnFalse() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);


        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator.setValue(new ONF01AValue("on"));
        actuator2.setValue(new ONF01AValue("off"));
        // Act
        boolean isEquals = actuator.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }
    /**
     * Tests that two ONF01A actuator objects with different actuatorModel IDs are not considered equivalent.
     */
    @Test
    void differentActuatorModelID_shouldReturnFalse() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);


        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble2);

        // Act
        boolean isEquals = actuator.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }
    /**
     * Tests that two ONF01A actuator objects with different Device IDs are not considered the same.
     */
    @Test
    void differentDeviceID_shouldReturnFalse() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);


        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new ONF01A(actuatorIDDouble, deviceIDDouble2, actuatorModelIDDouble);

        // Act
        boolean isEquals = actuator.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }
    /**
     * Test case for setting a valid value to an instance of ONF01A.
     * It should set the value and return it.
     */
    @Test
    void validSetValueONF01A_ShouldReturnValid() {
        // arrange
        String newValue= "OFf";
        ONF01AValue newVActuatorValue = mock(ONF01AValue.class);
        when(newVActuatorValue.getValue()).thenReturn(newValue);
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);

        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        ONF01A ONF01A = new ONF01A(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        ONF01A.setValue(newVActuatorValue);

        String expectedValue = "OFf";
        // assert
        assertEquals(ONF01A.getONF01AValue().getValue(), expectedValue);
    }
    /**
     * Test case for setting an invalid value to an instance of ONF01A (below zero).
     * It should return null.
     * @throws IllegalArgumentException if the value is not an instance of ONF01AValue
     */
    @Test
    void validONF01A_SetInvalidValue_shouldReturnNull() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> actuator.setValue(new ONF01AValue("One")));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Invalid value for ONF01AValue. Must be 'ON' or 'OFF'.";

        assertTrue(actualMessage.contains(expectedMessage));
    }
    /**
     * Test case for setting an invalid value to an instance of ONF01A, where the value isn't either 'ON' or 'OFF'.
     */
    @Test
    void validONF01A_SetInvalidValueNotOnOrOff_shouldReturnNull() {
        // arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);

        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> actuator.setValue(new ONF01AValue("OFE")));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Invalid value for ONF01AValue. Must be 'ON' or 'OFF'.";

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case for setting a valid value (ONF01AValue) to an instance of ONF01A.
     * It should set the value and return it.
     */
    @Test
    void setValue_withONF01AValue_shouldReturnONF01AValue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        ONF01AValue onf01AValue = new ONF01AValue("ON");

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        var result = actuator.setValue(onf01AValue);

        // Assert
        assertEquals(onf01AValue, result);
    }

    /**
     * Test case for setting an invalid value (not an instance of ONF01AValue) to an instance of ONF01A.
     * It should return null.
     */
    @Test
    void setValue_withNonONF01AValue_shouldReturnNull() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Value nonONF01AValue = mock(Value.class);

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        var result = actuator.setValue(nonONF01AValue);

        // Assert
        assertNull(result);
    }

    /**
     * Test case for comparing an instance of ONF01A with itself using the sameAs method.
     * It should return true.
     */
    @Test
    void sameAs_withSameONF01AObject_shouldReturnTrue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        boolean result = actuator.sameAs(actuator);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two different instances of ONF01A using the sameAs method.
     * It should return false.
     */
    @Test
    void sameAs_withDifferentONF01AObject_shouldReturnFalse() {
        // Arrange
        ActuatorID actuatorIDDouble1 = mock(ActuatorID.class);
        DeviceId deviceIDDouble1 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble1 = mock(ActuatorModelID.class);

        ActuatorID actuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);

        // Act
        Actuator actuator1 = new ONF01A(actuatorIDDouble1, deviceIDDouble1, actuatorModelIDDouble1);
        Actuator actuator2 = new ONF01A(actuatorIDDouble2, deviceIDDouble2, actuatorModelIDDouble2);
        boolean result = actuator1.sameAs(actuator2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing an instance of ONF01A with an object that is not an instance of ONF01A using the sameAs method.
     * It should return false.
     */
    @Test
    void sameAs_withNonONF01AObject_shouldReturnFalse() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Object nonONF01AObject = new Object();

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        boolean result = actuator.sameAs(nonONF01AObject);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two instances of ONF01A with the same _actuatorValue, _actuatorID, _deviceID, and _actuatorModelID using the sameAs method.
     * It should return true.
     */
    @Test
    void sameAs_withIdenticalONF01AObjects_shouldReturnTrue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        ONF01AValue onf01AValue = new ONF01AValue("ON");

        // Act
        Actuator actuator1 = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator1.setValue(onf01AValue);
        Actuator actuator2 = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator2.setValue(onf01AValue);
        boolean result = actuator1.sameAs(actuator2);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing an instance of ONF01A with itself using the equals' method.
     * It should return true.
     */
    @Test
    void equals_withSameONF01AObject_shouldReturnTrue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        // Assert
        assertTrue(true);
    }

    /**
     * Test case for comparing two different instances of ONF01A using the equals' method.
     * It should return false.
     */
    @Test
    void equals_withDifferentONF01AObject_shouldReturnFalse() {
        // Arrange
        ActuatorID actuatorIDDouble1 = mock(ActuatorID.class);
        DeviceId deviceIDDouble1 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble1 = mock(ActuatorModelID.class);

        ActuatorID actuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);

        // Act
        Actuator actuator1 = new ONF01A(actuatorIDDouble1, deviceIDDouble1, actuatorModelIDDouble1);
        Actuator actuator2 = new ONF01A(actuatorIDDouble2, deviceIDDouble2, actuatorModelIDDouble2);
        boolean result = actuator1.equals(actuator2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing an instance of ONF01A with an object that is not an instance of ONF01A using the equals' method.
     * It should return false.
     */
    @Test
    void equals_withNonONF01AObject_shouldReturnFalse() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Object nonONF01AObject = new Object();

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        boolean result = actuator.equals(nonONF01AObject);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for retrieving the value of an instance of ONF01A using the getValue method.
     * It should return the current value of the actuator.
     */
    @Test
    void getValue_withONF01AValue_shouldReturnONF01AValue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        ONF01AValue onf01AValue = new ONF01AValue("ON");

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator.setValue(onf01AValue);
        var result = actuator.getValue();

        // Assert
        assertEquals(onf01AValue, result);
    }

    /**
     * Test case for retrieving the value of an instance of ONF01A using the getValue method.
     * It should return null.
     */
    @Test
    void sameAs_withSameActuatorIDAndDeviceID_shouldReturnTrue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator1 = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        // Assert
        assertTrue(actuator1.sameAs(actuator2));
    }

    /**
     * Test to verify that the equals method returns true when comparing two ONF01A objects with the same ActuatorID.
     */
    @Test
    void equals_withSameInstance_shouldReturnTrue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        // Assert
        assertEquals(actuator, actuator);
    }

    /**
     * Test to verify that the hashCode method correctly calculates the hash code of a valid ONF01A object.
     */
    @Test
    void hashCode_withValidONF01AObject_shouldReturnCorrectHashCode() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        int actualHashCode = actuator.hashCode();

        // Assert
        assertEquals(actuatorIDDouble.hashCode(), actualHashCode);
    }

    @Test
    void equals_withSameActuatorID_shouldReturnTrue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator1 = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new ONF01A(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        // Assert
        assertEquals(actuator1, actuator2);
    }
}

