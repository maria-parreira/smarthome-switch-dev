package smartHomeDDD.persistence.jpa.datamodel;

import smartHomeDDD.domain.sensor.FactorySensor;
import smartHomeDDD.domain.sensor.Sensor;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorModelID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data model for the Sensor entity in the database.
 * It provides methods to convert between the Sensor domain object and the SensorDataModel.
 */
@Entity
@Table(name = "SENSOR")
public class SensorDataModel {

    /**
     * The ID of the sensor.
     */
    @Id
    private String sensorId;
    /**
     * The ID of the related Sensor Model.
     */
    private String sensorModelId;
    /**
     * The ID of the related Device.
     */
    private String deviceId;

    /**
     * Default constructor with no parameters.
     */
    public SensorDataModel() {
    }

    /**
     * Constructs a new SensorDataModel object with the specified Sensor.
     * @param sensor The Sensor object to be converted to a data model.
     */
    public SensorDataModel(Sensor sensor) {
        sensorId = sensor.identity().toString();
        sensorModelId = sensor.getSensorModelID().toString();
        deviceId = sensor.getDeviceID().toString();
    }

    /**
     * Converts a SensorDataModel to a Sensor object.
     * @param factory The factory to create the Sensor object.
     * @param sensorDataModel The SensorDataModel to be converted.
     * @return The Sensor object.
     */
    public static Sensor toDomain(FactorySensor factory, SensorDataModel sensorDataModel) {
        SensorID sensorId = new SensorID(sensorDataModel.sensorId);
        SensorModelID sensorModelId = new SensorModelID(sensorDataModel.sensorModelId);
        DeviceId deviceId = new DeviceId(sensorDataModel.deviceId);
        return factory.createSensor(deviceId, sensorModelId, sensorId);
    }

    /**
     * Converts a list of SensorDataModel to a list of Sensor objects.
     * @param factory The factory to create the Sensor object.
     * @param listDataModel The list of SensorDataModel to be converted.
     * @return The list of Sensor objects.
     */
    public static List<Sensor> toDomain(FactorySensor factory, Iterable<SensorDataModel> listDataModel) {
        List<Sensor> listDomain = new ArrayList<>();

        listDataModel.forEach(sensorDataModel -> {
            Sensor sensorDomain = toDomain(factory, sensorDataModel);
            listDomain.add(sensorDomain);
        });

        return listDomain;
    }
}
