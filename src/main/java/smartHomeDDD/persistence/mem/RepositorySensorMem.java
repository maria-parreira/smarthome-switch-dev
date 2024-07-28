package smartHomeDDD.persistence.mem;

import org.springframework.dao.DataIntegrityViolationException;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.repository.IRepositorySensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;

import java.util.*;

/**
 * RepositorySensorMem is a class that implements the IRepositorySensor interface for Sensor objects.
 * It provides methods for storing Sensor objects and retrieving them using their SensorID.
 */
public class RepositorySensorMem implements IRepositorySensor {
    /**
     * A HashMap used for storing Sensor objects. The SensorID of a Sensor object is used as the key.
     */
    private final Map<SensorID, Sensor> DATA = new HashMap<>();

    /**
     * Saves the provided Sensor entity in the repository.
     * If a Sensor with the same identity already exists in the repository, an IllegalArgumentException is thrown.
     * Otherwise, the Sensor entity is stored in the repository and returned.
     *
     * @param entity the Sensor entity to save.
     * @return the saved Sensor entity.
     * @throws IllegalArgumentException if a Sensor with the same identity already exists in the repository.
     */
    @Override
    public Sensor save(Sensor entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Sensor cannot be null");
        }
        if (containsOfIdentity(entity.identity())) {
            throw new DataIntegrityViolationException("Sensor already exists");
        }
        DATA.put(entity.identity(), entity);
        return entity;
    }

    /**
     * Retrieves all Sensor objects stored in the repository.
     *
     * @return An Iterable containing all Sensor objects in the repository.
     */
    @Override
    public Iterable<Sensor> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves a Sensor object with the specified SensorID from the repository.
     *
     * @param id The SensorID of the Sensor object to be retrieved.
     * @return An Optional containing the Sensor object if it exists in the repository, or an empty Optional if it does not.
     */
    @Override
    public Optional<Sensor> ofIdentity(SensorID id) {
        if (!containsOfIdentity(id))
            return Optional.empty();
        else
            return Optional.of(DATA.get(id));
    }

    /**
     * Checks if a Sensor object with the specified SensorID exists in the repository.
     *
     * @param id The SensorID of the Sensor object to be checked.
     * @return true if a Sensor object with the specified SensorID exists in the repository, false otherwise.
     */
    @Override
    public boolean containsOfIdentity(SensorID id) {
        for (SensorID sensorID : DATA.keySet())
            if (sensorID.equals(id)) {
                return true;
            }
        return false;
    }

    /**
     * Retrieves a list of Sensor objects that are associated with a specific Device.
     *
     * @param id The ID of the Device for which to retrieve the associated Sensors.
     * @return A list of Sensor objects associated with the specified Device.
     */

    @Override
    public List<Sensor> getSensorsByDeviceID(DeviceId id) {
        List<Sensor> sensorsInDevice = new ArrayList<>();
        Iterable<Sensor> allSensors = findAll();
        for (Sensor sensor : allSensors) {
            if (sensor.getDeviceID().equals(id)) {
                sensorsInDevice.add(sensor);
            }
        }
        return sensorsInDevice;
    }

    @Override
    public SensorID containsOfIdentitySI(SensorID id) {
        for (SensorID sensorID : DATA.keySet())
            if (sensorID.equals(id)) {
                return sensorID;
            }
        return null;
    }

}
