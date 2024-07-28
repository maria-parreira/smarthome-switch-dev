package smartHomeDDD.persistence.jpa.datamodel;

import smartHomeDDD.domain.actuator.Actuator;
import smartHomeDDD.domain.actuator.FactoryActuator;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data model for the actuator entity in the database.
 * It provides methods to convert between the actuator domain object and the ActuatorDataModel.
 */
@Entity
@Table(name = "ACTUATOR")
public class ActuatorDataModel {
    /**
     * The ID of the actuator.
     */
    @Id
    private String actuatorID;
    /**
     * The ID of the related Device.
     */
    private String deviceId;
    /**
     * The ID of the related Model.
     */
    private String modelID;

    public ActuatorDataModel() {
    }

    /**
     * ActuatorDataModel Constructor with actuator parameter.
     * @param actuator The actuator to be converted to a data model.
     */
    public ActuatorDataModel(Actuator actuator) {
        this.actuatorID = actuator.identity().toString();
        this.deviceId = actuator.getDeviceID().toString();
        this.modelID = actuator.getActuatorModelID().toString();
    }

    /**
     * Converts an ActuatorDataModel to an actuator object.
     * @param factoryActuator   The factory to create the actuator object.
     * @param actuatorDataModel The ActuatorDataModel to be converted.
     * @return The actuator object.
     */
    public static Actuator toDomain(FactoryActuator factoryActuator, ActuatorDataModel actuatorDataModel) {
        ActuatorID actuatorID = new ActuatorID(actuatorDataModel.actuatorID);
        DeviceId deviceId = new DeviceId(actuatorDataModel.deviceId);
        ActuatorModelID actuatorModelID = new ActuatorModelID(actuatorDataModel.modelID);
        return factoryActuator.createActuator(actuatorID, deviceId, actuatorModelID);
    }

    /**
     * Converts a list of ActuatorDataModel to a list of actuator objects.
     * @param factoryActuator The factory to create the actuator object.
     * @param listDataModel   The list of ActuatorDataModel to be converted.
     * @return The list of actuator objects.
     */
    public static List<Actuator> toDomain(FactoryActuator factoryActuator, Iterable<ActuatorDataModel> listDataModel) {
        List<Actuator> listDomain = new ArrayList<>();

        listDataModel.forEach(actuatorDataModel ->
        {
            Actuator actuatorDomain = toDomain(factoryActuator, actuatorDataModel);

            listDomain.add(actuatorDomain);
        });

        return listDomain;
    }
    /**
     * Updates the current Actuator object with information from another Actuator object.
     * @param actuator The House object containing the updated information.
     * @return True if the update was successful, otherwise false.
     */

    public boolean updateFromDomain(Actuator actuator)
    {
        this.actuatorID = actuator.identity().toString();
        this.deviceId = actuator.getDeviceID().toString();
        this.modelID = actuator.getActuatorModelID().toString();


        return true;
    }
}
