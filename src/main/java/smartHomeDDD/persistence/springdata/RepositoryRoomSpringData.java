package smartHomeDDD.persistence.springdata;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.RoomID;
import smartHomeDDD.persistence.jpa.datamodel.RoomDataModel;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation for managing Room entities using Spring Data.
 * This class provides methods to interact with the persistence layer for Room entities.
 */
@Repository
public class RepositoryRoomSpringData implements IRepositoryRoom {
    /**
     * The Spring Data repository for RoomDataModel entities.
     */
    final IRepositoryRoomSpringData _repositoryRoomSpringData;

    /**
     * The factory for creating Room domain objects.
     */
    final FactoryRoom _factoryRoom;

    /**
     * Constructor to initialize RepositoryRoomSpringData.
     *
     * @param factoryRoom              The factory for creating Room objects
     * @param repositoryRoomSpringData The Spring Data repository for Room entities
     */

    public RepositoryRoomSpringData(FactoryRoom factoryRoom, IRepositoryRoomSpringData repositoryRoomSpringData) {
        this._factoryRoom = factoryRoom;
        this._repositoryRoomSpringData = repositoryRoomSpringData;
    }

    /**
     * Persists a Room entity.
     *
     * @param room The Room entity to save
     * @return The persisted Room entity
     * @throws IllegalArgumentException if the provided Room object is null or duplicated
     */
    @Override
    public Room save(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (containsOfIdentity(room.identity())) {
            throw new DataIntegrityViolationException("Room already exists");
        }
        RoomDataModel roomDataModel = new RoomDataModel(room);
        this._repositoryRoomSpringData.save(roomDataModel);

        return room;
    }

    /**
     * Retrieves all Room entities.
     *
     * @return An Iterable of all Room entities
     */

    @Override
    public Iterable<Room> findAll() {
        List<RoomDataModel> roomDataModels = this._repositoryRoomSpringData.findAll();
        return RoomDataModel.toDomain(_factoryRoom, roomDataModels);
    }

    /**
     * Retrieves a Room entity by its identity.
     *
     * @param id The identity of the Room entity to retrieve
     * @return An Optional containing the Room entity if found, or an empty Optional otherwise
     */
    @Override
    public Optional<Room> ofIdentity(RoomID id) {
        Optional<RoomDataModel> roomDataModelSaved = this._repositoryRoomSpringData.findById(id.toString());

        if (roomDataModelSaved.isPresent()) {
            Room roomDomain = RoomDataModel.toDomain(_factoryRoom, roomDataModelSaved.get());
            return Optional.of(roomDomain);
        } else
            return Optional.empty();
    }

    /**
     * Checks if a Room entity with the specified identity exists.
     *
     * @param id The identity of the Room entity to check
     * @return true if the Room entity exists, false otherwise
     */

    @Override
    public boolean containsOfIdentity(RoomID id) {
        return _repositoryRoomSpringData.existsById(id.toString());
    }

    /**
     * Retrieves all Room entities associated with a House entity.
     *
     * @param houseId The identity of the House entity
     * @return A list of Room entities associated with the House entity
     */

    @Override
    public List<Room> getRoomsByHouseID(HouseId houseId) {
        List<RoomDataModel> roomDataModels = this._repositoryRoomSpringData.findByHouseId(houseId.toString());
        return RoomDataModel.toDomain(_factoryRoom, roomDataModels);
    }

    /**
     * Retrieves all Room entities that are outside the house.
     *
     * @param rooms The list of Room entities to filter
     * @return A list of Room entities that are outside the house
     */

    @Override
    public List<Room> getOutsideRooms(Iterable<Room> rooms) {
        List<RoomDataModel> outsideRooms = this._repositoryRoomSpringData.findByisInsideFalse();
        return RoomDataModel.toDomain(_factoryRoom, outsideRooms);
    }
}
