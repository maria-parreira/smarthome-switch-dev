package smartHomeDDD.persistence.springdata;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.FactoryActuator;
import smartHomeDDD.domain.repository.IRepositoryActuator;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.persistence.jpa.datamodel.ActuatorDataModel;

import java.util.List;
import java.util.Optional;

/**
 * Repository implementation for managing actuator entities using Spring Data.
 * This class provides methods to interact with the persistence layer for actuator entities.
 */
@Repository
public class RepositoryActuatorSpringData implements IRepositoryActuator
{
    /**
     * The Spring Data repository for ActuatorDataModel entities.
     */
    final IRepositoryActuatorSpringData _repositoryActuatorSpringData;
    /**
     * The factory for creating actuator domain objects.
     */
    final FactoryActuator _factoryActuator;

    /**
     * Constructor to initialize RepositoryActuatorSpringData.
     * @param factoryActuator              The factory for creating actuator objects
     * @param repositoryActuatorSpringData The Spring Data repository for actuator entities
     */
    public RepositoryActuatorSpringData(FactoryActuator factoryActuator, IRepositoryActuatorSpringData repositoryActuatorSpringData)
    {
        this._factoryActuator = factoryActuator;
        this._repositoryActuatorSpringData = repositoryActuatorSpringData;
    }

    /**
     * Persists an actuator entity.
     * @param actuator The actuator entity to save
     * @return The persisted actuator entity
     * @throws IllegalArgumentException if the provided actuator object is null
     */
    @Override
    public Actuator save(Actuator actuator)
    {
        if (actuator == null) {
            throw new IllegalArgumentException("actuator cannot be null");
        }
        if (containsOfIdentity(actuator.identity())) {
            throw new DataIntegrityViolationException("actuator already exists");
        }
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(actuator);
        this._repositoryActuatorSpringData.save(actuatorDataModel);
        return actuator;
    }

    /**
     * Update an actuator in the database
     * @param entity the actuator to update
     * @return the updated actuator
     */
    @Override
    public Actuator update(Actuator entity) {

        String actuatorID = entity.identity().toString();

        Optional<ActuatorDataModel> actuatorDataModel = this._repositoryActuatorSpringData.findById(actuatorID);

        if (actuatorDataModel.isPresent()) {
            boolean isUpdated = actuatorDataModel.get().updateFromDomain(entity);

            if (isUpdated) {
                ActuatorDataModel actuatorDataModelSaved = this._repositoryActuatorSpringData.save(actuatorDataModel.get());

                return ActuatorDataModel.toDomain(_factoryActuator,actuatorDataModelSaved);

            } else
                throw new IllegalArgumentException("Actuator cannot be updated");
        } else
            throw new IllegalArgumentException("Actuator cannot found");
    }

    /**
     * Retrieves all actuator entities.
     * @return An Iterable containing all actuator entities.
     */
    @Override
    public Iterable<Actuator> findAll()
    {
        List<ActuatorDataModel> listActuatorDataModelSaved = this._repositoryActuatorSpringData.findAll();
        return ActuatorDataModel.toDomain(_factoryActuator, listActuatorDataModelSaved);
    }

    /**
     * Retrieves an actuator entity by its identity.
     * @param id The identity of the actuator entity
     * @return An Optional containing the actuator entity, or empty if not found
     */
    @Override
    public Optional<Actuator> ofIdentity(ActuatorID id)
    {
        Optional<ActuatorDataModel> optionalActuatorDataModel = this._repositoryActuatorSpringData.findById(id.toString());
        if (optionalActuatorDataModel.isPresent()) {
            Actuator actuatorDomain = ActuatorDataModel.toDomain(_factoryActuator, optionalActuatorDataModel.get());
            return Optional.of(actuatorDomain);
        }
        return Optional.empty();
    }

    /**
     * Checks if an actuator entity with the provided identity exists.
     * @param id The identity of the actuator entity
     * @return True if the actuator entity exists, false otherwise
     */
    @Override
    public boolean containsOfIdentity(ActuatorID id)
    {
        return _repositoryActuatorSpringData.existsById(id.toString());
    }


    /**
     * Retrieves all Actuator entities associated with a Device.
     *
     * @param id The identity of the Device
     * @return A list of actuator entities associated with the Device
     */
    @Override
    public List<Actuator> getActuatorsByDeviceID(DeviceId id) {
        List<ActuatorDataModel> actuatorDataModels = this._repositoryActuatorSpringData.findByDeviceId(id.toString());
        return ActuatorDataModel.toDomain(_factoryActuator, actuatorDataModels);
    }
}
