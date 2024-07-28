/**
 * This interface represents a Sensor in the Smart Home domain.
 * It extends the AggregateRoot interface with SensorID as
 * the type of its identity.
 */
package smartHomeDDD.domain.sensor;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;

public interface Sensor extends AggregateRoot<SensorID> {

    /**
     * Getter method for the device's ID, associated with this sensor.
     *
     * @return the unique identifier of the device this sensor
     * is associated with.
     */
    DeviceId getDeviceID();

    /**
     * Getter method for the sensor model's ID, associated with this sensor.
     *
     * @return the unique identifier of the sensor model this sensor
     * is associated with.
     */
    SensorModelID getSensorModelID();

}