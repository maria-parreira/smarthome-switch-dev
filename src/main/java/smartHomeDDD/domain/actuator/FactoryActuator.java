package smartHomeDDD.domain.actuator;

import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;

/**
 * Factory interface for creating actuator objects.
 */
public interface FactoryActuator {

    /**
     * Instantiates an actuator Object using the specified parameters.
     * @param actuatorID        The unique identifier of the actuator.
     * @param deviceID          The unique identifier of the device which the actuator belongs to.
     * @param actuatorModelID   The model of the actuator.
     * @return returns a valid actuator Object
     */
    Actuator createActuator(ActuatorID actuatorID, DeviceId deviceID,
                            ActuatorModelID actuatorModelID
                           );
}