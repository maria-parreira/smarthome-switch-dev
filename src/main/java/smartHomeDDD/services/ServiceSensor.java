package smartHomeDDD.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import smartHomeDDD.domain.repository.IRepositoryDevice;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

import java.util.List;
import java.util.Optional;

/**
 * The ServiceSensor class provides services related to Sensor objects.
 * It uses a FactorySensor to create Sensor objects and repositories to store and retrieve them.
 */
@Service
public class ServiceSensor {
    /**
     * The FactorySensor used to create Sensor objects.
     */
    final FactorySensor factorySensor;
    /**
     * The RepositorySensor used to store and retrieve Sensor objects.
     */
    final IRepositorySensor repoSensor;

    /**
     * The RepositoryDevice used to store and retrieve Device objects.
     */
    final IRepositoryDevice repoDevice;

    /**
     * The service used to generate random ids.
     */
    final GenerateRandomId generateRandomId;

    /**
     * The SensorModelID of the temperature sensor.
     */
    static final String TEMP_SENSOR_MODEL_ID = "GA100K";

    /**
     * Constructs a new ServiceSensor with the specified FactorySensor and repositories.
     * Throws an IllegalArgumentException if any of the parameters are null.
     *
     * @param factorySensor the FactorySensor to be used by the service
     * @param repoSensor    the RepositorySensor to be used by the service
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public ServiceSensor(FactorySensor factorySensor, IRepositorySensor repoSensor, IRepositoryDevice repoDevice, GenerateRandomId generateRandomId) {
        if (factorySensor == null)
            throw new IllegalArgumentException("Factory cannot be null.");
        if (repoSensor == null)
            throw new IllegalArgumentException("Repository cannot be null.");
        if (repoDevice == null) {
            throw new IllegalArgumentException("Repository cannot be null.");
        }
        if (generateRandomId == null) {
            throw new IllegalArgumentException("GenerateRandomId cannot be null.");
        }

        this.factorySensor = factorySensor;
        this.repoSensor = repoSensor;
        this.repoDevice = repoDevice;
        this.generateRandomId = generateRandomId;
    }

    /**
     * Creates a new Sensor and saves it to the repository.
     * This method uses the FactorySensor to create a new Sensor with the provided parameters, and then saves the created Sensor to the RepositorySensor.
     *
     * @param deviceID      the DeviceId of the device to which the new Sensor will be added
     * @param sensorModelID the SensorModelID of the sensorModel that the new Sensor will be based on
     * @return the created Sensor after it has been saved to the RepositorySensor
     */
    public Sensor createNewSensor(DeviceId deviceID, SensorModelID sensorModelID) {
        if (!repoDevice.containsOfIdentity(deviceID)) {
            throw new EntityNotFoundException("Device not found");
        }
        SensorID sensorID = new SensorID(generateRandomId.generateID());
        Sensor sensorToBeCreated = this.factorySensor.createSensor(deviceID, sensorModelID, sensorID);
        return this.repoSensor.save(sensorToBeCreated);
    }

    /**
     * Lists all sensors in a device.
     *
     * @param id The id of the device to list sensors from.
     * @return A list of sensors in the device.
     */
    public List<Sensor> getSensorsByDeviceID(DeviceId id) {
        if (!repoDevice.containsOfIdentity(id)) {
            throw new EntityNotFoundException("Device not found");
        }

        return repoSensor.getSensorsByDeviceID(id);
    }


    /**
     * Retrieves a sensor by its ID.
     *
     * @param id The id of the sensor to be retrieved.
     * @return a sensor if it is present in the repository
     * @throws EntityNotFoundException if it is empty
     */
    public Sensor getSensor(SensorID id) {
        Optional<Sensor> sensorOpt = repoSensor.ofIdentity(id);
        if(sensorOpt.isPresent()){
            return sensorOpt.get();
        }
        else
            throw new EntityNotFoundException("Sensor not found.");
    }

    /**
     * Checks if a sensor is a temperature sensor.
     *
     * @param sensor The sensor to check.
     * @return True if the sensor is a temperature sensor, false otherwise.
     */

    public boolean isSensorOfTemperature(Sensor sensor) {
        return (sensor.getSensorModelID().toString().equals(TEMP_SENSOR_MODEL_ID));
    }



}