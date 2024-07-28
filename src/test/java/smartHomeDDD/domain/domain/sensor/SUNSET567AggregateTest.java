package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.sensor.ImplFactorySensor;
import smartHomeDDD.domain.sensor.SUNSET567;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

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

class SUNSET567AggregateTest {
    /**
     * Test case for valid SUNSET567 sensor creation.
     */
    @Test
    void validParameters_shouldCreateNewInstance() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNSET567");
        SensorID sensorID = new SensorID("S1");

        // Act
        SUNSET567 sunset567 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

        // Assert
        assertNotNull(sunset567);
        assertEquals(deviceId, sunset567.getDeviceID());
        assertEquals(sensorModelID, sunset567.getSensorModelID());
        assertEquals(sensorID, sunset567.identity());
    }

    //
    //equals
    //

    /**
     * Test case for comparing SUNSET567 with itself.
     */
    @Test
    void whenComparingItself_shouldReturnTrue_() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNSET567");
        SensorID sensorID = new SensorID("S1");
        SUNSET567 sunset567 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNSET567");
        SensorID sensorID = new SensorID("S1");
        SUNSET567 sunset5671 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        SUNSET567 sunset5672 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId1 = new DeviceId("D1");
        SensorModelID sensorModelID1 = new SensorModelID("SUNSET567");
        SensorID sensorID1 = new SensorID("S1");
        SUNSET567 sunset5671 = (SUNSET567) implFactorySensor.createSensor(deviceId1, sensorModelID1, sensorID1);

        DeviceId deviceId2 = new DeviceId("D2");
        SensorModelID sensorModelID2 = new SensorModelID("SUNSET567");
        SensorID sensorID2 = new SensorID("S2");
        SUNSET567 sunset5672 = (SUNSET567) implFactorySensor.createSensor(deviceId2, sensorModelID2, sensorID2);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNSET567");
        SensorID sensorID = new SensorID("S1");
        SUNSET567 sunset567 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        Object obj = new Object();

        // Act
        boolean result = sunset567.equals(obj);

        // Assert
        assertFalse(result);
    }

    //
    //sameAs
    //

    /**
     * Test case for comparing two identical SUNSET567's.
     */
    @Test
    void whenComparingTwoEqualSUNSET567SameAs_shouldReturnTrue() {
        // Arrange
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNSET567");
        SensorID sensorID = new SensorID("S1");
        SUNSET567 sunset5671 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        SUNSET567 sunset5672 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId1 = new DeviceId("D1");
        SensorModelID sensorModelID1 = new SensorModelID("SUNSET567");
        SensorID sensorID1 = new SensorID("S1");
        SensorID sensorID2 = new SensorID("S2");

        SUNSET567 sunset5671 = (SUNSET567) implFactorySensor.createSensor(deviceId1, sensorModelID1, sensorID1);
        SUNSET567 sunset5672 = (SUNSET567) implFactorySensor.createSensor(deviceId1, sensorModelID1, sensorID2);
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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId1 = new DeviceId("D1");
        DeviceId deviceId2 = new DeviceId("D2");
        SensorModelID sensorModelID1 = new SensorModelID("SUNSET567");
        SensorID sensorID1 = new SensorID("S1");

        SUNSET567 sunset5671 = (SUNSET567) implFactorySensor.createSensor(deviceId1, sensorModelID1, sensorID1);
        SUNSET567 sunset5672 = (SUNSET567) implFactorySensor.createSensor(deviceId2, sensorModelID1, sensorID1);

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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = new SensorModelID("SUNSET567");
        SensorID sensorID = new SensorID("1");
        SUNSET567 sunset567 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNSET567");
        SensorID sensorID = new SensorID("S1");
        SUNSET567 sunset567 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
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
        ImplFactorySensor implFactorySensor = new ImplFactorySensor();
        DeviceId deviceId = new DeviceId("D1");
        SensorModelID sensorModelID = new SensorModelID("SUNSET567");
        SensorID sensorID = new SensorID("S1");
        SUNSET567 sunset567 = (SUNSET567) implFactorySensor.createSensor(deviceId, sensorModelID, sensorID);
        GPSCoordinates gps = mock(GPSCoordinates.class);
        when(gps.getLatitude()).thenReturn(25.0);
        when(gps.getLongitude()).thenReturn(115.0);

        // Act
        LocalDate date = LocalDate.of(2024, 2, 28);
        Value actual = sunset567.getValue(date, gps);

        // Assert
        assertNull(actual);
    }
}
