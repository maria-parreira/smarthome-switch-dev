package smartHomeDDD.domain.domain.actuator;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.ONF01A;
import smartHomeDDD.domain.actuator.OPNCL0100;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is the test class for the OPNCL0100 actuator. It lists the following scenarios:
 * - Successful instantiation of the OPNCL0100 object with mock dependencies.
 * - Equality of ID when the identity method is called, ensuring it returns the correct ActuatorID.
 * - Non-equality of two OPNCL0100 actuator Objects when only the Value differs.
 * - Non-equality of two OPNCL0100 actuator Objects when only the ActuatorID differs.
 * - Non-equality of two OPNCL0100 actuator Objects when only the DeviceID differs.
 * - Non-equality of two OPNCL0100 actuator Objects when only the ActuatorModelID differs.
 * - Change of the actuator Value (via setValue method) of a valid value;
 * - Change of the actuator Value (via setValue method) of valid frontier values (lower and higher);
 * - Change of the actuator Value (via setValue method) of an invalid value;
 */
class OPNCL0100Test {
    /**
     * Tests a successful instantiation of the OPNCL0100 actuator Object
     */
    @Test
    void validActuator_shouldReturnNonNullObject(){
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
         // Act
        Actuator actuator = new OPNCL0100(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Assert
        assertNotNull(actuator);
        assertEquals(actuatorIDDouble, actuator.identity());
        assertEquals(actuatorModelIDDouble, actuator.getActuatorModelID());
        assertEquals(deviceIDDouble, actuator.getDeviceID());
    }
    /**
     * Tests that the identity method correctly retrieves the ActuatorID used during instantiation.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs() {
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertEquals(ActuatorIDDouble, actuator.identity());
    }
    /**
     * Tests that two OPNCL0100 actuator objects with the same state are considered equivalent by the Equals method.
     */
    @Test
    void EqualsOPNCL0100Objects_ShouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertEquals(actuator1, actuator2);
    }
    /**
     * Tests that two OPNCL0100 actuator objects with different actuator IDs are not considered equivalent.
     */
    @Test
    void OPNCL0100ObjectsWithDifferentIDs_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }
    /**
     * Tests that two OPNCL0100 actuator objects with different Device IDs are not considered equivalent.
     */
    @Test
    void OPNCL0100ObjectsWithDifferentDeviceIDs_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble2, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }
    /**
     * Tests that two OPNCL0100 actuator objects with different actuatorModel IDs are not considered equivalent.
     */
    @Test
    void OPNCL0100ObjectsWithDifferentActuatorModelIDs_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble2);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }
    /**
     * Test case for setting a valid value (between 0 and 100) to an instance of OPNCL0100.
     */
    @Test
    void validSetOPNCL0100Value_ShouldReturnValid() {
        // arrange
        String expectedValueString = "3";
        OPNCL0100Value newOpncl0100Value = mock(OPNCL0100Value.class);
        when(newOpncl0100Value.toString()).thenReturn(expectedValueString);
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);

        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator.setValue(newOpncl0100Value);
        // assert
        assertEquals(actuator.getValue().toString(), expectedValueString);
    }
    /**
     * Test case for setting a frontier value (0) to an instance of OPNCL0100.
     */
    @Test
    void validSetZeroValueOPNCL0100_ShouldReturnValid() {
        // arrange
        String expectedValueString = "0";
        OPNCL0100Value newOpncl0100Value = mock(OPNCL0100Value.class);
        when(newOpncl0100Value.toString()).thenReturn(expectedValueString);
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);

        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        actuator.setValue(newOpncl0100Value);

        // assert
        assertEquals(actuator.getValue().toString(), expectedValueString);
    }
    /**
     * Test case for setting a frontier value (100) to an instance of OPNCL0100.
     */
    @Test
    void validSetHundredValueOPNCL0100_ShouldReturnValid() {
        // arrange
        int expectedValue = 100;
        OPNCL0100Value newOpncl0100Value = mock(OPNCL0100Value.class);
        when(newOpncl0100Value.getValue()).thenReturn(expectedValue);
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);

        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        OPNCL0100 opncl0100 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        opncl0100.setValue(newOpncl0100Value);
        // assert
        assertEquals(opncl0100.getValue().getValue(), expectedValue);
    }
    /**
     * Test case for setting an invalid value to an instance of OPNCL0100 (below zero).
     * It should return null.
     * @throws IllegalArgumentException if the value is not an instance of OPNCL0100Value
     */
    @Test
    void validOPNCL0100_SetInvalidValueBelowZero_shouldReturnNull() throws IllegalArgumentException {
        // arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);

        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> actuator.setValue(new OPNCL0100Value(-1)));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Value out of range";

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
    /**
     * Test case for setting an invalid value to an instance of OPNCL0100 (above hundred).
     */
    @Test
    void validOPNCL0100_SetInvalidValueAboveHundred_shouldReturnNull(){
        // arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);

        DeviceId deviceIDDouble = mock(DeviceId.class);

        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> actuator.setValue(new OPNCL0100Value(101)));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Value out of range";

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
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

    @Test
    void sameAs_withSameStateObjects_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertTrue(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that two OPNCL0100 actuator objects with different actuator IDs are not considered equivalent.
     */
    @Test
    void sameAs_withDifferentActuatorID_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that two OPNCL0100 actuator objects with different Device IDs are not considered equivalent.
     */
    @Test
    void sameAs_withDifferentDeviceID_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble2, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that two OPNCL0100 actuator objects with different actuatorModel IDs are not considered equivalent.
     */
    @Test
    void sameAs_withDifferentActuatorModelID_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble2);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that two OPNCL0100 actuator objects with null ActuatorValue are considered equivalent by the sameAs method.
     */
    @Test
    void sameAs_withNullActuatorValue_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertTrue(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that the setValue method returns null when the provided value is not an instance of OPNCL0100Value.
     */
    @Test
    void setValue_withNonOPNCL0100Value_shouldReturnNull(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Value nonOPNCL0100Value = mock(Value.class);

        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act
        Value result = actuator.setValue(nonOPNCL0100Value);
        // Assert
        assertNull(result);
    }

    @Test
    void sameAs_withNonOPNCL0100Object_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Object nonOPNCL0100Object = new Object();

        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertFalse(actuator.sameAs(nonOPNCL0100Object));
    }

    /**
     * Tests that the sameAs method returns true when the ActuatorValue is null and the ActuatorID, DeviceID, and ActuatorModelID are the same.
     */
    @Test
    void sameAs_withNullActuatorValueAndSameIDs_shouldReturnTrue(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertTrue(actuator1.sameAs(actuator2));
    }

    /**
     * Tests that the sameAs method returns false when the ActuatorValue is null and the ActuatorID, DeviceID, or ActuatorModelID are different.
     */
    @Test
    void sameAs_withNullActuatorValueAndDifferentIDs_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble1 = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble1 = mock(DeviceId.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble1 = mock(ActuatorModelID.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble1, deviceIDDouble1, actuatorModelIDDouble1);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble2, deviceIDDouble2, actuatorModelIDDouble2);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    @Test
    void sameAs_withNonMatchingConditions_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble1 = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble1 = mock(DeviceId.class);
        DeviceId deviceIDDouble2 = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble1 = mock(ActuatorModelID.class);
        ActuatorModelID actuatorModelIDDouble2 = mock(ActuatorModelID.class);
        OPNCL0100Value actuatorValue = mock(OPNCL0100Value.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble1, deviceIDDouble1, actuatorModelIDDouble1);
        actuator1.setValue(actuatorValue);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble2, deviceIDDouble2, actuatorModelIDDouble2);
        // Act and Assert
        assertFalse(actuator1.sameAs(actuator2));
    }

    @Test
    void hashCode_withSameActuatorID_shouldReturnSameHashCode(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act
        int hashCode1 = actuator1.hashCode();
        int hashCode2 = actuator2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2);
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

        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertEquals(actuator, actuator);
    }

    /**
     * Tests that the equals method returns false when the provided object is an instance of OPNCL0100 with a different ActuatorID.
     */
    @Test
    void equals_withDifferentActuatorID_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble1 = mock(ActuatorID.class);
        ActuatorID ActuatorIDDouble2 = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        Actuator actuator1 = new OPNCL0100(ActuatorIDDouble1, deviceIDDouble, actuatorModelIDDouble);
        Actuator actuator2 = new OPNCL0100(ActuatorIDDouble2, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertNotEquals(actuator1, actuator2);
    }

    /**
     * Tests that the equals method returns false when the provided object is not an instance of OPNCL0100.
     */
    @Test
    void equals_withNonOPNCL0100Object_shouldReturnFalse(){
        // Arrange
        ActuatorID ActuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        Object nonOPNCL0100Object = new Object();

        Actuator actuator = new OPNCL0100(ActuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        // Act and Assert
        assertNotEquals(actuator, nonOPNCL0100Object);
    }

    /**
     * Test to verify that the setValue method correctly sets a valid value and returns it.
     * The test creates an OPNCL0100 object and sets a valid value to it.
     * It then checks if the returned value from the setValue method is the same as the set value.
     * If the setValue method is correctly implemented, this test should pass.
     */
    @Test
    void setValue_withValidValue_shouldReturnSetValue() {
        // Arrange
        ActuatorID actuatorIDDouble = mock(ActuatorID.class);
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);
        OPNCL0100Value validValue = new OPNCL0100Value(50);

        // Act
        Actuator actuator = new OPNCL0100(actuatorIDDouble, deviceIDDouble, actuatorModelIDDouble);
        Value returnedValue = actuator.setValue(validValue);

        // Assert
        assertEquals(validValue, returnedValue);
    }

    /**
     * Test to verify that the hashCode method returns the correct hash code for a valid OPNCL0100 object.
     * The test creates an OPNCL0100 object and then checks if the hashCode method returns the hash code of the ActuatorID.
     * If the hashCode method is correctly implemented, this test should pass.
     */
    @Test
    void hashCode_shouldReturnActuatorIDHashCode() {
        // Arrange
        ActuatorID actuatorID = new ActuatorID("knownValue"); // Replace with actual constructor or method call
        DeviceId deviceIDDouble = mock(DeviceId.class);
        ActuatorModelID actuatorModelIDDouble = mock(ActuatorModelID.class);

        // Act
        Actuator actuator = new OPNCL0100(actuatorID, deviceIDDouble, actuatorModelIDDouble);
        int hashCode = actuator.hashCode();

        // Assert
        assertEquals(actuatorID.hashCode(), hashCode);
    }

}