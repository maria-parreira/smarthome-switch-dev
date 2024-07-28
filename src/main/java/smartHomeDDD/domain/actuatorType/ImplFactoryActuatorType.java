package smartHomeDDD.domain.actuatorType;

import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;
/**
 * Factory class that implements Factory ActuatorType interface.
 * It creates actuatorModel objects.
 */
@Component
public class ImplFactoryActuatorType implements FactoryActuatorType{


    /**
     * Creates a new instance of ActuatorType with the given unit, description, and actuator type ID.
     *
     * @param unit the unit associated with the actuator type.
     * @param description the description of the actuator type.
     * @param actuatorTypeID the ID of the actuator type.
     * @return a new instance of ActuatorType.
     */

    public ActuatorType createActuatorType(Unit unit, Description description, ActuatorTypeID actuatorTypeID) {
        return new ActuatorType(unit, description, actuatorTypeID);
    }

}
