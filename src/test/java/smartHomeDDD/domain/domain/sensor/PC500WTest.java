package smartHomeDDD.domain.domain.sensor;

import smartHomeDDD.domain.sensor.PC500W;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.PC500WValue;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The PC500WTest class conducts unit tests on the PC500W class. The tests validate the following:
 * - Successful instantiation of the PC500W object with valid parameters.
 * - The 'identity' method correctly retrieves the sensor's ID.
 * - The 'getValue' method accurately retrieves the sensor's value.
 * - The 'getDeviceID' method correctly retrieves the sensor's device ID.
 * - The 'getSensorModelID' method correctly retrieves the sensor's model ID.
 * - The 'setData' method correctly sets the sensor's data.
 * - The 'equals' method correctly identifies identical sensors.
 * - The 'equals' method correctly identifies a sensor as identical to itself.
 * - The 'equals' method correctly identifies a sensor as different from an object of a different class.
 * - The 'equals' method correctly identifies sensors with different IDs as different.
 * - The 'sameAs' method correctly identifies sensors with different device IDs as different.
 * - The 'sameAs' method correctly identifies sensors with different model IDs as different.
 * - The 'sameAs' method correctly identifies identical sensors.
 * - The 'sameAs' method correctly identifies different sensors.
 * - The 'sameAs' method correctly identifies a sensor as different from an object of a different class.
 * - The 'hashCode' method correctly computes the hash code of a sensor.
 */
class PC500WTest {

    /**
     * Test case for creating a valid sensor.
     * The test verifies that the PC500W constructor returns a non-null object when provided with valid arguments.
     */
    @Test
    void validSensor_shouldReturnNonNullObject() {

        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);

