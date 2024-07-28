package smartHomeDDD.domain.actuatorModel;

import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;

/**
 * Factory class that implements Factory actuatorModel interface.
 * It creates actuatorModel objects.
 */
@Component
public class ImplFactoryActuatorModel implements FactoryActuatorModel {
    /**
     * The creation method of an actuator Model Object.
     * @param actuatorModelID the unique identifier of the actuator Model.
     * @return returns a actuatorModel object.
     */
    @Override
    public ActuatorModel createActuatorModel( ActuatorModelID actuatorModelID, ActuatorTypeID actuatorTypeID) {
        return new ActuatorModel(actuatorModelID, actuatorTypeID);
    }


}
