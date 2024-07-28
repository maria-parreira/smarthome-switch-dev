package smartHomeDDD.domain.sensorType;

import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;

/**
 * The ImplFactorySensorType class is a concrete implementation of the FactorySensorType interface.
 * It is tasked with constructing sensorType objects using the given parameters.
 */
@Component
public class ImplFactorySensorType implements FactorySensorType{

    /**
     * Constructs a sensorType object using the provided parameters.
     *
     * @param sensorTypeID The unique identifier for the sensor type.
     * @param description  The description associated with the sensor type.
     * @param unit         The unit of measurement used by the sensor type.
     * @return A sensorType object constructed with the provided parameters.
     */
        @Override
        public SensorType createSensorType(SensorTypeID sensorTypeID, Description description, Unit unit) {
            return new SensorType(unit, description, sensorTypeID);
        }

}

