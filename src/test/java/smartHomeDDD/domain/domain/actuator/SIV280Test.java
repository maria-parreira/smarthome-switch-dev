package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.SIV280;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The SIV280Test class contains the tests for the SIV280 class.
 * It tests the following cases:
 * create a new instance with valid parameters
 * compare the same object (returns true)
 * compare objects with different IDs, values, actuator model IDs and device IDs (returns false)
 * set a new value with a valid value
 * set a new value with an invalid value
 */
class SIV280Test
{
    /**
     * Tests if the SIV280 class can be instantiated with valid parameters.
     */
    @Test
    void validParameters_shouldCreateANewInstance() {
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Assert
        assertNotNull(actuator);
        assertEquals(ActuatorIDDouble, actuator.identity());
        assertEquals(actuatorModelIDDouble, actuator.getActuatorModelID());
        assertEquals(deviceIDDouble, actuator.getDeviceID());
    }


    /**
     * Tests if two SIV280 instances are the same using the same object.
     */

    @Test
    void sameObject_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        // Act
        boolean isEquals = actuator.sameAs(actuator);

        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test if two SIV280 instances are the same using different IDs.
     */

    @Test
    void differentIds_shouldReturnFalse(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        ActuatorID actuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SIV280(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SIV280(actuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);

        // Act
        boolean isEquals = actuator.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }
    /**
     * Test if two SIV280 instances are the same using different actuator model IDs.
     */
    @Test
    void differentActuatorModelID_shouldReturnFalse(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);

        Actuator actuator = new SIV280(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SIV280(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble2);

        // Act
        boolean isEquals = actuator.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Test if two SIV280 instances are the same using different device IDs.
     */
    @Test
    void differentDeviceID_shouldReturnFalse(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SIV280(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SIV280(actuatorIDDouble, deviceIDDouble2, actuatorModelIDDouble);

        // Act
        boolean isEquals = actuator.sameAs(actuator2);

        // Assert
        assertFalse(isEquals);
    }

    /**
     * Sets a valid SIV2280Value for a SIV280 instance.
     */
    @Test
    void setValueWithValidValue_shouldReturnTheValue(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SIV280(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        SIV280Value valueDouble2 = mock(SIV280Value.class);
        when(valueDouble2.getValue()).thenReturn(10);

        // Act
        Value actualValue = actuator.setValue(valueDouble2);

        // Assert
        assertEquals(valueDouble2, actualValue);
    }

    /**
     * Sets an invalid value for a SIV280 instance.
     */

    @Test
    void setValueWithInvalidValue_shouldReturnNull(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SIV280(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        OPNCL0100Value valueDouble2 = mock(OPNCL0100Value.class);
        when(valueDouble2.getValue()).thenReturn(10);

        // Act
        Value actualValue = actuator.setValue(valueDouble2);

        // Assert
        assertNull(actualValue);
    }

    /**
     * Tests that the equals method returns true when the provided object is the same instance as this.
     */
    @Test
    void equals_withSameInstance_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertEquals(actuator, actuator);
    }

    /**
     * Tests that the equals method returns false when the provided object is an instance of SIV280 with a different ActuatorID.
     */
    @Test
    void equals_withDifferentActuatorID_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble1 = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new SIV280(ActuatorIDDouble1, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SIV280(ActuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertNotEquals(actuator1, actuator2);
    }

    /**
     * Tests that the sameAs method returns true when the provided object is the same instance as this.
     */
    @Test
    void sameAs_withSameInstance_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertTrue(actuator.sameAs(actuator));
    }

    /**
     * Tests that the sameAs method returns false when the provided object is an instance of SIV280 with a different ActuatorID.
     */
    @Test
    void sameAs_withDifferentActuatorID_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble1 = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new SIV280(ActuatorIDDouble1, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SIV280(ActuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that the sameAs method returns false when the provided object is not an instance of SIV280.
     */
    @Test
    void sameAs_withNonSIV280Object_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Object nonSIV280Object = new Object();

        Actuator actuator = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator.sameAs(nonSIV280Object));
    }

    /**
     * Test to verify that the equals method returns true when comparing two SIV280 objects with the same ActuatorID, DeviceID, and ActuatorModelID.
     * The test creates two SIV280 objects with the same ActuatorID, DeviceID, and ActuatorModelID, and then checks if the equals method returns true.
     * If the equals method is correctly implemented, this test should pass.
     */
    @Test
    void equals_withSIV280Instance_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertEquals(actuator1, actuator2);
    }

    /**
     * Test to verify that the equals method returns false when comparing two SIV280 objects with different ActuatorID, DeviceID, or ActuatorModelID.
     * The test creates two SIV280 objects with different ActuatorID, DeviceID, or ActuatorModelID, and then checks if the equals method returns false.
     * If the equals method is correctly implemented, this test should pass.
     */
    @Test
    void equals_withNonMatchingConditions_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble1 = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new SIV280(ActuatorIDDouble1, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SIV280(ActuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertNotEquals(actuator1, actuator2);
    }

    /**
     * Test to verify that the equals method returns false when comparing a SIV280 object with an object that is not an instance of SIV280.
     * The test creates a SIV280 object and a non-SIV280 object, and then checks if the equals method returns false.
     * If the equals method is correctly implemented, this test should pass.
     */
    @Test
    void equals_withNonSIV280Instance_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Object nonSIV280Object = new Object();

        Actuator actuator = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertNotEquals(actuator, nonSIV280Object);
    }

    /**
     * Tests that the sameAs method returns true when the provided object is an instance of SIV280 with a null _actuatorValue and matching _actuatorID, _deviceID, and _actuatorModelID.
     */
    @Test
    void sameAs_withNullActuatorValueAndMatchingConditions_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertTrue(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that the sameAs method returns false when the provided object is an instance of SIV280 with a non-null _actuatorValue.
     */
    @Test
    void sameAs_withNonNullActuatorValue_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        SIV280Value valueDouble = mock(SIV280Value.class);

        Actuator actuator1 = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator1.setValue(valueDouble);
        Actuator actuator2 = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
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
        SIV280Value valueDouble = mock(SIV280Value.class);

        Actuator actuator = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
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

        Actuator actuator = new SIV280(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act
        int actualHashCode = actuator.hashCode();
        // Assert
        assertEquals(ActuatorIDDouble.hashCode(), actualHashCode);
    }

}

