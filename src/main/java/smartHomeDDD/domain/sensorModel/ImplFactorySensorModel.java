package smartHomeDDD.domain.sensorModel;

import org.springframework.stereotype.Service;
import smartHomeDDD.domain.valueobject.SensorModelID;
import smartHomeDDD.domain.valueobject.SensorTypeID;

/**
 * This class provides a concrete implementation of the FactorySensorModel interface.
 * It is responsible for creating sensorModel objects.
 */
@Service
public class ImplFactorySensorModel implements FactorySensorModel {

    /**
     * Creates a sensorModel object with the provided parameters.
     *
     * @param sensorModelID The unique identifier for the sensor model.
     * @param sensorTypeID The unique identifier for the sensor type.
     * @return A sensorModel object.
     */

    @Override
    public SensorModel createSensorModel( SensorModelID sensorModelID, SensorTypeID sensorTypeID) {
        return new SensorModel(sensorModelID, sensorTypeID);
    }
}
