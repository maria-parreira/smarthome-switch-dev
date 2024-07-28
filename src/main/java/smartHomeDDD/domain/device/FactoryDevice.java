package smartHomeDDD.domain.device;

import smartHomeDDD.domain.valueobject.*;

/**
 * Factory interface for creating device objects in the smart home domain.
 */
public interface FactoryDevice {

    /**
     * Creates a new device object with the provided parameters.
     * @param deviceId    The unique identifier for the device.
     * @param deviceName  The name of the device.
     * @param deviceModel The model of the device.
     * @param status      The activation status of the device.
     * @param roomId      The identifier of the room where the device is located.
     * @return A new device object initialized with the provided parameters.
     */
    Device createDevice(DeviceId deviceId, DeviceName deviceName, DeviceModel deviceModel, ActivationStatus status, RoomID roomId);
}
