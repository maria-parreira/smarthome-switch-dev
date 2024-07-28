package smartHomeDDD.persistence.jpa.datamodel;

import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * SensorReadingDataModel is a data model class for SensorReading objects.
 * It is used to map SensorReading objects to a relational database table.
 */
@Entity
@Table(name = "SENSOR_READING")
public class SensorReadingDataModel {

    /**
     * The ID of the sensor reading.
     */
    @Id
    private String sensorReadingID;
    /**
     * The reading value of the sensor reading.
     */
    private String reading;
    /**
     * The ID of the device associated with the sensor reading.
     */
    private String deviceID;
    /**
     * The ID of the sensor associated with the sensor reading.
     */
    private String sensorID;
    /**
     * The timestamp of the sensor reading.
     */
    private Timestamp timeStamp;

    /**
     * Default constructor for SensorReadingDataModel with no parameters.
     */
    public SensorReadingDataModel() {
    }

    /**
     * Constructor for SensorReadingDataModel that takes a SensorReading object as a parameter.
     * @param sensorReading A SensorReading object.
     */
    public SensorReadingDataModel(SensorReading sensorReading) {
        this.sensorReadingID = sensorReading.identity().toString();
        this.reading = sensorReading.getReading().toString();
        this.deviceID = sensorReading.getDeviceID().toString();
        this.sensorID = sensorReading.getSensorID().toString();
        this.timeStamp = sensorReading.getTimeStamp();
    }

    /**
     * Converts a SensorReadingDataModel object to a SensorReading object.
     * @param factory Factory for creating SensorReading
     * @param sensorReadingDataModel SensorReading data model object to convert
     * @return A SensorReading object
     */
    static public SensorReading toDomain(FactorySensorReading factory, SensorReadingDataModel sensorReadingDataModel) {
        SensorReadingID sensorReadingID = new SensorReadingID(sensorReadingDataModel.sensorReadingID);
        Reading reading = new Reading(sensorReadingDataModel.reading);
        DeviceId deviceID = new DeviceId(sensorReadingDataModel.deviceID);
        SensorID sensorID = new SensorID(sensorReadingDataModel.sensorID);
        Timestamp timeStamp = sensorReadingDataModel.timeStamp;

        return factory.createSensorReading(sensorReadingID, reading, deviceID, sensorID, timeStamp);
    }

    /**
     * Converts a list of SensorReadingDataModel objects to a list of SensorReading objects.
     * @param factory  Factory for creating SensorReading objects
     * @param listDataModel List of SensorReadingDataModel objects to convert
     * @return List of SensorReading objects
     */
    static public List<SensorReading> toDomainList(FactorySensorReading factory, List<SensorReadingDataModel> listDataModel) {
        List<SensorReading> listDomain = new ArrayList<>();

        for (SensorReadingDataModel sensorReadingDataModel : listDataModel) {
            SensorReading sensorReadingDomain = toDomain(factory, sensorReadingDataModel);
            listDomain.add(sensorReadingDomain);
        }
        return listDomain;
    }

}