package smartHomeDDD.persistence.jpa.datamodel;

import smartHomeDDD.domain.actuatorType.ActuatorType;
import smartHomeDDD.domain.actuatorType.FactoryActuatorType;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.Unit;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data model for the ActuatorType entity.
 * It is used to map the ActuatorType domain object to a format that can be stored in the database.
 */
@Entity
@Table(name = "ACTUATOR_TYPE")
public class ActuatorTypeDataModel {

    /**
     * The ID of the actuator type.
     */
    @Id
    private String actuatorTypeID;

    /**
     * The description of the actuator type.
     */
    private String actuatorTypeDescription;
    /**
     * The measurement unit of the actuator type.
     */
    private String actuatorTypeMeasurementUnit;

    /**
     * Default constructor with no parameters.
     */
    public ActuatorTypeDataModel() {}

    /**
     * Constructs an ActuatorTypeDataModel from an ActuatorType domain object.
     * @param actuatorType The ActuatorType domain object.
     */
    public ActuatorTypeDataModel(ActuatorType actuatorType) {
        if (actuatorType == null) {
            throw new IllegalArgumentException("ActuatorType cannot be null");
        }
        actuatorTypeID = actuatorType.identity().toString();
        actuatorTypeDescription = actuatorType.getDescription().toString();
        actuatorTypeMeasurementUnit = actuatorType.getUnit().toString();
    }

    /**
     * Converts this data model object to its corresponding domain object.
     * @param factory The factory used to create the ActuatorType domain object.
     * @param actuatorTypeDataModel The data model object to convert.
     * @return The corresponding ActuatorType domain object.
     * @throws IllegalStateException if the ActuatorType domain object creation fails
     */
    public static ActuatorType toDomain(FactoryActuatorType factory, ActuatorTypeDataModel actuatorTypeDataModel) {
        ActuatorTypeID actuatorTypeID = new ActuatorTypeID(actuatorTypeDataModel.actuatorTypeID);
        Description actuatorTypeDescription = new Description(actuatorTypeDataModel.actuatorTypeDescription);
        Unit actuatorTypeMeasurementUnit = new Unit(actuatorTypeDataModel.actuatorTypeMeasurementUnit);

        ActuatorType actuatorTypeDomain = factory.createActuatorType(actuatorTypeMeasurementUnit, actuatorTypeDescription, actuatorTypeID);

        if (actuatorTypeDomain == null) {
            throw new IllegalStateException("Failed to create ActuatorType domain object");
        }
        return actuatorTypeDomain;
    }

    /**
     * Converts a list of data model objects to their corresponding domain objects.
     * @param factory The factory used to create the ActuatorType domain objects.
     * @param listDataModel The list of data model objects to convert.
     * @return A list of corresponding ActuatorType domain objects.
     */
    public static Iterable<ActuatorType> toDomain(FactoryActuatorType factory, Iterable<ActuatorTypeDataModel> listDataModel) {
        List<ActuatorType> listDomain = new ArrayList<>();

        listDataModel.forEach(actuatorTypeDataModel -> {
            ActuatorType actuatorTypeDomain = toDomain(factory, actuatorTypeDataModel);
            listDomain.add(actuatorTypeDomain);
        });

        return listDomain;
    }

}
