package smartHomeDDD.domain.domain.sensorReading;

import smartHomeDDD.domain.sensorReading.ImplFactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.Reading;
import smartHomeDDD.domain.valueobject.SensorReadingID;
import smartHomeDDD.domain.valueobject.SensorID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test class includes the tests for the SensorReading Factory Class.
 * It tests the conditions for the following scenarios:
 * - instantiation of a new instance of SensorReading
 */
class ImplFactorySensorReadingTest
{
    /**
     * Tests the successful instantiation of a SensorReading object.
     */
    @Test
    void validCAP200Reading_shouldReturnValidObject()
    {
        ImplFactorySensorReading factorySensorReading = new ImplFactorySensorReading();
        SensorReadingID sensorReadingID = mock(SensorReadingID.class);
        Reading value = mock(Reading.class);
        DeviceId deviceID = mock(DeviceId.class);
        SensorID sensorID = mock(SensorID.class);
        Timestamp time = mock(Timestamp.class);

        ImplFactorySensorReading implFactorySensorReading = new ImplFactorySensorReading();

        try (MockedConstruction<SensorReading> SensorReadingDouble = Mockito.mockConstruction(SensorReading.class,
                (mock, context) -> {
                    SensorReadingID id = (SensorReadingID) context.arguments().get(0);
                    Reading val = (Reading) context.arguments().get(1);
                    DeviceId device = (DeviceId) context.arguments().get(2);
                    SensorID sensor = (SensorID) context.arguments().get(3);
                    Timestamp timestamp = (Timestamp) context.arguments().get(4);
                    when(mock.identity()).thenReturn(id);
                    when(mock.getReading()).thenReturn(val);
                    when(mock.getDeviceID()).thenReturn(device);
                    when(mock.getSensorID()).thenReturn(sensor);
                    when(mock.getTimeStamp()).thenReturn(timestamp);
                })) {

            // Act
            implFactorySensorReading.createSensorReading(sensorReadingID, value, deviceID, sensorID, time);

            // Assert
            List<SensorReading> mockedSensorReadings = SensorReadingDouble.constructed();
            SensorReading mockedSensorReading = SensorReadingDouble.constructed().get(0);

            assertEquals(1, mockedSensorReadings.size());
            assertEquals(sensorReadingID, mockedSensorReading.identity());
            assertEquals(value, mockedSensorReading.getReading());
            assertEquals(deviceID, mockedSensorReading.getDeviceID());
            assertEquals(sensorID, mockedSensorReading.getSensorID());
            assertEquals(time, mockedSensorReading.getTimeStamp());
        }
    }


}

