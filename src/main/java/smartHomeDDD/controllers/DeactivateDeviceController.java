package smartHomeDDD.controllers;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.dto.DeviceMapper;
import smartHomeDDD.services.ServiceDevice;

/**
 * The DeactivateDeviceController class is responsible for handling the deactivation of devices.
 * It uses a ServiceDevice instance to perform the deactivation operation.
 */
public class DeactivateDeviceController {

    /**
     * The ServiceDevice instance used for device operations.
     */

    private final ServiceDevice _serviceDevice;


    /**
     * Constructs a new DeactivateDeviceController with the provided ServiceDevice instance.
     *
     * @param serviceDevice The ServiceDevice instance to be used for device operations.
     * @throws IllegalArgumentException If the provided ServiceDevice instance is null.
     */
    public DeactivateDeviceController(ServiceDevice serviceDevice) {
        if (serviceDevice == null) {
            throw new IllegalArgumentException("ServiceDevice cannot be null.");
        } else {

            this._serviceDevice = serviceDevice;
        }
    }

    /**
     * Deactivates the device with the provided device ID string.
     * The device ID string is converted to a DeviceId value object, and the ServiceDevice instance is used to deactivate the device.
     * If the device is successfully deactivated, a DeviceDTO of the deactivated device is returned.
     * If the device cannot be found or deactivated, null is returned.
     *
     * @param deviceIdString The string representation of the device ID of the device to be deactivated.
     * @return A DeviceDTO of the deactivated device, or null if the device cannot be found or deactivated.
     */
    public DeviceDTO deactivateDevice(String deviceIdString) {

        DeviceId deviceId = DeviceMapper.DTOToDeviceId(deviceIdString);
        Device myDevice = _serviceDevice.deactivateDevice(deviceId);

        return DeviceMapper.deviceToDTO(myDevice);

    }
}


