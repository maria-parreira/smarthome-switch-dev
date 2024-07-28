package smartHomeDDD.domain.actuator;

import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory class for the creation of actuator Objects.
 * It implements the FactoryActuator Interface.
 */
@Component
public class ImplFactoryActuator implements FactoryActuator {

    /**
     * Instantiates an actuator Object using the specified parameters.
     *
     * @param actuatorID        The unique identifier of the actuator.
     * @param deviceID          The unique identifier of the device which the actuator belongs to.
     * @param actuatorModelID   The model of the actuator to be created.
     * @return returns a valid actuator Object
     */
    @Override
    public Actuator createActuator(ActuatorID actuatorID, DeviceId deviceID,
                                   ActuatorModelID actuatorModelID)
    {

        Object[] arguments = {actuatorID, deviceID, actuatorModelID};
        Class[] classTypes = {ActuatorID.class, DeviceId.class, ActuatorModelID.class};

        try {
            String actuatorModelName = actuatorModelID.toString();
            String strModelPath = "smartHomeDDD.domain.actuator." + actuatorModelName;
            return (Actuator) Class.forName(strModelPath).getConstructor(classTypes).newInstance(arguments);
        } catch (ClassNotFoundException |
                 NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            return null;
        }
    }

}
