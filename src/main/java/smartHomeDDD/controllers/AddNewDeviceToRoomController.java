package smartHomeDDD.controllers;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.dto.DeviceMapper;
import smartHomeDDD.dto.RoomMapper;
import smartHomeDDD.services.ServiceDevice;

/**
 * Controller class for adding a new device to a room.
 */
public class AddNewDeviceToRoomController {

    private final ServiceDevice _serviceDevice;

    /**
     * Constructs a new instance of the controller.
     *
     * @param serviceDevice     The service for creating and storing devices.
     * @throws IllegalArgumentException if serviceDevice is null.
     */
    public AddNewDeviceToRoomController(ServiceDevice serviceDevice) {
        if (serviceDevice != null) {
            this._serviceDevice = serviceDevice;
        }
        else {
            throw new IllegalArgumentException("ServiceDevice cannot be null.");
        }
    }

    /**
     * Adds a new device to a room.
     * The device data is extracted from the DTO and mapped to Value Objects.
     * Through ServiceDevice, the device is created with the Value Objects and saved to the repository.
     * The saved device is then mapped to a DTO and returned.
     *
     * @param deviceDTO The DTO containing the device data.
     * @return The DTO of the newly created device.
     */
    public DeviceDTO addNewDeviceToRoom(DeviceDTO deviceDTO) {

        // Extracting the data from the DTO
        String roomIdString = deviceDTO.getRoomId();
        String deviceNameString = deviceDTO.getDeviceName();
        String deviceModelString = deviceDTO.getDeviceModel();
        boolean activationStatusBoolean = deviceDTO.getActivationStatus();

        // Mapping the data to the Value Objects
        RoomID roomIDVO = RoomMapper.DTOToRoomId(roomIdString);
        DeviceName deviceNameVO = DeviceMapper.DTOToDeviceName(deviceNameString);
        DeviceModel deviceModelVO = DeviceMapper.DTOToDeviceModel(deviceModelString);
        ActivationStatus activationStatusVO = DeviceMapper.DTOToActivationStatus(activationStatusBoolean);

        // Creating and saving the device through the service
        Device device = _serviceDevice.addNewDevice(deviceNameVO, deviceModelVO, activationStatusVO, roomIDVO);

        // Mapping the saved device to a DTO and returning it
        return DeviceMapper.deviceToDTO(device);
    }
}
