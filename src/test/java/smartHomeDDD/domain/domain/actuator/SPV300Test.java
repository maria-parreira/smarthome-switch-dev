package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.SPV300;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The SPV300Test class contains the unit tests for the SPV300 class.
 * It tests the conditions for:
 * - creating a new SPV300 instance
 * - comparing a SPV300 instance with the same object
 * - Non-equality of two SPV300 actuator Objects with different ActuatorIDs
 * - Non-equality of two SPV300 actuator Objects with different ActuatorModelIDs
 * - Non-equality of two SPV300 actuator Objects with different DeviceIDs
 * - setting a new valid value for the SPV300 instance
 * - setting a new value for the SPV300 instance with invalid parameters
 */

class SPV300Test {

    /**
     * Tests if a new SPV300 instance can be created with valid parameters.
     */

    @Test
    void validParameters_shouldCreateANewInstance() {
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        // Act
        Actuator actuator = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Assert
        assertNotNull(actuator);
        assertEquals(ActuatorIDDouble, actuator.identity());
        assertEquals(actuatorModelIDDouble, actuator.getActuatorModelID());
        assertEquals(deviceIDDouble, actuator.getDeviceID());
    }


    /**
     * Tests if two SPV300 instances are the same using the same object.
     */
    @Test
    void sameObject_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Actuator actuator = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act
        boolean isEquals = actuator.sameAs(actuator);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Tests if two SPV300 instances are equal with different IDs.
     */
    @Test
    void differentIds_shouldReturnFalse(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        ActuatorID actuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Actuator actuator = new SPV300(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SPV300(actuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);
        // Act
        boolean isEquals = actuator.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Tests if two SPV300 instances are the same with different actuator model IDs.
     */
    @Test
    void differentActuatorModelID_shouldReturnFalse(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);
        Actuator actuator = new SPV300(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SPV300(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble2);
        // Act
        boolean isEquals = actuator.sameAs(actuator2);
        // Assert
        assertFalse(isEquals);
    }
    /**
     * Tests if two SPV300 instances are the same with different device IDs.
     */
    @Test
    void differentDeviceID_shouldReturnFalse(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Actuator actuator = new SPV300(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SPV300(actuatorIDDouble, deviceIDDouble2, actuatorModelIDDouble);
        // Act
        boolean isEquals = actuator.sameAs(actuator2);
        // Assert
        assertFalse(isEquals);
    }

    /**
     * Sets a new value for the SPV300 instance with a valid value.
     */
    @Test
    void setValueWithValidValue_shouldReturnTheValue(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Actuator actuator = new SPV300(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        SPV300Value valueDouble2 = mock(SPV300Value.class);
        when(valueDouble2.getValue()).thenReturn(60.0);
        // Act
        Value actualValue = actuator.setValue(valueDouble2);
        // Assert
        assertEquals(valueDouble2, actualValue);
    }

    /**
     * Sets a new value for the SPV300 instance with an invalid value.
     */

    @Test
    void setValueWithInvalidValue_shouldReturnNull(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Actuator actuator = new SPV300(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        OPNCL0100Value valueDouble2 = mock(OPNCL0100Value.class);
        when(valueDouble2.getValue()).thenReturn(10);
        // Act
        Value actualValue = actuator.setValue(valueDouble2);
        // Assert
        assertNull(actualValue);
    }

    /**
     * Tests if the sameAs method returns false when the provided object is not an instance of SPV300.
     */
    @Test
    void sameAs_withNonSPV300Instance_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Object nonSPV300Object = new Object();

        Actuator actuator = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator.sameAs(nonSPV300Object));
    }


    /**
     * Tests that the sameAs method returns false when the _actuatorValue is not null.
     */
    @Test
    void sameAs_withNonNullActuatorValue_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        SPV300Value valueDouble = mock(SPV300Value.class);

        Actuator actuator1 = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator1.setValue(valueDouble);
        Actuator actuator2 = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests if the equals method returns true when the provided object is the same instance.
     */
    @Test
    void equals_withSameInstance_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertEquals(actuator, actuator);
    }

    /**
     * Tests if the equals method returns false when the provided object is not an instance of SPV300.
     */
    @Test
    void equals_withNonSPV300Instance_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Object nonSPV300Object = new Object();

        Actuator actuator = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertNotEquals(actuator, nonSPV300Object);
    }

    /**
     * Tests if the equals method returns true when the provided object is an instance of SPV300 with the same ActuatorID.
     */
    @Test
    void equals_withSameActuatorID_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertEquals(actuator1, actuator2);
    }

    /**
     * Tests if the equals method returns false when the provided object is an instance of SPV300 with a different ActuatorID.
     */
    @Test
    void equals_withDifferentActuatorID_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble1 = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new SPV300(ActuatorIDDouble1, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SPV300(ActuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertNotEquals(actuator1, actuator2);
    }

    /**
     * Tests that the getValue method returns the correct value.
     */
    @Test
    void getValue_shouldReturnCorrectValue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        SPV300Value valueDouble = mock(SPV300Value.class);

        Actuator actuator = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator.setValue(valueDouble);
        // Act
        Value actualValue = actuator.getValue();
        // Assert
        assertEquals(valueDouble, actualValue);
    }

    /**
     * Tests that the hashCode method returns the correct hash code.
     */
    @Test
    void hashCode_shouldReturnCorrectHashCode(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SPV300(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act
        int actualHashCode = actuator.hashCode();
        // Assert
        assertEquals(ActuatorIDDouble.hashCode(), actualHashCode);
    }

}
