package smartHomeDDD.persistence.jpa.datamodel;

import smartHomeDDD.domain.actuatorModel.ActuatorModel;
import smartHomeDDD.domain.actuatorModel.FactoryActuatorModel;
import smartHomeDDD.domain.valueobject.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data model for the actuatorModel entity.
 * It is used to map the actuatorModel domain object to a format that can be stored in the database.
 */
@Entity
@Table(name = "ACTUATOR_MODEL")
public class ActuatorModelDataModel {

    /**
     * The ID of the actuator model.
     */
    @Id
    private String actuatorModelID;
    /**
     * The ID of the related ActuatorType.
     */
    private String actuatorTypeID;

    /**
     * Default constructor with no parameters.
     */
    public ActuatorModelDataModel() {
    }

    /**
     * Constructs a new ActuatorModelDataModel object with the specified actuatorModel.
     * @param actuatorModel The actuatorModel object to be converted to a data model.
     */
    public ActuatorModelDataModel(ActuatorModel actuatorModel) {
        actuatorModelID = actuatorModel.identity().toString();
        actuatorTypeID = actuatorModel.getActuatorTypeID().toString();
    }

    /**
     * Converts a ActuatorModelDataModel object to a actuatorModel domain object.
     * @param factory The factory to create actuatorModel objects
     * @param actuatorModelDataModel  The ActuatorModelDataModel object
     * @return The actuatorModel domain object
     */
    public static ActuatorModel toDomain(FactoryActuatorModel factory, ActuatorModelDataModel actuatorModelDataModel) {

        ActuatorModelID actuatorModelID = new ActuatorModelID(actuatorModelDataModel.actuatorModelID);
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID(actuatorModelDataModel.actuatorTypeID);

        return factory.createActuatorModel(actuatorModelID, actuatorTypeID);
    }

    /**
     * Converts a list of ActuatorModelDataModel objects to a list of actuatorModel domain objects.
     * @param factory The factory to create actuatorModel objects
     * @param listDataModel The list of ActuatorModelDataModel objects
     * @return The list of actuatorModel domain objects
     */
    public static List<ActuatorModel> toDomain(FactoryActuatorModel factory, Iterable<ActuatorModelDataModel> listDataModel) {
        List<ActuatorModel> listDomain = new ArrayList<>();

        listDataModel.forEach(actuatorModelDataModel -> {
            ActuatorModel actuatorModelDomain = toDomain(factory, actuatorModelDataModel);
            listDomain.add(actuatorModelDomain);
        });

        return listDomain;
    }

}
