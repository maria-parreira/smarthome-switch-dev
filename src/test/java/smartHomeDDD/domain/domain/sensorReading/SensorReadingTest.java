package smartHomeDDD.domain.domain.sensorReading;

import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.Reading;
import smartHomeDDD.domain.valueobject.SensorReadingID;
import smartHomeDDD.domain.valueobject.SensorID;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the SensorReading class.
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
 * - Comparing two equal SensorReading objects with different IDs
 * - Comparing two equal SensorReading objects with the same ID
 * - Comparing the same SensorReading object
 * - Comparing the hash code of two equal SensorReading objects
 */
class SensorReadingTest
{
    /**
     * Test to ensure that the SensorReading constructor creates a SensorReading object when given valid arguments.
     */
    @Test
    void validCAP200SensorReading_shouldReturnObject()
    {
        // Arrange
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        // Act
        SensorReading sensorReading = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        // Assert
        assertNotNull(sensorReading);
        assertEquals(SensorReadingID, sensorReading.identity());
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
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act
        SensorReadingID result = sensorReading.identity();
        // Assert
        assertEquals(SensorReadingID, result);
    }

    /**
     * Test to ensure that the sameAs method returns true when an object SensorReading is being compared to itself.
     */
    @Test
    void validSensorReadingBeingComparedWithItself_ShouldReturnEqualObject()
    {
        // Arrange
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act & Assert
        assertEquals(sensorReading, sensorReading);
    }

    /**
     * Test to ensure that the sameAs method returns true when comparing two equal SensorReading Objects.
     */
    @Test
    void equalSensorReadingsComparison_ShouldReturnEqual()
    {
        // Arrange
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        when(timeStamp.equals(any(Timestamp.class))).thenReturn(true);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act
        boolean isEquals = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertTrue(isEquals);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two different SensorReading Objects.
     */
    @Test
    void nonEqualSensorReadingsComparison_ShouldReturnDifferentSensorReadings()
    {
        // Arrange
        SensorReadingID SensorReadingID1 = mock(SensorReadingID.class);
        SensorReadingID SensorReadingID2 = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID1, value, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID2, value, deviceID, sensorID, timeStamp);
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
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
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
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value1 = mock(Reading.class);
        Reading value2 = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID, value1, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID, value2, deviceID, sensorID, timeStamp);
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
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID1 = mock(DeviceId.class);
        DeviceId deviceID2 = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID, value, deviceID1, sensorID, timeStamp);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID, value, deviceID2, sensorID, timeStamp);
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
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID1 = mock(SensorID.class);
        SensorID sensorID2 = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID, value, deviceID, sensorID1, timeStamp);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID, value, deviceID, sensorID2, timeStamp);
        // Act
        boolean result = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the sameAs method returns false when comparing two SensorReading Objects with different timestamps.
     */
    @Test
    void twoSensorReadingsWithDifferentTimestamps_shouldReturnFalse()
    {
        // Arrange
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp1 = mock(Timestamp.class);
        Timestamp timeStamp2 = mock(Timestamp.class);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp1);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp2);
        // Act
        boolean result = sensorReading1.sameAs(sensorReading2);
        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the equals method returns false when comparing two equal SensorReading Objects with different IDs.
     */
    @Test
    void shouldReturnFalse_WhenComparingTwoEqualSensorReadingsWithDifferentID() {
        // Arrange
        SensorReadingID SensorReadingID1 = mock(SensorReadingID.class);
        SensorReadingID SensorReadingID2 = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID1, value, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID2, value, deviceID, sensorID, timeStamp);
        // Act
        boolean result = sensorReading1.equals(sensorReading2);
        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the equals method returns false when comparing two different object types.
     */
    @Test
    void shouldReturnFalse_WhenComparingDifferentObjects() {
        // Arrange
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        Object obj = mock(Object.class);
        // Act
        boolean result = sensorReading.equals(obj);
        // Assert
        assertFalse(result);

    }

    /**
     * Test to ensure that the equals method returns true when comparing two equal SensorReading Objects with the same ID.
     */
    @Test
    void shouldReturnTrue_WhenComparingTwoEqualSensorReadingsWithSameID() {
        // Arrange
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act
        boolean result = sensorReading1.equals(sensorReading2);
        // Assert
        assertTrue(result);

    }

    /**
     * Test to ensure that the equals method returns true when comparing the same SensorReading object.
     */
    @Test
    void shouldReturnTrue_WhenComparingTheSameObject() {
        // Arrange
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act
        boolean result = sensorReading.equals(sensorReading);
        // Assert
        assertTrue(result);
    }

    /**
     * Test to ensure two equal sensorReading objects have the same hash code.
     */
    @Test
    void equalSensorReadings_ShouldReturnEqualHashCode()
    {
        // Arrange
        SensorReadingID SensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp timeStamp = mock(Timestamp.class);
        SensorReading sensorReading1 = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        SensorReading sensorReading2 = new SensorReading(SensorReadingID, value, deviceID, sensorID, timeStamp);
        // Act
        int hashCode1 = sensorReading1.hashCode();
        int hashCode2 = sensorReading2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}