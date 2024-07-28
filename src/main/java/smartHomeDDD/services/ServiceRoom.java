package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.repository.IRepositoryHouse;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.*;

import java.util.List;
import java.util.Optional;

/**
 * The ServiceRoom class provides services related to Room objects.
 * It uses a FactoryRoom to create Room objects and IRepositoryRoom to store and retrieve them.
 */
@Service
public class ServiceRoom {

    /**
     * Factory  for creating Room objects.
     */
    final FactoryRoom _factoryRoom;
    /**
     * Repository for storing and retrieving Room objects.
     */
    final IRepositoryRoom _repositoryRoom;
    /**
     * Repository for storing and retrieving House objects.
     */
    final IRepositoryHouse _repositoryHouse;
    /**
     * Service for generating random IDs.
     */
    final GenerateRandomId _generateRandomId;


    /**
     * Constructor for the ServiceRoom class.
     * @param factoryRoom    Factory for creating Room objects. Must not be null.
     * @param repositoryRoom Repository for storing and retrieving Room objects. Must not be null.
     * @param repositoryHouse Repository for storing and retrieving House objects. Must not be null.
     * @param generateRandomId Service for generating random IDs. Must not be null.
     * @throws IllegalArgumentException If the factory or repository is null.
     */
    public ServiceRoom(FactoryRoom factoryRoom, IRepositoryRoom repositoryRoom, IRepositoryHouse repositoryHouse, GenerateRandomId generateRandomId) {
        if (factoryRoom == null) {
            throw new IllegalArgumentException("Factory cannot be null");
        }
        if (repositoryRoom == null) {
            throw new IllegalArgumentException("RoomRepository cannot be null");
        }
        if (repositoryHouse == null) {
            throw new IllegalArgumentException("HouseRepository cannot be null");
        }
        if (generateRandomId == null) {
            throw new IllegalArgumentException("GenerateRandomId cannot be null");
        }
        this._factoryRoom = factoryRoom;
        this._repositoryRoom = repositoryRoom;
        this._repositoryHouse = repositoryHouse;
        this._generateRandomId = generateRandomId;
    }

    /**
     * Adds a room to a house.
     * @param houseId     The id of the house to add the room to.
     * @param floorNumber The floor number of the room.
     * @param dimensions  The dimensions of the room.
     * @param isInside    Whether the room is inside the house.
     * @param roomName    The name of the room.
     * @return The room that was added.
     * @throws IllegalArgumentException If the house is not found.
     */
    public Room addRoomToHouse(HouseId houseId,FloorNumber floorNumber, Dimensions dimensions, boolean isInside, RoomName roomName) {
        if (!_repositoryHouse.containsOfIdentity(houseId)) {
            throw new EntityNotFoundException("House not found");
        }
        RoomID roomId = new RoomID(_generateRandomId.generateID());
        Room room = _factoryRoom.createRoom(houseId, roomId, floorNumber, dimensions, isInside, roomName);
        return _repositoryRoom.save(room);
    }

    /**
     * Lists all rooms in a house.
     * @param houseId The id of the house to list rooms from.
     * @return A list of rooms in the house.
     * @throws IllegalArgumentException If the house is not found.
     */
    public List<Room> getRoomsByHouseID(HouseId houseId) {
        if (!_repositoryHouse.containsOfIdentity(houseId)) {
            throw new EntityNotFoundException("House not found");
        }
        return _repositoryRoom.getRoomsByHouseID(houseId);
    }

    /**
     * Retrieves a room by its ID.
     * @param roomID The ID of the room to retrieve.
     * @return The room with the provided ID if exists, else throws an EntityNotFoundException.
     */
    public Room getRoomById(RoomID roomID) {
        Optional<Room> room = _repositoryRoom.ofIdentity(roomID);
        if (room.isPresent()) {
            return room.get();
        }
        throw new EntityNotFoundException("Room not found");
    }

    /**
     * This method is used to add a new room to the repository.
     * @param roomID The unique identifier for the room to be added.
     * @param houseId The unique identifier for the house to be added.
     */
    public void isPowerGridMeterInHouse(HouseId houseId, RoomID roomID) {
        Optional<Room> room = _repositoryRoom.ofIdentity(roomID);
        if (room.isEmpty() || !room.get().getHouseId().equals(houseId)) {
            throw new EntityNotFoundException("PowerGridMeter not found in house");
        }
    }

}
