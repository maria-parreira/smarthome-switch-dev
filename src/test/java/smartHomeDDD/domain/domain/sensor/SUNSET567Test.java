package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.sensor.SUNSET567;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The SUNSET567Test class contains test cases for the SUNRISE407 sensor.
 * The tests cover the following scenarios:
 * - Valid SUNSET567 sensor creation
 * - Comparing SUNSET567 with itself
 * - Comparing two equal SUNSET567 sensors
 * - Comparing two different SUNSET567 sensors
 * - Comparing SUNSET567 with a different object
 * - Comparing two identical SUNSET567 sensors
 * - Comparing two identical SUNSET567 sensors with different IDs
 * - Comparing two SUNSET567 sensors from different devices
 * - Comparing two SUNSET567 sensors with different sensor models
 * - Successful retrieval value of SUNSET567 for a given date
 * - Invalid GPS coordinates
 * - Invalid date
 * - The "hashCode" method in the SUNSET567 class
 */

class SUNSET567Test {
    /**
     * Test case for valid SUNSET567 sensor creation.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        // Act
        SUNSET567 sunset567 = new SUNSET567(deviceId, sensorModelID, sensorID);

        // Assert
        assertNotNull(sunset567);
        assertEquals(deviceId, sunset567.getDeviceID());
        assertEquals(sensorModelID, sunset567.getSensorModelID());
        assertEquals(sensorID, sunset567.identity());
    }

    /**
     * Test case for comparing SUNSET567 with itself.
     */
    @Test
    void whenComparingItself_shouldReturnTrue_() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNSET567 sunset567 = new SUNSET567(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = sunset567.equals(sunset567);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing SUNSET567's with the same ID.
     */
    @Test
    void whenComparingTwoEqualSUNSET567_shouldReturnTrue() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNSET567 sunset5671 = new SUNSET567(deviceId, sensorModelID, sensorID);
        SUNSET567 sunset5672 = new SUNSET567(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = sunset5671.equals(sunset5672);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two different SUNSET567 sensors.
     */

    @Test
    void whenComparingTwoDifferentSUNSET567_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorID sensorID1 = mock(SensorID.class);
        SUNSET567 sunset5671 = new SUNSET567(deviceId1, sensorModelID1, sensorID1);

        DeviceId deviceId2 = mock(DeviceId.class);
        SensorModelID sensorModelID2 = mock(SensorModelID.class);
        SensorID sensorID2 = mock(SensorID.class);
        SUNSET567 sunset5672 = new SUNSET567(deviceId2, sensorModelID2, sensorID2);

        // Act
        boolean result = sunset5671.equals(sunset5672);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing SUNSET567 with a different object.
     */
    @Test
    void whenComparingSUNSET567WithDifferentObject_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNSET567 sunset567 = new SUNSET567(deviceId, sensorModelID, sensorID);
        Object obj = new Object();

        // Act
        boolean result = sunset567.equals(obj);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two identical SUNSET567's.
     */
    @Test
    void whenComparingTwoEqualSUNSET567SameAs_shouldReturnTrue() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNSET567 sunset5671 = new SUNSET567(deviceId, sensorModelID, sensorID);
        SUNSET567 sunset5672 = new SUNSET567(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = sunset5671.sameAs(sunset5672);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two identical SUNSET567's with different IDs.
     */

    @Test
    void comparingTwoEqualSUNSET567WithDifferentIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorID sensorID1 = mock(SensorID.class);
        SensorID sensorID2 = mock(SensorID.class);

        SUNSET567 sunset5671 = new SUNSET567(deviceId1, sensorModelID1, sensorID1);
        SUNSET567 sunset5672 = new SUNSET567(deviceId1, sensorModelID1, sensorID2);

        // Act
        boolean result = sunset5671.sameAs(sunset5672);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two SUNSET567's from different devices.
     */

    @Test
    void whenComparingSUNSET567FromDifferentDevices_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorID sensorID1 = mock(SensorID.class);

        SUNSET567 sunset5671 = new SUNSET567(deviceId1, sensorModelID1, sensorID1);
        SUNSET567 sunset5672 = new SUNSET567(deviceId2, sensorModelID1, sensorID1);

        // Act
        boolean result = sunset5671.sameAs(sunset5672);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two SUNSET567's with different sensor models.
     */

    @Test
    void whenComparingSUNSET567FromWithDifferentModels_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorModelID sensorModelID2 = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        SUNSET567 sunset5671 = new SUNSET567(deviceId, sensorModelID1, sensorID);
        SUNSET567 sunset5672 = new SUNSET567(deviceId, sensorModelID2, sensorID);

        // Act
        boolean result = sunset5671.sameAs(sunset5672);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for successful retrieval value of SUNSET567's for a given date.
     */

    @Test
    void successfullyGetValue_ShouldReturnInstant() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNSET567 sunset567 = new SUNSET567(deviceId, sensorModelID, sensorID);
        Latitude lat = new Latitude(55.0);
        Longitude lon = new Longitude(115.0);
        GPSCoordinates gps = new GPSCoordinates(lat, lon);

        // Act
        LocalDate date = LocalDate.of(2024, 2, 28);
        Value actual = sunset567.getValue(date, gps);

        // Assert
        LocalTime expectedReading = LocalTime.of(18, 0, 28);
        SUNSET567Value expected = new SUNSET567Value(expectedReading);
        assertEquals(expected.toString(), actual.toString());

    }

    /**
     * Test case for invalid GPS coordinates.
     */

    @Test
    void invalidGPS_ShouldReturnNull() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNSET567 sunset567 = new SUNSET567(deviceId, sensorModelID, sensorID);
        GPSCoordinates gps = mock(GPSCoordinates.class);
        when(gps.getLatitude()).thenReturn(25.0);
        when(gps.getLongitude()).thenReturn(115.0);

        // Act
        LocalDate date = LocalDate.of(2024, 2, 28);
        Value actual = sunset567.getValue(date, gps);

        // Assert
        assertNull(actual);
    }

    /**
     * Test case for invalid date.
     */

    @Test
    void invalidDate_ShouldReturnNull() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNSET567 sunset567 = new SUNSET567(deviceId, sensorModelID, sensorID);
        GPSCoordinates gps = mock(GPSCoordinates.class);
        when(gps.getLatitude()).thenReturn(25.0);
        when(gps.getLongitude()).thenReturn(115.0);

        // Act
        LocalDate date = LocalDate.of(2024, 2, 28);
        Value actual = sunset567.getValue(date, gps);

        // Assert
        assertNull(actual);
    }

    /**
     * Test case for the "hashCode" method in the SUNSET567 class.
     * This test verifies that the method returns the correct hash code for a SUNSET567 object.
     */
    @Test
    void hashCode_shouldReturnCorrectHashCode() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        SUNSET567 sunset567 = new SUNSET567(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        int actualHashCode = sunset567.hashCode();

        // Assert
        assertEquals(Objects.hash(sensorIdMock), actualHashCode);
    }

}
