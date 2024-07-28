package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.DomainId;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * The SensorModelID class represents the unique identifier of a sensor model in the Smart Home domain.
 * It implements the DomainId interface.
 */
public class SensorModelID implements DomainId {
    /**
     * The unique identifier for the sensor model.
     */
    private final String _id;

    /**
     * Constructs a SensorModelID object with the specified sensor model ID value.
     * Throws an IllegalArgumentException if the sensor model ID value is null or empty.
     *
     * @param id The sensor model ID value.
     * @throws IllegalArgumentException If the sensor model ID value is null or empty.
     */
    public SensorModelID(String id) {

        if (!isSensorModelIDNameValid(id)) {
            throw new IllegalArgumentException("The ID of the sensor model cannot be null or empty");
        }
        if (!isSensorModelNameInListOfModels(id)) {
            throw new IllegalArgumentException("Sensor Model name does not exist in list");
        }

        this._id = id;
    }


    /**
     * Checks if the provided sensor model name is valid.
     *
     * @param modelName The sensor model name to validate.
     * @return true if the sensor model name is not null, not empty, and not blank; false otherwise.
     */
    private boolean isSensorModelIDNameValid(String modelName) {
        return modelName != null && !modelName.isBlank() && !modelName.isEmpty();
    }


    /**
     * Checks if the provided sensor model name is in the list of models.
     *
     * @param modelName The sensor model name to check.
     * @return true if the sensor model name is in the list of models; false otherwise.
     */
    public boolean isSensorModelNameInListOfModels(String modelName) {
        String[] actuatorModels = generateModelList();
        String prefixedModelName = "sensors." + modelName;
        for (String sensorModel : actuatorModels) {
            if (sensorModel.equals(prefixedModelName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Generates a list of sensor models from a configuration file.
     *
     *  "filePathname" The path of the configuration file.
     * @return An array of sensor models.
     */
    private static String[] generateModelList() {
        try {
            String filePathname = "config.properties";
            Configurations configs = new Configurations();
            Configuration config = configs.properties(new File(filePathname));
            return config.getStringArray("sensor");
        } catch (ConfigurationException e) {
            throw new IllegalArgumentException("Something went wrong in reading the configuration: " + e.getMessage());
        }
    }


    /**
     * Checks if this SensorModelID object is equal to another object.
     * Two SensorModelID objects are considered equal if they have the same sensor model ID value.
     *
     * @param object The object to compare with this SensorModelID.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof SensorModelID sensorModelID) {
            return this._id.equals(sensorModelID._id);
        }
        return false;
    }

    /**
     * Returns a string representation of the SensorModelID object.
     *
     * @return The sensor model ID value as a string.
     */
    @Override
    public String toString() {
        return _id;
    }

    /**
     * Returns the hash code value for this object.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}
