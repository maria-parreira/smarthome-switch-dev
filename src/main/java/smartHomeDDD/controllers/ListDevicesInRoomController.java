package smartHomeDDD.controllers;

import smartHomeDDD.domain.device.Device;
import smartHomeDDD.domain.valueobject.RoomID;
import smartHomeDDD.dto.DeviceDTO;
import smartHomeDDD.dto.DeviceMapper;
import smartHomeDDD.dto.RoomMapper;
import smartHomeDDD.services.ServiceDevice;

import java.util.List;

/**
 * Controller for listing devices in a room.
 */

public class ListDevicesInRoomController {

    private final ServiceDevice serviceDevice;

    /**
     * Constructor for the US06ListDevicesInRoomController class.
     *
     * @param serviceDevice The service for devices.
     * @throws IllegalArgumentException If the device service is null.
     */

    public ListDevicesInRoomController(ServiceDevice serviceDevice) {
        if (serviceDevice == null) {
            throw new IllegalArgumentException("Device Service cannot be null");
        } else {
            this.serviceDevice = serviceDevice;
        }
    }

    /**
     * Lists all devices in a room.
     *
     * @param strRoomID The id of the room to list devices from, in string format.
     * @return A list of DTOs of the devices in the room.
     * @throws IllegalArgumentException If the room is not found.
     */

    public List<DeviceDTO> listDevicesInARoom(String strRoomID) {
        RoomID roomID = RoomMapper.DTOToRoomId(strRoomID);

        List<Device> devicesInRoom = serviceDevice.listDevicesInRoom(roomID);

        return DeviceMapper.deviceListToDTOList(devicesInRoom);
    }
}
