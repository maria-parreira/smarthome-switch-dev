package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.AVPC500W;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.AVPC500WValue;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This test class covers various functionalities and edge cases of the AVPC500W sensor implementation.
 * <p>
 * Test Cases:
 * - Validation of sensor creation: Ensures that a valid sensor object is created and returned as a non-null entity.
 * - Identity verification: Verifies if the identity method returns the expected SensorID.
 * - Average power consumption calculation: Tests the behavior of the getValue method to ensure accurate computation
 * of the average power consumption within specified time intervals.
 * - No readings within a period: Tests scenarios where the start and end times for retrieving values are equal,
 * ensuring that the getValue method returns no readings in such cases.
 * - Equality comparison: Tests the behavior of the equals and sameAs methods for comparing AVPC500W sensor instances,
 * ensuring correctness in identifying equal and non-equal instances.
 */
class AVPC500WTest {

    /**
     * Tests if a valid sensor object is created and returned as a non-null object.
     */
    @Test
    void validSensor_shouldReturnNonNullObject() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.toString()).thenReturn("device1");
        SensorModelID sensorModelID = mock(SensorModelID.class);
        when(sensorModelID.toString()).thenReturn("model1");
        SensorID sensorID = mock(SensorID.class);

        // Act
        AVPC500W mySensor = new AVPC500W(deviceId, sensorModelID, sensorID);
        // Assert
        assertNotNull(mySensor);
        assertEquals("model1", mySensor.getSensorModelID().toString());
        assertEquals("device1", mySensor.getDeviceID().toString());
    }

    /**
     * Tests if the sensor's identity method returns the expected SensorID.
     */

    @Test
    void validIdentity_ShouldReturnEqualsIDs() {
        // Arrange
        SensorID expectedSensorID = mock(SensorID.class);
        when(expectedSensorID.toString()).thenReturn("1");

        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        AVPC500W mySensor = new AVPC500W(deviceId, sensorModelID, expectedSensorID);
        // Act
        SensorID actualId = mySensor.identity();
        // Assert
        assertEquals(expectedSensorID, actualId);
    }

    /**
     * Tests if the AVPC500W sensor constructor throws an IllegalArgumentException
     * when a null SensorModelID is provided as input.
     */

    @Test
    void validIdentity_ShouldReturnSensorID() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        when(sensorIdDouble.toString()).thenReturn("1");

        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        AVPC500W mySensor = new AVPC500W(deviceId, sensorModelID, sensorIdDouble);
        // Act
        SensorID actualId = mySensor.identity();
        // Assert
        assertEquals(sensorIdDouble, actualId);
    }


    /**
     * Test case for the sameAs method in the AVPC500W class when comparing a AVPC500W object with a non-APC500W object.
     * The test verifies that the sameAs method returns false when a AVPC500W object is compared with a non-PC500W object.
     */
    @Test
    public void nonAVPC500WObject_ShouldReturnFalse() {

        // Arrange
        AVPC500W avpc500W = new AVPC500W(mock(DeviceId.class),
                mock(SensorModelID.class), mock(SensorID.class));

        Object obj = new Object();

        //Act + Assert
        boolean isEquals = avpc500W.sameAs(obj);
        assertFalse(isEquals);
    }


    /**
     * Tests the behavior of the equals method when comparing two AVPC500W sensor instances
     * with same DeviceId and SensorModelID and the same SensorID.
     * The method should return true, indicating that the sensors are considered equal.
     */
    @Test
    void sameAs_sameDeviceAndModel_SameSensorID_ShouldReturnTrue() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);

        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        AVPC500W avpc500w1 = new AVPC500W(deviceId, sensorModelID, sensorIdDouble);
        AVPC500W avpc500w2 = new AVPC500W(deviceId, sensorModelID, sensorIdDouble);

        // Act and Assert
        boolean isEquals = avpc500w1.equals(avpc500w2);
        assertTrue(isEquals);
    }

    /**
     * Tests if the getValue method of the AVPC500W sensor returns the average power consumption within a specified period.
     * The test verifies the behavior when provided with valid start and end times.
     */

    @Test
    void ShouldReturnAveragePowerConsumption_WithinPeriod_ValidStartEndTime() {

        // Arrange
        SensorID sensorId = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(15, 30);

        // Act

        try (MockedConstruction<AVPC500WValue> AVPC500WValueDouble = mockConstruction(AVPC500WValue.class, (mock, context) -> {
            Double readings = (Double) context.arguments().get(0);
            when(mock.toString()).thenReturn(readings.toString());
        })) {
            AVPC500W apc500w = new AVPC500W(deviceId, sensorModelID, sensorId);
            String averagePowerConsumption = apc500w.getValue(start, end).toString();

            // Assert
            List<AVPC500WValue> values = AVPC500WValueDouble.constructed();
            AVPC500WValue myValue = AVPC500WValueDouble.constructed().get(0);
            assertEquals(1, values.size());//create only one APC500WValue double
            assertEquals("138.875", averagePowerConsumption);
            assertEquals(myValue.toString(), averagePowerConsumption);
        }
    }

    /**
     * Tests if the getValue method of the AVPC500W sensor returns no readings within a specified period
     * when the start and end times are equal.
     */
    @Test
    void ShouldReturnNoReadings_WithinPeriod_StartEndTimeEqual() {

        // Arrange
        SensorID sensorId = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        LocalTime start = LocalTime.of(3, 0);
        LocalTime end = LocalTime.of(3, 0);

        // Act
        try (MockedConstruction<AVPC500WValue> APC500WValueDouble = mockConstruction(AVPC500WValue.class, (mock, context) -> {
            Double readings = (Double) context.arguments().get(0);
            when(mock.toString()).thenReturn(readings.toString());
        })) {
            AVPC500W apc500w = new AVPC500W(deviceId, sensorModelID, sensorId);
            String averagePowerConsumption = apc500w.getValue(start, end).toString();

            // Assert
            List<AVPC500WValue> values = APC500WValueDouble.constructed();
            AVPC500WValue myValue = APC500WValueDouble.constructed().get(0);
            assertEquals(1, values.size());
            assertEquals("0.0", averagePowerConsumption);
            assertEquals(myValue.toString(), averagePowerConsumption);
        }
    }


    /**
     * Tests if the sameAs method of the AVPC500W sensor returns false
     * when comparing two AVPC500W sensor instances with different sensorModelIDs.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareSensorsWithDifferentModelIDs() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorModelID sensorModelID2 = mock(SensorModelID.class);

        AVPC500W mySensor = new AVPC500W(deviceId, sensorModelID, sensorIdDouble);
        AVPC500W mySensor2 = new AVPC500W(deviceId, sensorModelID2, sensorIdDouble);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }


    /**
     * Tests if the sameAs method of the AVPC500W sensor returns false
     * when comparing two AVPC500W sensor instances with different DeviceIDs.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareSensorsWithDifferentDeviceIDs() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);


        AVPC500W mySensor = new AVPC500W(deviceId, sensorModelID, sensorIdDouble);
        AVPC500W mySensor2 = new AVPC500W(deviceId2, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the sameAs method of the AVPC500W sensor returns false
     * when comparing two AVPC500W sensor instances with different SensorIDs.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareSensorsWithDifferentSensorIDs() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);


        AVPC500W mySensor = new AVPC500W(deviceId, sensorModelID, sensorIdDouble);
        AVPC500W mySensor2 = new AVPC500W(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the equals method of the AVPC500W sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        AVPC500W mySensor = new AVPC500W(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the equals method of the AVPC500W sensor returns true
     * when the same sensor instance is compared.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareSensorToItself() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        AVPC500W mySensor = new AVPC500W(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }


    @Test
    void hashCode_sameSensorID_ShouldReturnSameHashCode() {
        // Arrange
        SensorID sensorId = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        AVPC500W avpc500w1 = new AVPC500W(deviceId, sensorModelID, sensorId);
        AVPC500W avpc500w2 = new AVPC500W(deviceId, sensorModelID, sensorId);

        // Act
        int hashCode1 = avpc500w1.hashCode();
        int hashCode2 = avpc500w2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}
