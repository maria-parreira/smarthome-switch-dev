package smartHomeDDD.controllers;

import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;
import smartHomeDDD.dto.HouseMapper;
import smartHomeDDD.dto.RoomDTO;
import smartHomeDDD.dto.RoomMapper;
import smartHomeDDD.services.ServiceRoom;

/**
 * Controller class responsible for adding a new room to a house.
 */
public class AddNewRoomToHouseController {

    ServiceRoom _serviceRoom;

    /**
     * Constructs the US02AddNewRoomToDeviceController with the specified factory for creating rooms and repository which manages and stores rooms.
     *
     */

    public AddNewRoomToHouseController(ServiceRoom serviceRoom) {

        if (serviceRoom == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }

        this._serviceRoom = serviceRoom;

    }


    /**
     * Adds a new room to a house in the system.
     * This method creates a new room based on the information provided in the DTO
     * and adds it to the specified house. If a room with the same ID already exists
     * in the specified house, it returns null.
     *
     * @param DTO The DTO containing information about the new room to be added.
     * @return The DTO of the created room if successfully added, or null if the operation failed.
     */
    public RoomDTO addRoomToHouse(RoomDTO DTO) {
        // Convert DTO fields to domain objects
        HouseId houseId = HouseMapper.DTOToHouseId(DTO.getHouseId());
        FloorNumber floorNumber = RoomMapper.DTOToFloorNumber(DTO.getFloorNumber());
        Length length = RoomMapper.DTOToLength(DTO.getLength());
        Width width = RoomMapper.DTOToWidth(DTO.getWidth());
        Height height = RoomMapper.DTOToHeight(DTO.getHeight());
        boolean isInside = DTO.isInside();
        RoomName roomName = RoomMapper.DTOToRoomName(DTO.getRoomName());

        // Create dimensions object
        Dimensions dimensions = new Dimensions(length, width, height);

        // Create room object
        Room room = _serviceRoom.addRoomToHouse(houseId, floorNumber, dimensions, isInside, roomName);

        // Convert the saved room back to DTO and return
        return RoomMapper.roomToDTO(room);
    }
}
