package smartHomeDDD.persistence.jpa.repository;

import smartHomeDDD.domain.repository.IRepositoryRoom;
import smartHomeDDD.domain.room.FactoryRoom;
import smartHomeDDD.domain.room.Room;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.RoomID;
import smartHomeDDD.persistence.jpa.datamodel.RoomDataModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation for managing Room entities using JPA.
 * This class provides methods to interact with the persistence layer for Room entities.
 */
public class RepositoryRoomJPAImpl implements IRepositoryRoom {

    /**
     * The factory for creating Room domain objects.
     */
    final FactoryRoom _factoryRoom;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * Constructor to initialize RepositoryRoomJPAImpl.
     *
     * @param factoryRoom The factory for creating Room objects
     */
    public RepositoryRoomJPAImpl(FactoryRoom factoryRoom, EntityManager manager) {
        _factoryRoom = factoryRoom;
        _manager = manager;
    }

    /**
     * Retrieves the entity manager
     * Retrieves the EntityManager used for database operations.
     *
     * @return The entity manager instance.
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

    /**
     * Saves a Room entity to the database.
     *
     * @param room The Room object to be saved.
     * @return The saved Room object.
     * @throws IllegalArgumentException if the Room object is null or already exists in the database.
     */
    @Override
    public Room save(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (containsOfIdentity(room.identity())) {
            throw new IllegalArgumentException("Room already exists");
        }
        RoomDataModel roomDataModel = new RoomDataModel(room);
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(roomDataModel);
        em.getTransaction().commit();
        em.close();

        return room;
    }

    /**
     * Retrieves all Room entities stored in the database.
     *
     * @return An iterable containing all Room entities.
     */
    @Override
    public Iterable<Room> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM RoomDataModel e");
        List listDataModel = query.getResultList();

        return (Iterable<Room>) RoomDataModel.toDomain(_factoryRoom, listDataModel);
    }

    /**
     * Retrieves the Room entity with the specified RoomID from the database, if present.
     *
     * @param id the RoomID of the Room entity to retrieve.
     * @return an optional containing the Room, or an empty optional if not found.
     */
    @Override
    public Optional<Room> ofIdentity(RoomID id) {
        RoomDataModel roomDataModel = getEntityManager().find(RoomDataModel.class, id.toString());
        if (roomDataModel != null) {
            Room room = RoomDataModel.toDomain(_factoryRoom, roomDataModel);
            return Optional.of(room);
        }

        return Optional.empty();
    }

    /**
     * Checks if the database contains a Room entity with the specified RoomID.
     *
     * @param id the RoomID to check.
     * @return true if the database contains the Room entity, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(RoomID id) {
        Optional<Room> room = ofIdentity(id);

        return room.isPresent();
    }

    /**
     * Retrieves a list of Room entities from the database that belong to a specific house.
     *
     * @param houseId The HouseId of the house.
     * @return A List of Room entities that belong to the specified house.
     */
    @Override
    public List<Room> getRoomsByHouseID(HouseId houseId) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM RoomDataModel e WHERE e.houseId = :houseId");
        query.setParameter("houseId", houseId.toString());
        List<RoomDataModel> listDataModel = query.getResultList();

        return RoomDataModel.toDomain(_factoryRoom, listDataModel);
    }

    /**
     * Retrieves a list of Room entities from the database that are outside the house.
     *
     * @param rooms The list of Room entities to filter.
     * @return A List of Room entities that are outside the house.
     */
    @Override
    public List<Room> getOutsideRooms(Iterable<Room> rooms) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM RoomDataModel e WHERE e.isInside = false");
        List<RoomDataModel> listDataModel = query.getResultList();
        return RoomDataModel.toDomain(_factoryRoom, listDataModel);
    }
}
