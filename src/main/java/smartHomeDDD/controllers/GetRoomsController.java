package smartHomeDDD.controllers;

import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.dto.HouseMapper;
import smartHomeDDD.dto.RoomDTO;
import smartHomeDDD.dto.RoomMapper;
import smartHomeDDD.services.ServiceRoom;

import java.util.List;

/**
 * Controller class that gives the list of rooms.
 */
public class GetRoomsController {

    private final ServiceRoom _serviceRoom;
    /**
     * Constructor for the US06ListDevicesInRoomController class.
     *
     * @param serviceRoom The service for devices.
     * @throws IllegalArgumentException If the device service is null.
     */


    public GetRoomsController(ServiceRoom serviceRoom) {
        if (serviceRoom == null) {
            throw new IllegalArgumentException("Service cannot be null");

        }
        this._serviceRoom = serviceRoom;
    }


    /**
     * List all rooms in a house.
     *
     * @param strhouseId The id of the house to list rooms from, in string format.
     * @return A list of DTOs of the rooms in the house.
     * @throws IllegalArgumentException If the house is not found.
     */
    public List<RoomDTO> getRooms(String strhouseId) {
        HouseId houseID = HouseMapper.DTOToHouseId(strhouseId);
        List <Room> roomsInHouse = _serviceRoom.getRoomsByHouseID(houseID);

        return RoomMapper.roomsListToDTO(roomsInHouse);
    }
}
