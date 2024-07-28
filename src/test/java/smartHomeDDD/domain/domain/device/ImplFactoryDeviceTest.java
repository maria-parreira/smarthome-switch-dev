package smartHomeDDD.domain.domain.device;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.device.ImplFactoryDevice;
import smartHomeDDD.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test class includes the tests for the Device Factory Class.
 * It tests the conditions for the following scenarios:
 * - create a new instance of Device
 */

class ImplFactoryDeviceTest {

    /**
     * Tests the successful instantiation of a Device object.
     */
    @Test
    void createNewInstanceOfDevice() {

        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        DeviceName deviceName = mock(DeviceName.class);
        DeviceModel deviceModel = mock(DeviceModel.class);
        ActivationStatus status = mock(ActivationStatus.class);
        RoomID roomId = mock(RoomID.class);

        ImplFactoryDevice implFactoryDevice = new ImplFactoryDevice();

        try (MockedConstruction<Device> DeviceDouble = Mockito.mockConstruction(Device.class,
                (mock, context) -> {
                    DeviceId id = (DeviceId) context.arguments().get(0);
                    DeviceName name = (DeviceName) context.arguments().get(1);
                    DeviceModel model = (DeviceModel) context.arguments().get(2);
                    ActivationStatus activationStatus = (ActivationStatus) context.arguments().get(3);
                    RoomID room = (RoomID) context.arguments().get(4);
                    when(mock.identity()).thenReturn(id);
                    when(mock.getDeviceName()).thenReturn(name);
                    when(mock.getDeviceModel()).thenReturn(model);
                    when(mock.getActivationStatus()).thenReturn(activationStatus);
                    when(mock.getRoomId()).thenReturn(room);
                })) {

            // Act
            Device result = implFactoryDevice.createDevice(deviceId, deviceName, deviceModel, status, roomId);

            // Assert
            List<Device> mockedDevices = DeviceDouble.constructed();
            Device mockedDevice = DeviceDouble.constructed().get(0);

            assertEquals(1, mockedDevices.size());
            assertEquals(result, mockedDevice);
            assertEquals(deviceId, result.identity());
            assertEquals(deviceName, result.getDeviceName());
            assertEquals(deviceModel, result.getDeviceModel());
            assertEquals(status, result.getActivationStatus());
            assertEquals(roomId, result.getRoomId());
        }
    }
}
