package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.repository.IRepositoryActuatorModel;
import smartHomeDDD.domain.valueobject.*;

import java.util.List;
import java.util.Optional;

/**
 * The ServiceActuatorModel class provides services related to actuatorModel objects.
 * It uses a FactoryActuatorModel to create actuatorModel objects and IRepositoryActuatorModel to store and retrieve them.
 */

@Service
public class ServiceActuatorModel {
    /**
     * Repository of actuator models.
     */
    IRepositoryActuatorModel _repoActuatorModel;
    /**
     * Factory of actuator models.
     */
    final FactoryActuatorModel _factoryActuatorModel;

    /**
     * Constructor for the ServicesActuatorModels class.
     * @param repoActuatorModel    Repository of actuator models.
     * @param factoryActuatorModel Factory of actuator models.
     * @throws IllegalArgumentException If the repository or factory is null.
     */
    public ServiceActuatorModel(IRepositoryActuatorModel repoActuatorModel, FactoryActuatorModel factoryActuatorModel) {
        if (repoActuatorModel == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        if (factoryActuatorModel == null) {
            throw new IllegalArgumentException("Factory cannot be null");
        }
        this._repoActuatorModel = repoActuatorModel;
        this._factoryActuatorModel = factoryActuatorModel;
    }

    /**
     * Get all actuator models for a given actuator type.
     * @param actuatorTypeID actuator type.
     * @return List of actuator models.
     */
    public List<ActuatorModel> getModelsByActuatorType(ActuatorTypeID actuatorTypeID) {
        return _repoActuatorModel.getModelsByActuatorType(actuatorTypeID);
    }

    /**
     * Get actuator model by ID.
     * @param id actuator model ID.
     * @return Actuator model.
     */
    public ActuatorModel getActuatorModelByID(ActuatorModelID id) {
        Optional<ActuatorModel> actuatorModel = _repoActuatorModel.ofIdentity(id);
        if (actuatorModel.isPresent()) {
            return actuatorModel.get();
        } else
            throw new EntityNotFoundException("Actuator Model not found.");
    }

    /**
     * Creates a new actuatorModel object and saves it in the repository.
     * @param actuatorTypeID the ID of the actuator type
     * @param actuatorModelID the ID of the actuator model
     * @return the actuatorModel object that was created and saved.
     */
    public ActuatorModel createActuatorModel(ActuatorModelID actuatorModelID,ActuatorTypeID actuatorTypeID) {
        ActuatorModel actuatorModel = _factoryActuatorModel.createActuatorModel(actuatorModelID, actuatorTypeID);
        return _repoActuatorModel.save(actuatorModel);
    }
}