        // Act
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorID);

        // Assert
        assertNotNull(pc500w);
        assertEquals(sensorID, pc500w.identity());
        assertEquals(sensorModelID, pc500w.getSensorModelID());
        assertEquals(deviceId, pc500w.getDeviceID());
    }

    /**
     * Test case for checking the identity of a sensor.
     * The test verifies that the identity method of the PC500W class returns the expected SensorID.
     */
    @Test
    void validIdentity_ShouldReturnEqualsID() {
        // Arrange
        SensorID sensorID2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        PC500W pc500W = new PC500W(deviceId, sensorModelID, sensorID2);
        // Act
        SensorID actualId = pc500W.identity();
        // Assert
        assertEquals(sensorID2, actualId);
    }

    /**
     * Test case for the getValue method in the PC500W class when the time exists in the readings.
     * The test verifies that the getValue method returns the correct value when the time exists in the readings.
     */
    @Test
    void getValue_ExistingTime_ShouldReturnCorrectValue() {
        // Arrange
        SensorID sensorId = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorId);
        LocalTime time = LocalTime.of(3, 0);

        // Act
        try (MockedConstruction<PC500WValue> mockedValue = mockConstruction(PC500WValue.class, (mock, context) -> {
            Double valueReading = (Double) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {

            Double powerConsumption = Double.valueOf(pc500w.getValue(time).toString());

            // Assert
            List<PC500WValue> values = mockedValue.constructed();
            PC500WValue myValue = mockedValue.constructed().get(0);
            assertEquals(1, values.size());

            assertEquals(100.0, powerConsumption);
            assertEquals(myValue.toString(), powerConsumption.toString());
        }
    }

    /**
     * Test case for the getValue method in the PC500W class when the time does not exist in the readings.
     * The test verifies that the getValue method returns the default value when the time does not exist in the readings.
     */
    @Test
    void getValue_NonExistingTime_ShouldReturnDefaultValue() {

        // Arrange
        SensorID sensorId = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);

        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorId);
        LocalTime time = LocalTime.of(1, 0);
        // Act
        try (MockedConstruction<PC500WValue> mockedValue = mockConstruction(PC500WValue.class, (mock, context) -> {
            Double valueReading = (Double) context.arguments().get(0);
            when(mock.toString()).thenReturn(valueReading.toString());
        })) {

            Double powerConsumption = Double.valueOf(pc500w.getValue(time).toString());

            // Assert
            List<PC500WValue> values = mockedValue.constructed();
            PC500WValue myValue = mockedValue.constructed().get(0);
            assertEquals(1, values.size());

            assertEquals(0.0, powerConsumption);
            assertEquals(myValue.toString(), powerConsumption.toString());
        }
    }

    /**
     * Test case for the getDeviceID method in the PC500W class.
     * This test verifies that the method returns the correct DeviceId that was set during the construction of the PC500W object.
     */
    @Test
    public void getDeviceID_ShouldReturnCorrectDeviceID() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorID);

        // Act
        DeviceId returnedDeviceId = pc500w.getDeviceID();

        // Assert
        assertEquals(deviceId, returnedDeviceId);
    }

    /**
     * Test case for the getSensorModelID method in the PC500W class.
     * This test verifies that the method returns the correct SensorModelID that was set during the construction of the PC500W object.
     */
    @Test
    public void getSensorModelID_ShouldReturnCorrectSensorModelID() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorID);

        // Act
        SensorModelID returnedSensorModelID = pc500w.getSensorModelID();

        // Assert
        assertEquals(sensorModelID, returnedSensorModelID);
    }

    /**
     * Test case for the setData method in the PC500W class.
     * This test verifies that the setData method returns a map with the correct readings at the correct times.
     */
    @Test
    void setData_ShouldReturnCorrectReadings() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorID);

        // Act
        Map<LocalTime, Double> readings = pc500w.setData();

        // Assert
        assertEquals(100.0, readings.get(LocalTime.of(3, 0)));
        assertEquals(200.0, readings.get(LocalTime.of(15, 15)));
        assertEquals(250.5, readings.get(LocalTime.of(12, 30)));
        assertEquals(300.0, readings.get(LocalTime.of(18, 2)));
    }

    /**
     * This test case verifies that the sameAs method of the PC500W class returns true when comparing the same object.
     */
    @Test
    void samePC500WObject_shouldReturnTrue() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = pc500w.sameAs(pc500w);

        // Assert
        assertTrue(result);
    }

    /**
     * This test case verifies that the equals method of the PC500W class returns false when comparing a PC500W object with a non-PC500W object.
     */
    @Test
    void twoDifferentPC500WObject_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorID);
        Object obj = new Object();

        // Act
        boolean result = pc500w.sameAs(obj);

        // Assert
        assertFalse(result);
    }
    /**
     * This test case verifies that the sameAs method of the PC500W class returns false when comparing two objects with different SensorIDs.
     */
    @Test
    void differentPC500WIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceID = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID1 = mock(SensorID.class);
        SensorID sensorID2 = mock(SensorID.class);
        PC500W pc500w1 = new PC500W(deviceID, sensorModelID, sensorID1);
        PC500W pc500w2 = new PC500W(deviceID, sensorModelID, sensorID2);

        // Act
        boolean result = pc500w1.sameAs(pc500w2);

        // Assert
        assertFalse(result);
    }
    /**
     * This test case verifies that the sameAs method of the PC500W class returns false when comparing two objects with different DeviceIds.
     */
    @Test
    void differentPC500WDeviceIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceID1 = mock(DeviceId.class);
        DeviceId deviceID2 = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w1 = new PC500W(deviceID1, sensorModelID, sensorID);
        PC500W pc500w2 = new PC500W(deviceID2, sensorModelID, sensorID);

        // Act
        boolean result = pc500w1.sameAs(pc500w2);

        // Assert
        assertFalse(result);
    }
    /**
     * This test case verifies that the sameAs method of the PC500W class returns false when comparing two objects with different SensorModelIDs.
     */
    @Test
    void differentSensorModelIds_shouldReturnFalse() {
        // Arrange
        DeviceId deviceID = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorModelID sensorModelID2 = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w1 = new PC500W(deviceID, sensorModelID1, sensorID);
        PC500W pc500w2 = new PC500W(deviceID, sensorModelID2, sensorID);

        // Act
        boolean result = pc500w1.sameAs(pc500w2);

        // Assert
        assertFalse(result);
    }

    /**
     * This test case verifies that the equals method of the PC500W class returns true when comparing the same object.
     */
    @Test
    void whenComparingItself_shouldReturnTrue() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = pc500w.equals(pc500w);

        // Assert
        assertTrue(result);
    }

    /**
     * This test case verifies that the equals method of the PC500W class returns true when comparing two objects with the same DeviceId, SensorModelID, and SensorID.
     */
    @Test
    void twoEqualPC500W_shouldReturnTrue() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w1 = new PC500W(deviceId, sensorModelID, sensorID);
        PC500W pc500w2 = new PC500W(deviceId, sensorModelID, sensorID);

        // Act
        boolean result = pc500w1.equals(pc500w2);

        // Assert
        assertTrue(result);
    }

    /**
     * This test case verifies that the equals method of the PC500W class returns false when comparing two objects with different DeviceIds, SensorModelIDs, and SensorIDs.
     */
    @Test
    void twoDifferentPC500W_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        SensorModelID sensorModelID1 = mock(SensorModelID.class);
        SensorID sensorID1 = mock(SensorID.class);
        PC500W pc500w1 = new PC500W(deviceId1, sensorModelID1, sensorID1);

        DeviceId deviceId2 = mock(DeviceId.class);
        SensorModelID sensorModelID2 = mock(SensorModelID.class);
        SensorID sensorID2 = mock(SensorID.class);
        PC500W pc500w2 = new PC500W(deviceId2, sensorModelID2, sensorID2);

        // Act
        boolean result = pc500w1.equals(pc500w2);

        // Assert
        assertFalse(result);
    }

    /**
     * This test case verifies that the equals method of the PC500W class returns false when comparing a PC500W object with a non-PC500W object.
     */
    @Test
    void whenComparingPC500WWithDifferentObject_shouldReturnFalse() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        SensorID sensorID = mock(SensorID.class);
        PC500W pc500w = new PC500W(deviceId, sensorModelID, sensorID);
        Object obj = new Object();

        // Act
        boolean result = pc500w.equals(obj);

        // Assert
        assertFalse(result);
    }

    /**
     * Test to ensure that the hashCode method of the PC500W class returns the correct hash code.
     * This test uses Mockito to create a mock SensorID, DeviceId, and SensorModelID.
     * It then asserts that the actual hash code returned by the hashCode method is equal to the hash code of the mock SensorID.
     */
    @Test
    void hashCode_shouldReturnCorrectHashCode() {
        // Arrange
        SensorID sensorIdMock = mock(SensorID.class);
        DeviceId deviceIdMock = mock(DeviceId.class);
        SensorModelID sensorModelIdMock = mock(SensorModelID.class);
        PC500W pc500w = new PC500W(deviceIdMock, sensorModelIdMock, sensorIdMock);

        // Act
        int actualHashCode = pc500w.hashCode();

        // Assert
        assertEquals(Objects.hash(sensorIdMock), actualHashCode);
    }

    /**
     * Test to ensure that the sameAs method of the PC500W class returns false when the SensorIDs of two PC500W objects are different.
     * This test uses Mockito to create two PC500W objects with different SensorIDs.
     * It then asserts that the sameAs method returns false when comparing these two PC500W objects.
     */
    @Test
    void sameAs_shouldReturnFalseWhenSensorIDsAreDifferent() {
        // Arrange
        SensorID sensorId1 = mock(SensorID.class);
        SensorID sensorId2 = mock(SensorID.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelID sensorModelID = mock(SensorModelID.class);
        PC500W pc500w1 = new PC500W(deviceId, sensorModelID, sensorId1);
        PC500W pc500w2 = new PC500W(deviceId, sensorModelID, sensorId2);

        // Act
        boolean result = pc500w1.sameAs(pc500w2);

        // Assert
        assertFalse(result);
    }
}
