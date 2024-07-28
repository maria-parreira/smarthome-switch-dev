package smartHomeDDD.domain.sensorType;

import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;


/**
 * The FactorySensorType interface outlines the requirements for a factory class that constructs sensorType objects.
 */
public interface FactorySensorType {

    /**
     * Constructs a sensorType object using the provided parameters.
     *
     * @param sensorTypeID The unique identifier for the sensor type.
     * @param description  The description associated with the sensor type.
     * @param unit         The unit of measurement used by the sensor type.
     * @return A sensorType object constructed with the provided parameters.
     */
    SensorType createSensorType(SensorTypeID sensorTypeID, Description description, Unit unit);
}

