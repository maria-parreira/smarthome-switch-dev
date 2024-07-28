package smartHomeDDD.domain.actuator;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;

/**
 * This interface defines the contract for an actuator in the SmartHome domain.
 * Each actuator has an actuator type and model.
 */
public interface Actuator extends AggregateRoot<ActuatorID> {

    /**
     * Sets the current value of the actuator.
     *
     * @return The value of the actuator.
     */
    Value setValue(Value value);

    /**
     * Returns the ID of the device related to the actuator
     * @return The value of the actuator.
     */
    DeviceId getDeviceID();

    /**
     * Returns the ID of the Model related to the actuator
     * @return The value of the actuator.
     */
    ActuatorModelID getActuatorModelID();

    /**
     * Returns the current value of the actuator.
     *
     * @return The value of the actuator.
     */
    Value getValue();

}