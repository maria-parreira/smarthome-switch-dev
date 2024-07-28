package smartHomeDDD.domain.repository;

import smartHomeDDD.ddd.Repository;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;

import java.util.List;

/**
 * IRepositorySensor is an interface that extends the Repository interface for Sensor objects.
 * It provides methods for storing Sensor objects and retrieving them using their SensorID.
 */
public interface IRepositorySensor extends Repository<SensorID, Sensor> {

    /**
     * Retrieves a list of Sensor objects that are associated with a specific Device.
     *
     * @param id The ID of the Device for which to retrieve the associated Sensors.
     * @return A list of Sensor objects associated with the specified Device.
     */
    List<Sensor> getSensorsByDeviceID(DeviceId id);

    SensorID containsOfIdentitySI(SensorID id);
}
