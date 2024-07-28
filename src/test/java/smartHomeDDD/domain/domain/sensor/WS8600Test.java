package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensor.WS8600;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.WS8600Value;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The WS8600Test class is a test class for the WS8600 class.
 * It provides tests for the following scenarios:
 * - The constructor creates a new instance of WS8600 with valid parameters.
 * - The sameAs method returns true when the object is the same.
 * - The sameAs method returns false when the object has a different sensor ID.
 * - The sameAs method returns false when the object has a different Device ID.
 * - The sameAs method returns false when the object has a different Sensor Model ID.
 * - The getValue method returns the correct value for reading no. 1.
 * - The getValue method returns the correct value for reading no. 2.
 * - The equals method returns false when different sensor instances are compared.
 * - The equals method returns true when the same sensor instance is compared.
 * - The equals method returns true when compared to duplicate instance.
 * - The equals method returns false when compared to sensor with different sensorID.
 * - The hashCode method returns the correct hash code.
 * - The sameAs method returns false when the object passed is not an instance of WS8600.
 */
class WS8600Test {

    /**
     * Test that the constructor creates a new instance of WS8600 with valid parameters.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {
        // Arrange
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        // Act
        WS8600 wf8600 = new WS8600(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);

        // Assert
        assertNotNull(wf8600);
        assertEquals(wf8600.identity(), sensorIDDouble);
        assertEquals(wf8600.getDeviceID(), deviceIdDouble);
        assertEquals(wf8600.getSensorModelID(), sensorModelIDDouble);
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
        WS8600 wf8600 = new WS8600(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);

        // Act
        boolean result = wf8600.sameAs(wf8600);

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
        WS8600 wf8600 = new WS8600(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        WS8600 wf8600v2 = new WS8600(deviceIdDouble, sensorModelIDDouble, sensorIDDouble2);

        // Act
        boolean result = wf8600.sameAs(wf8600v2);

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
        WS8600 wf8600 = new WS8600(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        WS8600 wf8600v2 = new WS8600(deviceIdDouble2, sensorModelIDDouble, sensorIDDouble);

        // Act
        boolean result = wf8600.sameAs(wf8600v2);

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
        WS8600 wf8600 = new WS8600(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
        WS8600 wf8600v2 = new WS8600(deviceIdDouble, sensorModelIDDouble2, sensorIDDouble);

        // Act
        boolean result = wf8600.sameAs(wf8600v2);

        // Assert
        assertFalse(result);
    }

    /**
     * Test that the getValue method returns the correct value for reading no. 1.
     */
    @Test
    void getValueForReading1_shouldReturnSensorValue() {
        // Arrange
        Integer readingID = 1;
        String expectedValue = "Wind Speed: 10.5 Km/h; Wind Direction: 0.0 rad - East";
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<WS8600Value> WS8600ValueDouble = mockConstruction(WS8600Value.class, (mock, context) -> {
            Double speed = (Double) context.arguments().get(0);
            Double direction = (Double) context.arguments().get(1);
            when(mock.toString()).thenReturn("Wind Speed: " + speed + " Km/h; Wind Direction: " + direction + " rad - East");
        })) {
            WS8600 ws8600 = new WS8600(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            ws8600.setValue(readingID);

            // Act
            WS8600Value value = (WS8600Value) ws8600.getValue();

            // Assert
            List<WS8600Value> values = WS8600ValueDouble.constructed();

            assertEquals(1, values.size());
            assertEquals(expectedValue, value.toString());
        }
    }

    /**
     * Test that the getValue method returns the correct value for reading no. 2.
     */
    @Test
    void getValueForReading2_shouldReturnSensorValue() {
        // Arrange
        Integer readingID = 2;
        String expectedValue = "Wind Speed: 20.5 Km/h; Wind Direction: 4.714 rad - South East";
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIDDouble = mock(SensorModelID.class);
        SensorID sensorIDDouble = mock(SensorID.class);

        try (MockedConstruction<WS8600Value> WS8600ValueDouble = mockConstruction(WS8600Value.class, (mock, context) -> {
            Double speed = (Double) context.arguments().get(0);
            Double direction = (Double) context.arguments().get(1);
            when(mock.toString()).thenReturn("Wind Speed: " + speed + " Km/h; Wind Direction: " + direction + " rad - South East");
        })) {
            WS8600 ws8600 = new WS8600(deviceIdDouble, sensorModelIDDouble, sensorIDDouble);
            ws8600.setValue(readingID);

            // Act
            WS8600Value value = (WS8600Value) ws8600.getValue();

            // Assert
            List<WS8600Value> values = WS8600ValueDouble.constructed();

            assertEquals(1, values.size());
            assertEquals(expectedValue, value.toString());
        }
    }


    /**
     * Tests if the equals method of the WS8600 sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        WS8600 mySensor = new WS8600(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }


    /**
     * Tests if the equals method of the WS8600 sensor returns true
     * when the same sensor instance is compared.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareSensorToItself() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        WS8600 mySensor = new WS8600(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the equals method of the WS8600 sensor returns true
     * when compared to duplicate instance.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareDuplicateSensor() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        WS8600 mySensor = new WS8600(deviceId, sensorModelID, sensorIdDouble);
        WS8600 mySensor2 = new WS8600(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the equals method of the WS8600 sensor returns false
     * when compared to sensor with different sensorID.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareSensorWithDifferentSensorID() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        WS8600 mySensor = new WS8600(deviceId, sensorModelID, sensorIdDouble);
        WS8600 mySensor2 = new WS8600(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }


    /**
     * Test case for the hashCode method in the WS8600 class.
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
        WS8600 ws8600 = new WS8600(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        int actualHashCode = ws8600.hashCode();

        // Assert
        assertEquals(Objects.hash(sensorIdMock), actualHashCode);
    }

    /**
     * Test case for the "sameAs" method in the WS8600 class.
     * This test verifies that the method returns false when the object passed is not an instance of WS8600.
     */
    @Test
    void sameAs_withNonWS8600Object_shouldReturnFalse() {
        // Arrange
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        SensorID sensorIdMock = mock(SensorID.class);
        WS8600 ws8600 = new WS8600(deviceIdMock, sensorModelIdMock, sensorIdMock);
        Object nonWS8600Object = new Object();

        // Act
        boolean result = ws8600.sameAs(nonWS8600Object);

        // Assert
        assertFalse(result);
    }
}