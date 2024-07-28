package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensor.SUNRISE407;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

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
 * - successful retrieval value of SUNRISE407's for a given date
 * - invalid GPS coordinates
 * - invalid date
 */
class SUNRISE407AggregateTest {
    /**
     * Test case for valid SUNRISE407 sensor creation.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");

        // Act
        SUNRISE407 sunrise407 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

        // Assert
        assertNotNull(sunrise407);
        assertEquals(deviceId, sunrise407.getDeviceID());
        assertEquals(sensorModelID, sunrise407.getSensorModelID());
        assertEquals(sensorID, sunrise407.identity());
    }

    //
    //equals
    //

    /**
     * Test case for comparing SUNRISE407 with itself.
     */
    @Test
    void whenComparingItself_shouldReturnTrue_() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");
        SUNRISE407 sunrise407 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = sunrise407.equals(sunrise407);

        // Assert
        assertTrue(true);
    }

    /**
     * Test case for comparing SUNRISE407's with the same ID.
     */

    @Test
    void whenComparingTwoEqualSUNRISE407_shouldReturnTrue() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");
        SUNRISE407 sunrise4071 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        SUNRISE407 sunrise4072 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID1 = new SensorID("S1");
        SensorID sensorID2 = new SensorID("S2");
        SUNRISE407 sunrise4071 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID1);
        SUNRISE407 sunrise4072 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID2);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");
        SUNRISE407 sunrise407 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        Object obj = new Object();

        // Act
        boolean result = sunrise407.equals(obj);

        // Assert
        assertFalse(result);
    }

    //
    //sameAs
    //

    /**
     * Test case for comparing two identical SUNRISE407's.
     */
    @Test
    void whenComparingTwoEqualSUNRISE407SameAs_shouldReturnTrue() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");
        SUNRISE407 sunrise4071 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        SUNRISE407 sunrise4072 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId1 = new DeviceId("D1");
        SensorModelID sensorModelID1 = new SensorModelID("SUNRISE407");
        SensorID sensorID1 = new SensorID("S1");
        SensorID sensorID2 = new SensorID("S2");

        SUNRISE407 sunrise4071 = (SUNRISE407) implFactorySensor.createSensor(deviceId1, sensorModelID1, sensorID1);
        SUNRISE407 sunrise4072 = (SUNRISE407) implFactorySensor.createSensor(deviceId1, sensorModelID1, sensorID2);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId1 = new DeviceId("D1");
        DeviceId deviceId2 = new DeviceId("D2");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");

        SUNRISE407 sunrise4071 = (SUNRISE407) implFactorySensor.createSensor(deviceId1, sensorModelID, sensorID);
        SUNRISE407 sunrise4072 = (SUNRISE407) implFactorySensor.createSensor(deviceId2, sensorModelID, sensorID);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");
        SUNRISE407 sunrise407 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

        // Mock GPSCoordinates
        GPSCoordinates gps = mock(GPSCoordinates.class);
        when(gps.getLatitude()).thenReturn(55.0);
        when(gps.getLongitude()).thenReturn(115.0);

        // Mock _data map
        Map<LocalDate, Map<GPSCoordinates, LocalTime>> data = new HashMap<>();
        Map<GPSCoordinates, LocalTime> dayData = new HashMap<>();
        dayData.put(gps, LocalTime.of(7, 0, 28));
        data.put(LocalDate.of(2024, 2, 28), dayData);
        sunrise407.setDataMap(data); // Set the mock _data map

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");
        SUNRISE407 sunrise407 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNRISE407");
        SensorID sensorID = new SensorID("S1");
        SUNRISE407 sunrise407 = (SUNRISE407) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        GPSCoordinates gps = mock(GPSCoordinates.class);
        when(gps.getLatitude()).thenReturn(55.0);
        when(gps.getLongitude()).thenReturn(115.0);

        // Act
        LocalDate date = LocalDate.of(2024, 2, 28);
        Value actual = sunrise407.getValue(date, gps);

        // Assert
        assertNull(actual);
    }
}
