package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.ECA300K;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ECA300KValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * This test class covers various functionalities and edge cases of the ECA300K sensor implementation.
 * Test Cases:
 * - Validation of sensor creation: Ensures that a valid sensor object is created and returned as a non-null entity.
 * - Identity verification: Verifies if the identity method returns the expected SensorID.
 * - Average power consumption calculation: Tests the behavior of the getValue method to ensure accurate computation
 *   of the energy consumption within specified time intervals.
 * - No readings within a period: Tests scenarios where the start and end times for retrieving values are equal,
 *   ensuring that the getValue method returns no readings in such cases.
 * - Equality comparison: Tests the behavior of the sameAs methods for comparing ECA300K sensor instances,
 *   ensuring correctness in identifying equal and non-equal instances.
 */

class ECA300KTest {

    /**
     * Tests if a valid ECA300K sensor object is created and returned as a non-null object.
     * This test verifies that an ECA300K sensor object is created successfully with the provided parameters and is not null.
     * It also checks if the DeviceId and SensorModelID of the created sensor match the expected values.
     */
    @Test
    void validSensor_shouldReturnNonNullObject(){
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.toString()).thenReturn("device2");
        SensorModelID sensorModelID = mock(SensorModelID.class);
        when(sensorModelID.toString()).thenReturn("model2");
        SensorID sensorID = mock(SensorID.class);
        // Act
        ECA300K mySensor = new ECA300K(deviceId, sensorModelID, sensorID);
        // Assert
        assertNotNull(mySensor);
        assertEquals("model2", mySensor.getSensorModelID().toString());
        assertEquals("device2", mySensor.getDeviceID().toString());
    }

    /**
     * Tests if the sensor's identity method returns the expected SensorID.
     * This test verifies that the identity method of the ECA300K sensor returns the expected SensorID.
     */
    @Test
    void validIdentity_ShouldReturnEqualsIDs(){
        // Arrange
        SensorID expectedSensorID = mock(SensorID.class);
        when(expectedSensorID.toString()).thenReturn("1");

        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ECA300K mySensor = new ECA300K(deviceId, sensorModelID, expectedSensorID);
        // Act
        SensorID actualId = mySensor.identity();
        // Assert
        assertEquals(expectedSensorID, actualId);
    }

    /**
     * Test case for the sameAs method in the ECA300K class when comparing an ECA300K object with a non-ECA300K object.
     * The test verifies that the sameAs method returns false when an ECA300K object is compared with a non-ECA300K object.
     */
    @Test
    public void nonEEC300KObject_ShouldReturnFalse() {

        // Arrange
        ECA300K eec300K = new ECA300K(mock(DeviceId.class),
                mock(SensorModelID.class), mock(SensorID.class));

        Object obj = new Object();

        //Act + Assert
        assertFalse(eec300K.sameAs(obj));
    }

    /**
     * Tests the behavior of the sameAs method when comparing two ECA300K sensor instances
     * with the same parameters.
     * The method should return true, indicating that the sensors are considered equal.
     */
    @Test
    void sameAs_SameObject_ShouldReturnTrue() {

        // Arrange

        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ECA300K eec300K1 = new ECA300K(deviceId,sensorModelID, sensorIdDouble);
        ECA300K eec300K2 = new ECA300K(deviceId, sensorModelID, sensorIdDouble);

        // Act and Assert
        assertTrue(eec300K1.sameAs(eec300K2));
    }

    /**
     * Tests the behavior of the sameAs method when comparing two ECA300K sensor instances
     * with different DeviceId and SensorModelID but the same SensorID.
     * The method should return false, indicating that the sensors are not considered equal.
     */
    @Test
    void sameAs_DifferentDeviceAndModel_SameSensorID_ShouldReturnFalse() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);

        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        DeviceId deviceId2 = mock(DeviceId.class);
        SensorModelID sensorModelID2 = mock(SensorModelID.class);

        ECA300K eca300K1 = new ECA300K(deviceId,sensorModelID, sensorIdDouble);
        ECA300K eca300K2 = new ECA300K(deviceId2,sensorModelID2, sensorIdDouble);

        // Act and Assert
        boolean isEquals = eca300K1.sameAs(eca300K2);
        assertFalse(isEquals);
    }

    /**
     * Tests the behavior of the sameAs method when comparing two ECA300K sensor instances
     * with same DeviceId and SensorModelID and the same SensorID.
     * The method should return true, indicating that the sensors are considered equal.
     */

    @Test
    void sameAs_SameDeviceAndModel_SameSensorID_ShouldReturnTrue() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);

        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ECA300K eca300K1 = new ECA300K(deviceId,sensorModelID, sensorIdDouble);
        ECA300K eca300K2 = new ECA300K(deviceId,sensorModelID, sensorIdDouble);

        // Act and Assert
        boolean isEquals = eca300K1.sameAs(eca300K2);
        assertTrue(isEquals);
    }

    /**
     * Tests if the getValue method of the ECA300K sensor returns the energy consumption per hour within a specified period.
     * The test verifies the behavior when provided with valid start and end times.
     */
    @Test
    void ShouldReturnEnergyConsumptionPerHour_WithinPeriod_ValidStartEndTime() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);

        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(11, 30);

        // Act

        try(MockedConstruction<ECA300KValue> ECA300KValueDouble = mockConstruction(ECA300KValue.class,(mock, context)-> {
            Double readings = (Double) context.arguments().get(0);
            when(mock.toString()).thenReturn(readings.toString());
        })) {
            ECA300K eca300K = new ECA300K(deviceIdDouble,sensorModelIdDouble,sensorIdDouble);
            String energyConsumption = eca300K.getValue(start,end).toString();

            // Assert
            List<ECA300KValue> values = ECA300KValueDouble.constructed();
            ECA300KValue myValue = ECA300KValueDouble.constructed().get(0);
            assertEquals(1, values.size());//create only one ECA300KValue double
            assertEquals("10.0",energyConsumption);
            assertEquals(myValue.toString(),energyConsumption);
        }
    }

    /**
     * Tests if the getValue method of the ECA300K sensor returns the energy consumption per hour within a specified period
     * when the start and end times are equal.
     */
    @Test
    void ShouldReturnEnergyConsumptionPerHour_WithinPeriod_StartEndTimeSame() {

        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceIdDouble = mock(DeviceId.class);
        SensorModelID sensorModelIdDouble = mock(SensorModelID.class);

        LocalTime start = LocalTime.of(11, 30);
        LocalTime end = LocalTime.of(11, 30);

        // Act

        try(MockedConstruction<ECA300KValue> ECA300KValueDouble = mockConstruction(ECA300KValue.class,(mock, context)-> {
            Double readings = (Double) context.arguments().get(0);
            when(mock.toString()).thenReturn(readings.toString());
        })) {
            ECA300K eca300K = new ECA300K(deviceIdDouble,sensorModelIdDouble,sensorIdDouble);
            String energyConsumption = eca300K.getValue(start,end).toString();

            // Assert
            List<ECA300KValue> values = ECA300KValueDouble.constructed();
            ECA300KValue myValue = ECA300KValueDouble.constructed().get(0);
            assertEquals(1, values.size());//create only one ECA300KValue double
            assertEquals("0.0",energyConsumption);
            assertEquals(myValue.toString(),energyConsumption);
        }
    }

    /**
     * Tests if the equals method of the ECA300K sensor returns false
     * when different sensor instances are compared.
     */
    @Test
    void ShouldReturnFalse_WhenUsingEqualsToCompareDifferentClassSensors() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ECA300K mySensor = new ECA300K(deviceId, sensorModelID, sensorIdDouble);
        Sensor mySensor2 = mock(Sensor.class);

        // Act
        boolean actual = mySensor.equals(mySensor2);

        // Assert
        assertFalse(actual);
    }

    /**
     * Tests if the equals method of the ECA300K sensor returns true
     * when the same sensor instance is compared.
     */
    @Test
    void ShouldReturnTrue_WhenUsingEqualsToCompareSensorToItself() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        ECA300K mySensor = new ECA300K(deviceId, sensorModelID, sensorIdDouble);

        // Act
        boolean actual = mySensor.equals(mySensor);

        // Assert
        assertTrue(actual);
    }

    /**
     * Tests if the sameAs method of the ECA300K sensor returns false
     * when comparing two ECA300K sensor instances with different SensorIDs.
     */
    @Test
    void ShouldReturnFalse_WhenUsingSameAsToCompareSensorsWithDifferentSensorIDs() {
        // Arrange
        SensorID sensorIdDouble = mock(SensorID.class);
        SensorID sensorIdDouble2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);


        ECA300K mySensor = new ECA300K(deviceId, sensorModelID, sensorIdDouble);
        ECA300K mySensor2 = new ECA300K(deviceId, sensorModelID, sensorIdDouble2);

        // Act
        boolean actual = mySensor.sameAs(mySensor2);

        // Assert
        assertFalse(actual);
    }

}
