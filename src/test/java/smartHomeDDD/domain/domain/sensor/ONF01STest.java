package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.ONF01S;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ONF01SValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The ONF01STest class conducts unit tests on the ONF01S class. The tests validate the following:
 * - Successful instantiation of the ONF01S object with valid parameters.
 * - The 'sameAs' method correctly identifies identical objects.
 * - The 'sameAs' method correctly identifies objects with differing sensor IDs, Device IDs, and Sensor Model IDs.
 * - The 'getValue' method accurately retrieves the correct value for readings 1 through 4.
 * - The 'equals' method correctly identifies non-identical sensor instances.
 * - The 'equals' method correctly identifies identical sensor instances.
 */
class ONF01STest {
    /**
     * Test that the constructor creates a new instance of ONF01S with valid parameters.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        // Act
        ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);

        // Assert
        assertNotNull(onf01s);
        assertEquals(onf01s.identity(), sensorIDDouble);
        assertEquals(onf01s.getDeviceID(), deviceIdDouble);
        assertEquals(onf01s.getSensorModelID(), sensorModelIDDouble);
    }

    /**
     * Test that the sameAs method returns true when the object is the same.
     */

    @Test
    void sameObject_shouldReturnTrue() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);

        // Act
        boolean result = onf01s.sameAs(onf01s);

        // Assert
        assertTrue(result);
    }

    /**
     * Test that the sameAs method returns false when the object has a different sensor ID.
     */
    @Test
    void differentIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        SensorID sensorIDDouble2 = mock(SensorID.class);
        ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        ONF01S onf01sv2 = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble2);

        // Act
        boolean result = onf01s.sameAs(onf01sv2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test that the sameAs method returns false when the object has a different Device ID.
     */

    @Test
    void differentDeviceIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        ONF01S onf01sv2 = new ONF01S(deviceIdDouble2, sensorModelIDDouble, sensorIDDouble);

        // Act
        boolean result = onf01s.sameAs(onf01sv2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test that the sameAs method returns false when the object has a different Sensor Model ID.
     */

    @Test
    void differentSensorModelIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorModelID sensorModelIDDouble2 = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);
        ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        ONF01S onf01sv2 = new ONF01S(deviceIdDouble, sensorModelIDDouble2, sensorIDDouble);

        // Act
        boolean result = onf01s.sameAs(onf01sv2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test that the getValue method returns the correct value for reading no. 1.
     */
    @Test
    void getValueForReading1_shouldReturnSensorValue() {
        // Arrange
        Integer readingNumber = 1;
        String expectedValue = "ON";
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<ONF01SValue> ONF01SValueDouble = mockConstruction(ONF01SValue.class, (mock, context) -> {
            String valueReading = (String) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading);
        })) {
            ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            onf01s.setValue(readingNumber);

            // Act
            ONF01SValue value = (ONF01SValue) onf01s.getValue();

            // Assert
            List<ONF01SValue> values = ONF01SValueDouble.constructed();
            smartHomeDDD.domain.valueobject.ONF01SValue mockedValueConstructed = values.get(0);

            assertEquals(1, values.size());
            assertEquals(value, mockedValueConstructed);
            assertEquals(expectedValue, value.toString());
        }
    }

    /**
     * Test that the getValue method returns the correct value for reading no. 2.
     */
    @Test
    void getValueForReading2_shouldReturnSensorValue() {
        // Arrange
        Integer readingNumber = 2;
        String expectedValue = "OFF";
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<ONF01SValue> ONF01SValueDouble = mockConstruction(ONF01SValue.class, (mock, context) ->
                when(mock.toString()).thenReturn(expectedValue))) {
            ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            onf01s.setValue(readingNumber);

            // Act
            ONF01SValue value = (ONF01SValue) onf01s.getValue();

            // Assert
            List<ONF01SValue> values = ONF01SValueDouble.constructed();
            smartHomeDDD.domain.valueobject.ONF01SValue mockedValueConstructed = values.get(0);

            assertEquals(1, values.size());
            assertEquals(value, mockedValueConstructed);
            assertEquals(expectedValue, value.toString());
        }
    }

    /**
     * Test that the getValue method returns the correct value for reading no. 3.
     */
    @Test
    void getValueForReading3_shouldReturnSensorValue() {
        // Arrange
        Integer readingNumber = 3;
        String expectedValue = "OFF";
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<ONF01SValue> ONF01SValueDouble = mockConstruction(ONF01SValue.class, (mock, context) ->
                when(mock.toString()).thenReturn(expectedValue))) {
            ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            onf01s.setValue(readingNumber);

            // Act
            ONF01SValue value = (ONF01SValue) onf01s.getValue();

            // Assert
            List<ONF01SValue> values = ONF01SValueDouble.constructed();
            smartHomeDDD.domain.valueobject.ONF01SValue mockedValueConstructed = values.get(0);

            assertEquals(1, values.size());
            assertEquals(value, mockedValueConstructed);
            assertEquals(expectedValue, value.toString());
        }
    }

    /**
     * Test that the getValue method returns the correct value for reading no. 4.
     */
    @Test
    void getValueForReading4_shouldReturnSensorValue() {
        // Arrange
        Integer readingNumber = 4;
        String expectedValue = "ON";
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<ONF01SValue> ONF01SValueDouble = mockConstruction(ONF01SValue.class, (mock, context) ->
                when(mock.toString()).thenReturn(expectedValue))) {
            ONF01S onf01s = new ONF01S(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            onf01s.setValue(readingNumber);

            // Act
            ONF01SValue value = (ONF01SValue) onf01s.getValue();

            // Assert
            List<ONF01SValue> values = ONF01SValueDouble.constructed();
            smartHomeDDD.domain.valueobject.ONF01SValue mockedValueConstructed = values.get(0);

            assertEquals(1, values.size());
            assertEquals(value, mockedValueConstructed);
            assertEquals(expectedValue, value.toString());
        }
    }

    /**
     * Tests if the equals method of the ONF01S sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ONF01S mySensor = new ONF01S(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the equals method of the ONF01S sensor returns true
     * when the same sensor instance is compared.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareSensorToItself() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ONF01S mySensor = new ONF01S(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the equals method of the ONF01S sensor returns true
     * when compared to duplicate instance.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareDuplicateSensor() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ONF01S mySensor = new ONF01S(deviceId, sensorModelID, sensorIdDouble);
        ONF01S mySensor2 = new ONF01S(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the equals method of the ONF01S sensor returns false
     * when compared to sensor with different sensorID.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareSensorWithDifferentSensorID() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ONF01S mySensor = new ONF01S(deviceId, sensorModelID, sensorIdDouble);
        ONF01S mySensor2 = new ONF01S(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Test to ensure that the hashCode method of the ONF01S class returns the correct hash code.
     * This test uses Mockito to create a mock SensorID, DeviceId, and SensorModelID.
     * It then asserts that the actual hash code returned by the hashCode method is equal to the hash code of the mock SensorID.
     */
    @Test
    void hashCode_shouldReturnCorrectHashCode() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        ONF01S onf01s = new ONF01S(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        int actualHashCode = onf01s.hashCode();

        // Assert
        assertEquals(Objects.hash(sensorIdMock), actualHashCode);
    }

    /**
     * Test to ensure that the sameAs method of the ONF01S class returns false when the object compared is not an instance of ONF01S.
     * This test uses Mockito to create a mock SensorID, DeviceId, and SensorModelID, and a String object that is not an instance of ONF01S.
     * It then asserts that the sameAs method returns false when comparing the ONF01S object to the String object.
     */
    @Test
    void sameAs_shouldReturnFalseWhenObjectIsNotONF01S() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        ONF01S onf01s = new ONF01S(deviceIdMock, sensorModelIdMock, sensorIdMock);
        String notONF01S = "Not an ONF01S object";

        // Act
        boolean isSame = onf01s.sameAs(notONF01S);

        // Assert
        assertFalse(isSame);
    }
}
