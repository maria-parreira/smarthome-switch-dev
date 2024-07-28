package smartHomeDDD.domain.sensorModel;

import smartHomeDDD.domain.valueobject.*;

/**
 * The FactorySensorModel interface outlines the requirements for a factory class that constructs sensorModel objects.
 */

public interface FactorySensorModel {

    /**
     * Constructs a sensorModel object using the provided parameters.
     *
     * @param sensorModelID The unique identifier for the Sensor Model, which should be from a list of existing Models.
     * @param sensorTypeID  The unique identifier for the Sensor Type associated with the Sensor Model.
     * @return A sensorModel object constructed with the provided parameters.
     */
    SensorModel createSensorModel(SensorModelID sensorModelID, SensorTypeID sensorTypeID);
}
