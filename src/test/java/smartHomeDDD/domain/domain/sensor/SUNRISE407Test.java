package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.sensor.SUNRISE407;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The SUNRISE407Test class contains test cases for the SUNRISE407 sensor.
 * The tests cover the following scenarios:
 * - valid SUNRISE407 sensor creation
 * - comparing SUNRISE407 with itself
 * - comparing two equal SUNRISE407 sensors
 * - comparing two different SUNRISE407 sensors
 * - comparing SUNRISE407 with a different object
 * - comparing two identical SUNRISE407's
 * - comparing two identical SUNRISE407's with different IDs
 * - comparing two SUNRISE407's from different devices
 * - comparing two SUNRISE407's with different sensor models
 * - successful retrieval value of SUNRISE407's for a given date
 * - invalid GPS coordinates
 * - invalid date
 * - hashCode method in the SUNRISE407 class
 */

class SUNRISE407Test {
    /**
     * Test case for valid SUNRISE407 sensor creation.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        // Act
        SUNRISE407 sunrise407 = new SUNRISE407(deviceId, sensorModelID, sensorID);

        // Assert
        assertNotNull(sunrise407);
        assertEquals(deviceId, sunrise407.getDeviceID());
        assertEquals(sensorModelID, sunrise407.getSensorModelID());
        assertEquals(sensorID, sunrise407.identity());
    }

    /**
     * Test case for comparing SUNRISE407 with itself.
     */
    @Test
    void whenComparingItself_shouldReturnTrue_() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNRISE407 sunrise407 = new SUNRISE407(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = sunrise407.equals(sunrise407);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing SUNRISE407's with the same ID.
     */
    @Test
    void whenComparingTwoEqualSUNRISE407_shouldReturnTrue() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNRISE407 sunrise4071 = new SUNRISE407(deviceId, sensorModelID, sensorID);
        SUNRISE407 sunrise4072 = new SUNRISE407(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = sunrise4071.equals(sunrise4072);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two different SUNRISE407 sensors.
     */

    @Test
    void whenComparingTwoDifferentSUNRISE407_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorID sensorID1 = mock(SensorID.class);
        SUNRISE407 sunrise4071 = new SUNRISE407(deviceId1, sensorModelID1, sensorID1);

        DeviceId deviceId2 = mock(DeviceId.class);
        SensorModelID sensorModelID2 = mock(SensorModelID.class);
        SensorID sensorID2 = mock(SensorID.class);
        SUNRISE407 sunrise4072 = new SUNRISE407(deviceId2, sensorModelID2, sensorID2);

        // Act
        boolean result = sunrise4071.equals(sunrise4072);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing SUNRISE407 with a different object.
     */
    @Test
    void whenComparingSUNRISE407WithDifferentObject_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNRISE407 sunrise407 = new SUNRISE407(deviceId, sensorModelID, sensorID);
        Object obj = new Object();

        // Act
        boolean result = sunrise407.equals(obj);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two identical SUNRISE407's.
     */
    @Test
    void whenComparingTwoEqualSUNRISE407SameAs_shouldReturnTrue() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNRISE407 sunrise4071 = new SUNRISE407(deviceId, sensorModelID, sensorID);
        SUNRISE407 sunrise4072 = new SUNRISE407(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = sunrise4071.sameAs(sunrise4072);

        // Assert
        assertTrue(result);
    }

    /**
     * Test case for comparing two identical SUNRISE407's with different IDs.
     */

    @Test
    void comparingTwoEqualSUNRISE407WithDifferentIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorID sensorID1 = mock(SensorID.class);
        SensorID sensorID2 = mock(SensorID.class);

        SUNRISE407 sunrise4071 = new SUNRISE407(deviceId1, sensorModelID1, sensorID1);
        SUNRISE407 sunrise4072 = new SUNRISE407(deviceId1, sensorModelID1, sensorID2);

        // Act
        boolean result = sunrise4071.sameAs(sunrise4072);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two SUNRISE407's from different devices.
     */

    @Test
    void whenComparingSUNRISE407FromDifferentDevices_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorID sensorID1 = mock(SensorID.class);

        SUNRISE407 sunrise4071 = new SUNRISE407(deviceId1, sensorModelID1, sensorID1);
        SUNRISE407 sunrise4072 = new SUNRISE407(deviceId2, sensorModelID1, sensorID1);

        // Act
        boolean result = sunrise4071.sameAs(sunrise4072);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for comparing two SUNRISE407's with different sensor models.
     */

    @Test
    void whenComparingSUNRISE407FromWithDifferentModels_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorModelID sensorModelID2 = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        SUNRISE407 sunrise4071 = new SUNRISE407(deviceId, sensorModelID1, sensorID);
        SUNRISE407 sunrise4072 = new SUNRISE407(deviceId, sensorModelID2, sensorID);

        // Act
        boolean result = sunrise4071.sameAs(sunrise4072);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case for successful retrieval value of SUNRISE407's for a given date.
     */

    @Test
    void successfullyGetValue_ShouldReturnInstant() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        SUNRISE407 sunrise407 = new SUNRISE407(deviceId, sensorModelID, sensorID);
        Latitude lat = new Latitude(50.0);
        Longitude lon = new Longitude(110.0);
        GPSCoordinates gps = new GPSCoordinates(lat, lon);

        // Act
        LocalDate date = LocalDate.of(2024, 2, 28);
        Value actual = sunrise407.getValue(date, gps);

        // Assert
        LocalTime expectedReading = LocalTime.of(7, 0, 28);
        Value expected = new SUNRISE407Value(expectedReading);
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
        SUNRISE407 sunrise407 = new SUNRISE407(deviceId, sensorModelID, sensorID);
        GPSCoordinates gps = mock(GPSCoordinates.class);
        when(gps.getLatitude()).thenReturn(25.0);
        when(gps.getLongitude()).thenReturn(115.0);

        // Act
        LocalDate date = LocalDate.of(2024, 2, 28);
        Value actual = sunrise407.getValue(date, gps);

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
        SUNRISE407 sunrise407 = new SUNRISE407(deviceId, sensorModelID, sensorID);
        GPSCoordinates gps = mock(GPSCoordinates.class);
        when(gps.getLatitude()).thenReturn(25.0);
        when(gps.getLongitude()).thenReturn(115.0);

        // Act
        LocalDate date = LocalDate.of(2024, 2, 28);
        Value actual = sunrise407.getValue(date, gps);

        // Assert
        assertNull(actual);
    }

    /**
     * Test case for the hashCode method in the SUNRISE407 class.
     * This test verifies that the hashCode method returns the correct hash code.
     * The test uses Mockito to create a mock SensorID, DeviceId, and SensorModelID.
     * It then asserts that the actual hash code returned by the hashCode method is equal to the hash code of the mock SensorID.
     */
    @Test
    void hashCode_shouldReturnCorrectHashCode() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        SUNRISE407 sunrise407 = new SUNRISE407(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        int actualHashCode = sunrise407.hashCode();

        // Assert
        assertEquals(Objects.hash(sensorIdMock), actualHashCode);
    }
}