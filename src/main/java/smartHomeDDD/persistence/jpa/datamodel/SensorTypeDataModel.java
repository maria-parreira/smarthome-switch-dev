package smartHomeDDD.persistence.jpa.datamodel;


import smartHomeDDD.domain.sensorType.FactorySensorType;
import smartHomeDDD.domain.sensorType.SensorType;
import smartHomeDDD.domain.valueobject.Description;
import smartHomeDDD.domain.valueobject.SensorTypeID;
import smartHomeDDD.domain.valueobject.Unit;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data model for the sensorType entity in the database.
 * It provides methods to convert between the sensorType domain object and the SensorTypeDataModel.
 * */
@Entity
@Table(name = "SENSOR_TYPE")
public class SensorTypeDataModel {

    /**
     * The primary key of the sensorType entity.
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String sensorTypeID;

    /**
     * The description of the sensorType entity.
     */
    private String sensorTypeDescription;

    /**
     * The measurement unit of the sensorType entity.
     */
    private String sensorTypeMeasurementUnit;


    /**
     * Default constructor with no parameters.
     */
    public SensorTypeDataModel() {}

    /**
     * Constructor to create a SensorTypeDataModel from a sensorType domain object.
     * @param sensorType The sensorType domain object
     */
    public SensorTypeDataModel(SensorType sensorType)
    {
        sensorTypeID = sensorType.identity().toString();
        sensorTypeDescription = sensorType.getDescription().toString();
        sensorTypeMeasurementUnit = sensorType.getUnit().toString();
    }

    /**
     * Converts a SensorTypeDataModel object to a sensorType domain object.
     * @param factory The factory to create sensorType objects
     * @param sensorTypeDataModel The SensorTypeDataModel object to convert
     * @return The corresponding sensorType domain object
     */
    static public SensorType toDomain(FactorySensorType factory, SensorTypeDataModel sensorTypeDataModel)
    {
        SensorTypeID sensorTypeID = new SensorTypeID(sensorTypeDataModel.sensorTypeID);
        Description sensorTypeDescription = new Description(sensorTypeDataModel.sensorTypeDescription);
        Unit sensorTypeMeasurementUnit = new Unit(sensorTypeDataModel.sensorTypeMeasurementUnit);

        return factory.createSensorType(sensorTypeID, sensorTypeDescription, sensorTypeMeasurementUnit);
    }

    /**
     * Converts a list of SensorTypeDataModel objects to a list of sensorType domain objects.
     * @param factory The factory to create sensorType objects
     * @param listDataModel The list of SensorTypeDataModel objects to convert
     * @return The corresponding list of sensorType domain objects
     */
    static public Iterable<SensorType> toDomain(FactorySensorType factory, Iterable<SensorTypeDataModel> listDataModel)
    {
        List<SensorType> listDomain = new ArrayList<>();

        listDataModel.forEach(sensorTypeDataModel -> {
            SensorType sensorTypeDomain = toDomain(factory, sensorTypeDataModel);
            listDomain.add(sensorTypeDomain);
        });

        return listDomain;
    }

}
