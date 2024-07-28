package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.RoomID;

import java.util.List;

/**
 * The IRepositoryRoom interface provides a contract for a repository that manages Room objects.
 * It extends the generic Repository interface, specifying RoomID as the ID type and Room as the entity type.
 */
public interface IRepositoryRoom extends Repository<RoomID, Room> {

    /**
     * Retrieves a list of Room objects that are associated with a specific House.
     *
     * @param houseId The ID of the House for which to retrieve the associated Rooms.
     * @return A list of Room objects associated with the specified House.
     */
    List<Room> getRoomsByHouseID(HouseId houseId);
    /**
     * Retrieves a list of Room objects that are outside the house.
     *
     * @param rooms The list of Room objects to filter.
     * @return A list of Room objects that are outside the house.
     */
    List<Room> getOutsideRooms(Iterable<Room> rooms);
}
