package smartHomeDDD.domain.domain.sensorReading;

import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.ImplFactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the SensorReading aggregate.
 * This class tests the conditions for the following scenarios:
 * - creating a valid instance of SensorReading
 * - returning the SensorReadingID
 * - Comparing a SensorReading object with itself
 * - Comparing two equal SensorReading objects
 * - Comparing two different SensorReading objects
 * - Comparing a SensorReading object with a different object
 * - Comparing two SensorReading objects with different values
 * - Comparing two SensorReading objects with different deviceIDs
 * - Comparing two SensorReading objects with different sensorIDs
 * - Comparing two SensorReading objects with different timestamps
 */
class SensorReadingAggregateTest
{
    /**
     * Test to ensure that the SensorReading constructor creates a SensorReading object when given valid arguments.
     */
    @Test
    void validCAP200SensorReading_shouldReturnObject()
    {
        // Arrange
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value = new Reading("15");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        // Act
        SensorReading sensorReading = new SensorReading(sensorReadingID, value, deviceID, sensorID, timeStamp);
        // Assert
        assertNotNull(sensorReading);
        assertEquals(sensorReadingID, sensorReading.identity());
        assertEquals(value, sensorReading.getReading());
        assertEquals(deviceID, sensorReading.getDeviceID());
        assertEquals(sensorID, sensorReading.getSensorID());
        assertEquals(timeStamp, sensorReading.getTimeStamp());
    }

    /**
     * Test to ensure that the identity method returns the sensorReading id.
     */
    @Test
    void validSensorReadingID_ShouldReturnSensorReadingIDWhenGettingIdentity()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value = new Reading("On");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SensorReading sensorReading = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act
        SensorReadingID result = sensorReading.identity();
        // Assert
        assertEquals(sensorReadingID, result);
    }

    /**
     * Test to ensure that the sameAs method returns true when an object SensorReading is being compared to itself.
     */
    @Test
    void validSensorReadingBeingComparedWithItself_ShouldReturnEqualObject()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value = new Reading("15");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SensorReading sensorReading = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act
        boolean result = sensorReading.equals(sensorReading);
        // Assert
        assertTrue(result);
    }

    /**
     * Test to ensure that the sameAs method returns true when comparing two equal SensorReading Objects.
     */
    @Test
    void equalSensorReadingsComparison_ShouldReturnEqual()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value = new Reading("15");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SensorReading sensorReading1 = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act
        boolean result = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertTrue(result);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two different SensorReading Objects.
     */
    @Test
    void nonEqualSensorReadingsComparison_ShouldReturnDifferentSensorReadings()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID1 = new SensorReadingID("sensorReadingID01");
        SensorReadingID sensorReadingID2 = new SensorReadingID("sensorReadingID02");
        Reading value = new Reading("15");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SensorReading sensorReading1 = factorySensorReading.createSensorReading(sensorReadingID1, value, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = factorySensorReading.createSensorReading(sensorReadingID2, value, deviceID, sensorID, timeStamp);
        // Act
        boolean result = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing a SensorReading Object to a different object.
     */
    @Test
    void nonEqualSensorReadingsComparison_SameAsShouldReturnFalse()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value = new Reading("15");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SensorReading sensorReading = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID, timeStamp);
        Object obj = new Object();
        // Act
        boolean result = sensorReading.sameAs(obj);
        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two SensorReading Objects with different values.
     */
    @Test
    void twoSensorReadingsWithDifferentValues_shouldReturnFalse()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value1 = new Reading("15");
        Reading value2 = new Reading("20");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SensorReading sensorReading1 = factorySensorReading.createSensorReading(sensorReadingID, value1, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = factorySensorReading.createSensorReading(sensorReadingID, value2, deviceID, sensorID, timeStamp);
        // Act
        boolean result = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two SensorReading Objects with different deviceIDs.
     */
    @Test
    void twoSensorReadingsWithDifferentDeviceIDs_shouldReturnFalse()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value = new Reading("15");
        DeviceId deviceID1 = new DeviceId("deviceID01");
        DeviceId deviceID2 = new DeviceId("deviceID02");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SensorReading sensorReading1 = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID1, sensorID, timeStamp);
        SensorReading sensorReading2 = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID2, sensorID, timeStamp);
        // Act
        boolean result = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two SensorReading Objects with different sensorIDs.
     */
    @Test
    void twoSensorReadingsWithDifferentSensorIDs_shouldReturnFalse()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value = new Reading("15");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID1 = new SensorID("sensorID01");
        SensorID sensorID2 = new SensorID("sensorID02");
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        SensorReading sensorReading1 = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID1, timeStamp);
        SensorReading sensorReading2 = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID2, timeStamp);
        // Act
        boolean result = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two SensorReading Objects with different timestamps.
     */
    @Deprecated
    @Test
    void twoSensorReadingsWithDifferentTimestamps_shouldReturnFalse()
    {
        // Arrange
        FactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = new SensorReadingID("sensorReadingID01");
        Reading value = new Reading("15");
        DeviceId deviceID = new DeviceId("deviceID01");
        SensorID sensorID = new SensorID("sensorID01");
        Timestamp timeStamp1 = new Timestamp(2021, 1, 1, 1, 1, 1, 1);
        Timestamp timeStamp2 = new Timestamp(2022, 9, 5, 3, 10, 1, 2);
        SensorReading sensorReading1 = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID, timeStamp1);
        SensorReading sensorReading2 = factorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID, timeStamp2);
        // Act
        boolean result = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertFalse(result);
    }
}