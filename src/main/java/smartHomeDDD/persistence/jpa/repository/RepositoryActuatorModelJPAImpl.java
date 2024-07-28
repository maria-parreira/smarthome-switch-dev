package smartHomeDDD.persistence.jpa.repository;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.repository.IRepositoryActuatorModel;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.persistence.jpa.datamodel.ActuatorModelDataModel;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

/**
 * This class provides a JPA implementation of the IRepositoryActuatorModel interface.
 * It provides methods for performing CRUD operations on actuatorModel entities in a database.
 */
public class RepositoryActuatorModelJPAImpl implements IRepositoryActuatorModel {

    /**
     * The factory for creating actuator Model instances.
     */
    final FactoryActuatorModel _factoryActuatorModel;

    /**
     * The EntityManager used for database operations.
     */
    final EntityManager _manager;

    /**
     * Constructs a RepositoryActuatorModelJPA object with the specified FactoryActuatorModel.
     * @param factoryActuatorModel The factory for creating actuator Model instances.
     */
    public RepositoryActuatorModelJPAImpl(FactoryActuatorModel factoryActuatorModel, EntityManager manager) {
        this._factoryActuatorModel = factoryActuatorModel;
        this._manager = manager;
    }

    /**
     * Retrieves the EntityManager for database operations.
     * @return The EntityManager instance.
     */
    private EntityManager getEntityManager() {
        return _manager;
    }

    /**
     * Saves an actuator Model entity to the database.
     * @param actuatorModel the model of the actuator object
     * @return an actuator Model object if it is successfully saved in the repository
     */
    @Override
    public ActuatorModel save(ActuatorModel actuatorModel) {
        if (actuatorModel == null) {
            throw new IllegalArgumentException("actuator Model cannot be null");
        }
        if (containsOfIdentity(actuatorModel.identity())) {
            throw new IllegalArgumentException("actuator Model already exists");
        }

        ActuatorModelDataModel actuatorModelDataModel = new ActuatorModelDataModel(actuatorModel);

        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(actuatorModelDataModel);
        tx.commit();
        em.close();

        return actuatorModel;
    }

    /**
     * Retrieves all actuatorModel objects stored in the repository.
     * @return a list of every actuatorModel object saved in the repository
     */
    @Override
    public Iterable<ActuatorModel> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM ActuatorModelDataModel e");

        List<ActuatorModelDataModel> listDataModel = query.getResultList();
        return ActuatorModelDataModel.toDomain(_factoryActuatorModel, listDataModel);
    }

    /**
     * Retrieves an actuator Model entity from the database by its ActuatorModelID.
     * @param id The ActuatorModelID of the actuator Model to retrieve.
     * @return An Optional containing the actuator Model if it exists otherwise it returns an empty Optional.
     */
    @Override
    public Optional<ActuatorModel> ofIdentity(ActuatorModelID id) {
        ActuatorModelDataModel actuatorModelDataModel = getEntityManager().find(ActuatorModelDataModel.class, id.toString());

        if (actuatorModelDataModel != null) {
            ActuatorModel actuatorModelDomain = ActuatorModelDataModel.toDomain(_factoryActuatorModel, actuatorModelDataModel);
            return Optional.of(actuatorModelDomain);
        } else
            return Optional.empty();
    }

    /**
     * A method to check if an actuator Model with the specified ActuatorModelID exists in the database.
     * @param id The ActuatorModelID of the actuator Model to check for.
     * @return true if the actuator Model exists, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(ActuatorModelID id) {
        Optional<ActuatorModel> actuatorModel = ofIdentity(id);
        return actuatorModel.isPresent();
    }

    /**
     * A method to retrieve all actuator Models with the specified ActuatorTypeID from the database.
     * @param actuatorTypeID The ActuatorTypeID of the actuator Model to retrieve.
     * @return A list of actuator Models with the specified ActuatorTypeID.
     */
    @Override
    public List<ActuatorModel> getModelsByActuatorType(ActuatorTypeID actuatorTypeID) {
        Query query = getEntityManager().createQuery("SELECT e FROM ActuatorModelDataModel e WHERE e.actuatorTypeID = :actuatorTypeID");
        query.setParameter("actuatorTypeID", actuatorTypeID.toString());
        List<ActuatorModelDataModel> resultList = query.getResultList();
        return ActuatorModelDataModel.toDomain(_factoryActuatorModel, resultList);
    }
}