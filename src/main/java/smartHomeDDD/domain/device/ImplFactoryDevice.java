package smartHomeDDD.domain.device;

import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.*;

/**
 * Factory class that implements Factory Device interface.
 * It creates device objects in the smart home domain.
 * Additionally, it provides methods to convert primitive data types to value objects.
 */
@Component
public class ImplFactoryDevice implements FactoryDevice {

    /**
     * Creates a new device object with the provided parameters.
     * @param deviceId    The unique identifier for the device.
     * @param deviceName  The name of the device.
     * @param deviceModel The model of the device.
     * @param status      The activation status of the device.
     * @param roomId      The identifier of the room where the device is located.
     * @return A new device object initialized with the provided parameters.
     */
    @Override
    public Device createDevice(DeviceId deviceId, DeviceName deviceName, DeviceModel deviceModel, ActivationStatus status, RoomID roomId){
        return new Device(deviceId, deviceName, deviceModel, status, roomId);
    }
}
