package smartHomeDDD.persistence.jpa.repository;

import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.FactoryActuator;
import smartHomeDDD.domain.repository.IRepositoryActuator;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.persistence.jpa.datamodel.ActuatorDataModel;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for persisting the actuator objects in the database.
 * It uses the ActuatorDataModel class to convert the actuator objects to DataModel objects and vice versa.
 */
public class RepositoryActuatorJPAImpl implements IRepositoryActuator {
    /**
     * Factory for creating actuator objects
     */
    final FactoryActuator _factoryActuator;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * Constructor for the RepositoryActuatorJPAImpl class
     */
    public RepositoryActuatorJPAImpl(FactoryActuator factoryActuator, EntityManager manager) {
        this._factoryActuator = factoryActuator;
        this._manager = manager;
    }

    /**
     * Creates a EntityManager object.
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

    /**
     * Persists an actuator object in the database.
     *
     * @param actuator The actuator object to be persisted.
     * @return The persisted actuator object.
     */
    @Override
    public Actuator save(Actuator actuator) {
        if (actuator == null) {
            throw new IllegalArgumentException("actuator cannot be null");
        }
        if (containsOfIdentity(actuator.identity())) {
            throw new IllegalArgumentException("actuator already exists");
        }
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(actuator);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(actuatorDataModel);
        tx.commit();
        em.close();

        return actuator;
    }

    /**
     * Retrieves a list with all persisted actuators.
     *
     * @return a list with all persisted actuators.
     */
    @Override
    public Iterable<Actuator> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM ActuatorDataModel e");

        List<ActuatorDataModel> listDataModel = query.getResultList();

        return ActuatorDataModel.toDomain(_factoryActuator, listDataModel);
    }

    /**
     * Retrieves an actuator object from the database by its ID.
     *
     * @param id The ID of the actuator object to be retrieved.
     * @return The actuator object with the specified ID.
     */
    @Override
    public Optional<Actuator> ofIdentity(ActuatorID id) {
        ActuatorDataModel actuatorDataModel = getEntityManager().find(ActuatorDataModel.class, id.toString());

        if (actuatorDataModel != null) {
            return Optional.of(ActuatorDataModel.toDomain(_factoryActuator, actuatorDataModel));
        } else
            return Optional.empty();
    }

    /**
     * Checks if an actuator object with the specified ID exists in the database.
     *
     * @param id The ID of the actuator object to be checked.
     * @return True if the actuator object exists, otherwise false.
     */
    @Override
    public boolean containsOfIdentity(ActuatorID id) {
        Optional<Actuator> optionalActuator = ofIdentity(id);
        return optionalActuator.isPresent();
    }

    /**
     * Retrieves a list of Actuator objects that are associated with a specific Device.
     *
     * @param id The ID of the Device for which to retrieve the associated Actuators.
     * @return A list of Actuator objects associated with the specified Device.
     */
    @Override
    public List<Actuator> getActuatorsByDeviceID(DeviceId id) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM ActuatorDataModel e WHERE e.deviceId = :deviceId");
        query.setParameter("deviceId", id.toString());
        List<ActuatorDataModel> listDataModel = query.getResultList();

        return ActuatorDataModel.toDomain(_factoryActuator, listDataModel);
    }

    /**
     * Updates the provided Actuator entity in the repository.
     * @param entity The Actuator entity to be updated.
     * @return The updated Actuator entity.
     * @throws IllegalArgumentException if the provided Actuator is null.
     */

    @Override
    public Actuator update(Actuator entity) throws IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("Actuator cannot be null");

        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();

        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(entity);

        tx.begin();
        em.merge(actuatorDataModel);
        tx.commit();

        em.close();

        return entity;
    }


}
