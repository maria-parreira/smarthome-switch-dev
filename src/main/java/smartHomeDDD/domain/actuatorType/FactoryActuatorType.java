package smartHomeDDD.domain.actuatorType;


import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;

/**
 * Factory interface for creating actuator types.
 */
public interface FactoryActuatorType {

    /**
     * Creates a new instance of ActuatorType with the given unit, description, and actuator type ID.
     * @param unit the unit associated with the actuator type.
     * @param description the description of the actuator type.
     * @param actuatorTypeID the ID of the actuator type.
     * @return a new instance of ActuatorType.
     */
    ActuatorType createActuatorType(Unit unit, Description description, ActuatorTypeID actuatorTypeID) ;

}
