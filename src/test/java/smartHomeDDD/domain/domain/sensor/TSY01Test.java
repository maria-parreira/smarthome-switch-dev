package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensor.TSY01;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.TSY01Value;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class tests the TSY01 class.
 * It provides tests for the following scenarios:
 * - Test that the constructor creates a new instance of TSY01 with valid parameters
 * - Test that the sameAs method returns true when the object is the same
 * - Test that the sameAs method returns false when the sensor ID is different
 * - Test that the sameAs method returns false when the Device ID is different
 * - Test that the sameAs method returns false when the Sensor Model ID is different
 * - Test that the getValue method returns the correct value for reading 1
 * - Test that the getValue method returns the correct value for reading 2
 * - Test that the getValue method returns the correct value for reading 3
 * - Test that the equals method returns false when different sensor instances are compared
 * - Test that the sameAs method returns false when different sensor instances are compared
 * - Test that the equals method returns true when the same sensor instance is compared
 * - Test that the equals method returns true when compared to duplicate instance
 * - Test that the equals method returns false when compared to sensor with different sensorID
 * - Test that the hashCode method returns the correct hash code
 */
class TSY01Test {
    /**
     * Test that the constructor creates a new instance of TSY01 with valid parameters.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        // Act
        TSY01 tsy01 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);

        // Assert
        assertNotNull(tsy01);
        assertEquals(tsy01.identity(), sensorIDDouble);
        assertEquals(tsy01.getDeviceID(), deviceIdDouble);
        assertEquals(tsy01.getSensorModelID(), sensorModelIDDouble);
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
        TSY01 tsy01 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);

        // Act
        boolean result = tsy01.sameAs(tsy01);

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
        TSY01 tsy01 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        TSY01 tsy01v2 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble2);

        // Act
        boolean result = tsy01.sameAs(tsy01v2);

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
        TSY01 tsy01 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        TSY01 tsy01v2 = new TSY01(deviceIdDouble2, sensorModelIDDouble, sensorIDDouble);

        // Act
        boolean result = tsy01.sameAs(tsy01v2);

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
        TSY01 tsy01 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        TSY01 tsy01v2 = new TSY01(deviceIdDouble, sensorModelIDDouble2, sensorIDDouble);

        // Act
        boolean result = tsy01.sameAs(tsy01v2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test that the getValue method returns the correct value for reading 1.
     */

    @Test
    void getValueForReading1_shouldReturnSensorValue() {
        // Arrange
        Integer readingID = 1;
        int expectedValue = 15;
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);


        try (MockedConstruction<TSY01Value> TSY01ValueDouble = mockConstruction(TSY01Value.class, (mock, context) -> {
            Integer valueReading = (Integer) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {
            TSY01 tsy01 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            tsy01.setValue(readingID);

            // Act
            TSY01Value value = (TSY01Value) tsy01.getValue();

            // Assert
            List<smartHomeDDD.domain.valueobject.TSY01Value> values = TSY01ValueDouble.constructed();
            smartHomeDDD.domain.valueobject.TSY01Value mockedValue = TSY01ValueDouble.constructed().get(0);

            assertEquals(1, values.size());
            assertEquals(value, mockedValue);
            assertEquals(Integer.toString(expectedValue), value.toString());
        }
    }

    /**
     * Test that the getValue method returns the correct value for reading 2.
     */

    @Test
    void getValueForReading2_shouldReturnSensorValue() {
        // Arrange
        Integer readingID = 2;
        int expectedValue = 85;
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);


        try (MockedConstruction<TSY01Value> TSY01ValueDouble = mockConstruction(TSY01Value.class, (mock, context) -> {
            Integer valueReading = (Integer) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {
            TSY01 tsy01 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            tsy01.setValue(readingID);

            // Act
            TSY01Value value = (TSY01Value) tsy01.getValue();

            // Assert
            List<smartHomeDDD.domain.valueobject.TSY01Value> values = TSY01ValueDouble.constructed();
            smartHomeDDD.domain.valueobject.TSY01Value mockedValue = TSY01ValueDouble.constructed().get(0);

            assertEquals(1, values.size());
            assertEquals(value, mockedValue);
            assertEquals(Integer.toString(expectedValue), value.toString());
        }
    }

    /**
     * Test that the getValue method returns the correct value for reading 3.
     */
    @Test
    void getValueForReading3_shouldReturnSensorValue() {
        // Arrange
        Integer readingID = 3;
        int expectedValue = 65;
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);


        try (MockedConstruction<TSY01Value> TSY01ValueDouble = mockConstruction(TSY01Value.class, (mock, context) -> {
            Integer valueReading = (Integer) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {
            TSY01 tsy01 = new TSY01(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            tsy01.setValue(readingID);

            // Act
            TSY01Value value = (TSY01Value) tsy01.getValue();

            // Assert
            List<smartHomeDDD.domain.valueobject.TSY01Value> values = TSY01ValueDouble.constructed();
            smartHomeDDD.domain.valueobject.TSY01Value mockedValue = TSY01ValueDouble.constructed().get(0);

            assertEquals(1, values.size());
            assertEquals(value, mockedValue);
            assertEquals(Integer.toString(expectedValue), value.toString());
        }
    }

    /**
     * Tests if the equals method of the TSY01 sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        TSY01 mySensor = new TSY01(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the sameAs method of the TSY01 sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        TSY01 mySensor = new TSY01(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the equals method of the TSY01 sensor returns true
     * when the same sensor instance is compared.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareSensorToItself() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        TSY01 mySensor = new TSY01(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the equals method of the TSY01 sensor returns true
     * when compared to duplicate instance.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareDuplicateSensor() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        TSY01 mySensor = new TSY01(deviceId, sensorModelID, sensorIdDouble);
        TSY01 mySensor2 = new TSY01(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the equals method of the TSY01 sensor returns false
     * when compared to sensor with different sensorID.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareSensorWithDifferentSensorID() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        TSY01 mySensor = new TSY01(deviceId, sensorModelID, sensorIdDouble);
        TSY01 mySensor2 = new TSY01(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Test case for the hashCode method in the TSY01 class.
     * This test verifies that the hashCode method returns the correct hash code.
     * The test uses Mockito to create a mock SensorID.
     * It then asserts that the actual hash code returned by the hashCode method is equal to the hash code of the mock SensorID.
     */
    @Test
    void hashCode_shouldReturnCorrectHashCode() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        TSY01 tsy01 = new TSY01(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        int actualHashCode = tsy01.hashCode();

        // Assert
        assertEquals(Objects.hash(sensorIdMock), actualHashCode);
    }
}