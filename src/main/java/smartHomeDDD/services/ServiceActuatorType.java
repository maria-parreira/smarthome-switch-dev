package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.FactoryActuatorType;
import smartHomeDDD.domain.repository.IRepositoryActuatorType;
import smartHomeDDD.domain.valueobject.*;

import java.util.Optional;

/**
 * The ServiceActuatorType class provides services related to ActuatorType objects.
 * It uses a FactoryActuatorType to create ActuatorType objects and IRepositoryActuatorType to store and retrieve them.
 */
@Service
public class ServiceActuatorType {
    /**
     * Repository of actuator types.
     */
    final IRepositoryActuatorType _repoActuatorType;

    /**
     * Factory of actuator types.
     */
    final FactoryActuatorType _factoryActuatorType;

    /**
     * Constructor for the ServiceActuatorType class.
     * @param repoActuatorType    Repository of actuator types.
     * @param factoryActuatorType Factory of actuator types.
     * @throws IllegalArgumentException If the repository or factory is null.
     */
    public ServiceActuatorType(IRepositoryActuatorType repoActuatorType, FactoryActuatorType factoryActuatorType) {
        if (repoActuatorType == null)
            throw new IllegalArgumentException("Repository cannot be null");
        if (factoryActuatorType == null)
            throw new IllegalArgumentException("Factory cannot be null");
        this._repoActuatorType = repoActuatorType;
        this._factoryActuatorType = factoryActuatorType;
    }

    /**
     * List all actuator types.
     * @return List of actuator types.
     */
    public Iterable<ActuatorType> getActuatorTypes() {
        return _repoActuatorType.findAll();
    }

    /**
     * Retrieve an actuator type by its identifier.
     * @param actuatorTypeID The identifier of the actuator type.
     * @return The actuator type.
     */
    public ActuatorType getActuatorTypeById(ActuatorTypeID actuatorTypeID) {
        Optional<ActuatorType> actuatorType = _repoActuatorType.ofIdentity(actuatorTypeID);
        if (actuatorType.isPresent()) {
            return actuatorType.get();
        }
        throw new EntityNotFoundException("Actuator Type not found.");
    }

    /**
     * Creates a new actuatorType object and saves it in the repository.
     * @param actuatorTypeID the ID of the actuator type.
     * @param description  the description of the actuator type.
     * @param unit         the unit of the actuator type.
     * @return the actuatorType object that was created and saved.
     */
    public ActuatorType createActuatorType(ActuatorTypeID actuatorTypeID, Description description, Unit unit) {
        ActuatorType actuatorType = _factoryActuatorType.createActuatorType(unit, description, actuatorTypeID);
        return _repoActuatorType.save(actuatorType);
    }
}
