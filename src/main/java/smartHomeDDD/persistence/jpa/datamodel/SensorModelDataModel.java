package smartHomeDDD.persistence.jpa.datamodel;

import smartHomeDDD.domain.sensorModel.FactorySensorModel;
import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data model for the sensorModel entity in the database.
 * It provides methods to convert between the sensorModel domain object and the SensorModelDataModel.
 */
@Entity
@Table(name = "SENSOR_MODEL")
public class SensorModelDataModel {

    /**
     * The ID of the sensor model.
     */
    @Id
    private String sensorModelID;
    /**
     * The ID of the related sensorType.
     */
    private String sensorTypeID;

    /**
     * Default constructor with no parameters.
     */
    public SensorModelDataModel() {
    }

    /**
     * Constructs a new SensorModelDataModel object with the specified sensorModel.
     * @param sensorModel The sensorModel object to be converted to a data model.
     */
    public SensorModelDataModel(SensorModel sensorModel) {
        sensorModelID = sensorModel.identity().toString();
        sensorTypeID = sensorModel.getSensorTypeID().toString();
    }

    /**
     * Converts a SensorModelDataModel to a sensorModel object.
     * @param factory The factory to create the sensorModel object.
     * @param sensorTypeDataModel The SensorModelDataModel to be converted.
     * @return The sensorModel object.
     */
    public static SensorModel toDomain(FactorySensorModel factory, SensorModelDataModel sensorTypeDataModel) {
        SensorModelID sensorModelID = new SensorModelID(sensorTypeDataModel.sensorModelID);
        SensorTypeID sensorTypeID = new SensorTypeID(sensorTypeDataModel.sensorTypeID);

        return factory.createSensorModel(sensorModelID, sensorTypeID);
    }

    /**
     * Converts a list of SensorModelDataModel to a list of sensorModel objects.
     * @param factory The factory to create the sensorModel object.
     * @param listDataModel The list of SensorModelDataModel to be converted.
     * @return The list of sensorModel objects.
     */
    public static List<SensorModel> toDomain(FactorySensorModel factory, Iterable<SensorModelDataModel> listDataModel) {
        List<SensorModel> listDomain = new ArrayList<>();
        listDataModel.forEach(sensorModelDataModel -> {
            SensorModel sensorModelDomain = toDomain(factory, sensorModelDataModel);
            listDomain.add(sensorModelDomain);
        });

        return listDomain;
    }
}
