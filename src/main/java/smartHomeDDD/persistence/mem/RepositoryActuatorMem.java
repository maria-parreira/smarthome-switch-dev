package smartHomeDDD.persistence.mem;

import smartHomeDDD.domain.repository.IRepositoryActuator;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.DeviceId;

import java.util.*;

/**
 * RepositoryActuator is a class that implements the Repository interface for actuator objects.
 * It provides methods for storing actuator objects and retrieving them using their ActuatorID.
 */
public class RepositoryActuatorMem implements IRepositoryActuator {

    /**
     * The map to store actuator objects mapped by their ActuatorID.
     */
    private final Map<ActuatorID, Actuator> DATA = new HashMap<>();

    /**
     * Saves the provided actuator entity in the repository.
     *
     * @param entity the actuator entity to save.
     * @return the saved actuator entity.
     */
    @Override
    public Actuator save(Actuator entity) {
        if (entity == null) {
            throw new IllegalArgumentException("actuator cannot be null");
        }
        if (containsOfIdentity(entity.identity())) {
            throw new IllegalArgumentException("actuator already exists");
        }
        DATA.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Update the provided actuator entity.
     * @param entity The actuator entity to save.
     * @return The saved actuator entity.
     */
    @Override
    public Actuator update(Actuator entity) {
        DATA.put(entity.identity(), entity );
        return entity;
    }


    /**
     * Retrieves all actuator entities from the repository.
     *
     * @return an iterable containing all actuator entities.
     */
    @Override
    public Iterable<Actuator> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves an actuator entity from the repository using its ActuatorID.
     *
     * @param id The ActuatorID of the actuator entity to retrieve.
     * @return The actuator entity with the specified ActuatorID.
     */
    @Override
    public Optional<Actuator> ofIdentity(ActuatorID id) {
        if (!containsOfIdentity(id))
            return Optional.empty();
        else
            return Optional.of(DATA.get(id));
    }

    /**
     * Retrieves the ActuatorID of the actuator entity with the specified ActuatorID from the repository.
     *
     * @param id The ActuatorID of the actuator entity to retrieve.
     * @return The ActuatorID of the actuator entity with the specified ActuatorID.
     */
    @Override
    public boolean containsOfIdentity(ActuatorID id) {
        for (ActuatorID actuatorID : DATA.keySet()) {
            if (actuatorID.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a list of Actuator objects that are associated with a specific Device.
     *
     * @param id The ID of the Device for which to retrieve the associated Actuators.
     * @return A list of Actuator objects associated with the specified Device.
     */
    @Override
    public List<Actuator> getActuatorsByDeviceID(DeviceId id) {
        List<Actuator> actuatorsInDevice = new ArrayList<>();
        Iterable<Actuator> allSensors = findAll();
        for (Actuator actuator : allSensors) {
            if (actuator.getDeviceID().equals(id)) {
                actuatorsInDevice.add(actuator);
            }
        }
        return actuatorsInDevice;
    }


}
