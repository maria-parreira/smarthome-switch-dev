package smartHomeDDD.dto;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.valueobject.ActuatorID;

import java.io.File;
import java.lang.reflect.InvocationTargetException;


/**
 * Mapper class for converting actuator domain objects to ActuatorDTO objects.
 */
public class ActuatorMapper {

    /**
     * Given a actuatorID String, Retrieves a actuatorType Value Object.
     *
     * @param actuatorID A String actuatorID.
     * @return returns an actuatorID VO.
     */
    public static ActuatorID convertToActuatorID(String actuatorID) {
        return new ActuatorID(actuatorID);
    }


    /**
     * Given an actuator object, it converts it into an actuatorDTO
     *
     * @param myActuator the actuator Object to be passed to DTO.
     * @return returns the equivalent ActuatorDTO
     */
    public static ActuatorDTO convertToActuatorDTO(Actuator myActuator) {
        String actuatorID = myActuator.identity().toString();
        String deviceID = myActuator.getDeviceID().toString();
        String actuatorModelID = myActuator.getActuatorModelID().toString();
        return new ActuatorDTO(actuatorID, deviceID, actuatorModelID);
    }



    /**
     * Given an actuator object, it converts it into an actuatorDTO
     *
     * @param actuatorID the id of the actuator object
     * @return the equivalent ActuatorDTO
     */
    public static ActuatorIDExitWebDTO convertToIDActuatorExitWebDTO(ActuatorID actuatorID) {

        String actuatorId = actuatorID.toString();

        return new ActuatorIDExitWebDTO(actuatorId);
    }

    /**
     * Given an actuator object, it converts it into an ActuatorExitWebDTO
     *
     * @param actuator the actuator object
     * @return the equivalent ActuatorExitWebDTO
     */
    public static ActuatorExitWebDTO convertToActuatorExitWebDTO(Actuator actuator) {

        String actuatorId = actuator.identity().toString();
        String deviceId = actuator.getDeviceID().toString();
        String actuatorModelId = actuator.getActuatorModelID().toString();

        return new ActuatorExitWebDTO(actuatorId, deviceId, actuatorModelId);
    }


    /**
     * Given a String value and an actuatorModel Name, it returns the corresponding Value Object.
     *
     * @param value     Value of the actuator in String format.
     * @param modelName The corresponding model name of the actuator in String format.
     * @return Returns the respective Value object.
     */
    public static Value convertToValue(String value, String modelName) throws InstantiationException {

        Object[] arguments = new Object[1];
        Class[] classTypes = new Class[1];

        try {
            String valueType = getValueType(modelName);

            switch (valueType) {
                case "int":
                    arguments[0] = Integer.parseInt(value);
                    classTypes[0] = int.class;
                    break;

                case "double":
                    arguments[0] = Double.parseDouble(value);
                    classTypes[0] = double.class;
                    break;
                default:
                    arguments[0] = value;
                    classTypes[0] = String.class;
                    break;
            }

            String strModelPath = "smartHomeDDD.domain.valueobject." + modelName + "Value";
            return (Value) Class.forName(strModelPath).getConstructor(classTypes).newInstance(arguments);

        } catch (NumberFormatException |
                 ClassNotFoundException |
                 NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            throw new NumberFormatException("Input value not valid.");
        } catch (ConfigurationException e) {
            throw new InstantiationException("Something went wrong in reading the configuration: " + e.getMessage());
        }
    }


    /**
     * Method to determine which entry parameter type is needed
     * to construct the specific value to each actuator class.
     *
     * @param modelName The model name of the actuator.
     *                  It is used to determine the type of the value.
     * @return Returns the type of the value.
     */
    private static String getValueType(String modelName) throws ConfigurationException {

        Configurations configs = new Configurations();
        PropertiesConfiguration config = configs.properties(new File("config.properties"));
        String[] actuatorModels = config.getStringArray("actuatorModel");
        for (String actuatorModel : actuatorModels) {
            String[] actuatorModelParts = actuatorModel.split("\\.");
            if (actuatorModelParts[0].equals(modelName)) {
                return actuatorModelParts[2];
            }
        }
        throw new IllegalArgumentException("Actuator Model is not present in the system.");
    }

}
