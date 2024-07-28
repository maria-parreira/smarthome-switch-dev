package smartHomeDDD.persistence.mem;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.RoomID;

import java.util.*;

/**
 * In-memory repository for Room entities.
 */

public class RepositoryRoomMem implements IRepositoryRoom {
    /**
     * The map to store Room objects mapped by their RoomID.
     */
    private final Map<RoomID, Room> DATA = new HashMap<>();
    /**
     * Saves the provided Room entity in the repository.
     *
     * @param entity the Room entity to save.
     * @return the saved Room entity.
     */
    @Override
    public Room save(Room entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if( containsOfIdentity(entity.identity()) ){
            throw new DataIntegrityViolationException("Room already exists");
        }
        DATA.put( entity.identity(), entity );
        return entity;
    }

    /**
     * Retrieves all Room entities from the repository.
     *
     * @return an iterable containing all Room entities.
     */
    @Override
    public Iterable<Room> findAll() {
        return DATA.values();
    }
    /**
     * Retrieves the Room entity with the specified RoomID from the repository, if present.
     *
     * @param id the RoomID of the Room entity to retrieve.
     * @return an optional containing the Room entity, or an empty optional if not found.
     */
    @Override
    public Optional<Room> ofIdentity(RoomID id) {
        if( !containsOfIdentity(id) )
            return Optional.empty();
        else
            return Optional.of( DATA.get(id) );
    }
    /**
     * Checks if the repository contains a Room entity with the specified RoomID.
     *
     * @param id the RoomID to check.
     * @return true if the repository contains the Room entity, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(RoomID id) {
        for (RoomID roomId : DATA.keySet()) {
            if (roomId.equals(id)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Gets all rooms in a house.
     *
     * @param houseId The ID of the house to get rooms from.
     * @return A list of rooms in the house.
     */
    @Override
    public List<Room> getRoomsByHouseID(HouseId houseId) {
        List<Room> roomsInHouse = new ArrayList<>();
        Iterable <Room> allRooms = findAll();
        for (Room room : allRooms) {
            if (room.getHouseId().equals(houseId)) {
                roomsInHouse.add(room);
            }
        }
        return roomsInHouse;
    }
    /**
     * Gets all rooms that are outside.
     *
     * @param rooms The rooms to filter.
     * @return A list of rooms that are outside.
     */

    @Override
    public List<Room> getOutsideRooms(Iterable<Room> rooms) {
        List<Room> outsideRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isInside()) {
                outsideRooms.add(room);
            }
        }
        return outsideRooms;
    }
}
