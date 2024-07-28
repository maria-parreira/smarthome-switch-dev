package smartHomeDDD.domain.valueobject;
import smartHomeDDD.ddd.DomainId;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * This class represents the ID of an actuator model.
 * It implements the DomainID interface.
 */
public class ActuatorModelID implements DomainId {
    /**
     * The unique identifier of the ActualModel
     */
    private final String _id;

    /**
     * Constructs a ActuatorModelID object with the specified actuator model ID value.
     * Throws an IllegalArgumentException if the actuator model ID value is null or empty.
     *
     * @param id The actuator model ID value.
     * @throws IllegalArgumentException If the actuator model ID value is null or empty.
     */
    public ActuatorModelID(String id)
    {

        if (!isActuatorModelIDNameValid(id)) {
            throw new IllegalArgumentException("The ID of the actuator model cannot be null or empty");
        }
        if (!isActuatorModelInListOfModels(id)) {
            throw new IllegalArgumentException("actuator Model name does not exist in list");
        }

        this._id = id;
    }

    /**
     * Checks if the provided actuator model name is valid.
     *
     * @param modelName The actuator model name to validate.
     * @return true if the actuator model name is not null, not empty, and not blank; false otherwise.
     */
    private boolean isActuatorModelIDNameValid(String modelName)
    {
        return modelName != null && !modelName.isBlank() && !modelName.isEmpty();
    }


    /**
     * Checks if the provided actuator model name is in the list of models.
     *
     * @param actuatorModelName The actuator model name to check.
     * @return true if the actuator model name is in the list of models; false otherwise.
     */
    public boolean isActuatorModelInListOfModels(String actuatorModelName)
    {
        String[] actuatorModels = generateActuatorModelList("config.properties");
        String prefixedModelName = "actuators." + actuatorModelName;
        for (String actuatorModel : actuatorModels) {
            if (actuatorModel.equals(prefixedModelName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a list of actuator models from a configuration file.
     *
     * @param filePathname The path of the configuration file.
     * @return An array of actuator models.
     */
    public static String[] generateActuatorModelList(String filePathname)
    {
        try {
            Configurations configs = new Configurations();
            Configuration config = configs.properties(new File(filePathname));
            return config.getStringArray("actuator");
        } catch (ConfigurationException e) {
            throw new IllegalArgumentException("Something went wrong in reading the configuration: " + e.getMessage());
        }
    }


    /**
     * Tests whether an object is equal to ActuatorModelID one.
     * @param object the object to compare.
     * @return returns true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof ActuatorModelID actuatorModelID) {
            return this._id.equals(actuatorModelID._id);
        }
        return false;
    }

    /**
     * Returns the string representation of the actuator model ID.
     * @return returns the string representation of the actuator model ID.
     */
    @Override
    public String toString() {
        return _id;
    }

    /**
     * Returns the hash code of the actuator model ID.
     * @return returns the hash code of the actuator model ID.
     */
    @Override
    public int hashCode() {
        return _id.hashCode();
    }
}

