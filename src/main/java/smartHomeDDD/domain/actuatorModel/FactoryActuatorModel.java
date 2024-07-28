package smartHomeDDD.domain.actuatorModel;
import smartHomeDDD.domain.valueobject.*;

/**
 * Factory interface for creating ActuatorModels.
 */
public interface FactoryActuatorModel {

    /**
     * Creates a new actuator Model object using the specified parameters.
     * @param actuatorModelID the unique identifier of the actuator Model.
     * @return returns an actuatorModel object
     */
    ActuatorModel createActuatorModel(ActuatorModelID actuatorModelID, ActuatorTypeID actuatorTypeID);

}
