package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.FactoryActuator;

import smartHomeDDD.domain.repository.IRepositoryActuator;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.*;

import java.util.List;
import java.util.Optional;

/**
 * The ServiceActuator class provides services related to actuator objects.
 * It uses a FactoryActuator to create actuator objects and repositories to store and retrieve them.
 */
@Service
public class ServiceActuator {
    /**
     * Repository of actuators.
     */
    final IRepositoryActuator _repoActuator;
    /**
     * Factory of actuators.
     */
    final FactoryActuator _factoryActuator;

    /**
     * Repository of devices.
     */
    final IRepositoryDevice _repoDevice;

    final GenerateRandomId _generateRandomId;
    /**
     * Constructor for the ServiceActuator class .
     *
     * @param repoActuator    Repository of actuators.
     * @param factoryActuator Factory of actuators.
     * @throws IllegalArgumentException If the repository or factory is null.
     */
    public ServiceActuator(IRepositoryActuator repoActuator, FactoryActuator factoryActuator, IRepositoryDevice repoDevice, GenerateRandomId generateRandomId) {
        if (repoActuator == null)
            throw new IllegalArgumentException("Repository cannot be null");
        if (factoryActuator == null)
            throw new IllegalArgumentException("Factory cannot be null");
        if (repoDevice == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        if (generateRandomId == null) {
            throw new IllegalArgumentException("GenerateRandomId cannot be null");
        }
        _repoActuator = repoActuator;
        _factoryActuator = factoryActuator;
        _repoDevice = repoDevice;
        _generateRandomId = generateRandomId;
    }

    /**
     * Adds a new actuator to a device.
     *
     * @param deviceID        the ID of the device.
     * @param actuatorModelID the model ID of the actuator.
     * @return the actuator that was added.
     */

    public Actuator addNewActuator(DeviceId deviceID, ActuatorModelID actuatorModelID) {
        if (!_repoDevice.containsOfIdentity(deviceID)) {
            throw new EntityNotFoundException("Device not found.");
        }
        ActuatorID actuatorID = new ActuatorID(_generateRandomId.generateID());
        Actuator actuator = this._factoryActuator.createActuator(actuatorID, deviceID, actuatorModelID);
        return this._repoActuator.save(actuator);
    }



    /**
     * Lists all actuators in a device.
     *
     * @param id The id of the device to list actuators from.
     * @return A list of actuators in the device.
     * @throws EntityNotFoundException if the device is not found.
     */
    public List<Actuator> getActuatorsByDeviceID(DeviceId id) {
        if (!_repoDevice.containsOfIdentity(id)) {
            throw new EntityNotFoundException("Device not found");
        }
        return _repoActuator.getActuatorsByDeviceID(id);
    }

    /**
     * Retrieves an actuator by its ID.
     *
     * @param id The id of the actuator to be retrieved.
     * @return an actuator if it is present in the repository
     * @throws EntityNotFoundException if it is empty
     */
    public Actuator getActuator(ActuatorID id) {
        Optional<Actuator> actuatorOpt = _repoActuator.ofIdentity(id);
        if(actuatorOpt.isPresent()){
            return actuatorOpt.get();
        }
        else
            throw new EntityNotFoundException("Actuator not found.");
    }

    /**
     * Updates the roller blind value.
     * It then sets the actuator to the new value.
     *
     * @param actuator The actuator to update its value.
     * @param newValue The new value to set the actuator to.
     * @return The actuator with the new value set.
     * @throws IllegalArgumentException If the shouldCloseRollerBlind is unable to find or modify the actuator.
     */
    public Actuator updateRollerBlind(Actuator actuator, OPNCL0100Value newValue) {
        actuator.setValue(newValue);
        _repoActuator.update(actuator);
        return actuator;
    }

    /**
     * Verifies if the sensor and actuator belong to same device, and have the correct models to
     * close the roller blind (CAP200 sensor, a sensor to measure a percentage capacity, and OPNCL0100 actuator,
     * an actuator to close the roller blind).
     *
     * @param actuator the chosen actuator
     * @param sensor   the chosen sensor
     * @throws IllegalArgumentException if the sensor and actuator don't belong to the same device or are not of the specified models.
     */

    public void areActuadorAndSensorInSameDevice(Actuator actuator, Sensor sensor){
        if(!actuator.getDeviceID().equals(sensor.getDeviceID())){
            throw new IllegalArgumentException("The sensor and/or actuator are not from the same device.");
        }
        if(!isOPNCL0100(actuator) || !isCAP200sensor(sensor)){
            throw new IllegalArgumentException("The actuator and/or sensor doesn't have the correct model.");
        }
    }

    /**
     * Checks if an actuator is a OPNCL0100 actuator.
     *
     * @param actuator The actuator to check.
     * @return True if the actuator is a OPNCL0100 actuator, false otherwise.
     */
    private Boolean isOPNCL0100(Actuator actuator) {
        return actuator.getActuatorModelID().toString().equals("OPNCL0100");
    }

    /**
     * Checks if a sensor is from the model CAP200.
     *
     * @param sensor The sensor to check.
     * @return true if it is from the model CAP200.
     */
    private Boolean isCAP200sensor(Sensor sensor) {
        return sensor.getSensorModelID().toString().equals("CAP200");
    }


}