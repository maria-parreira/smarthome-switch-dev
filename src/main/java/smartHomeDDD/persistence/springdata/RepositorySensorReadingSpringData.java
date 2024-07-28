package smartHomeDDD.persistence.springdata;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import smartHomeDDD.domain.repository.IRepositorySensorReading;
import smartHomeDDD.domain.sensorReading.FactorySensorReading;
import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SensorID;
import smartHomeDDD.domain.valueobject.SensorReadingID;
import smartHomeDDD.persistence.jpa.datamodel.SensorReadingDataModel;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of the IRepositorySensorReading interface using Spring Data JPA.
 */
@Repository
public class RepositorySensorReadingSpringData implements IRepositorySensorReading {

    /**
     * Instance of the Spring Data repository for sensor readings.
     */
    final IRepositorySensorReadingSpringData _repositorySensorReadingSpringData;

    /**
     * Factory for creating sensor readings.
     */
    final FactorySensorReading _factorySensorReading;

    /**
     * Constructs a RepositorySensorReadingSpringData object.
     *
     * @param factorySensorReading          The factory for creating sensor readings.
     * @param repositorySensorReadingSpringData The Spring Data repository for sensor readings.
     */
    public RepositorySensorReadingSpringData(FactorySensorReading factorySensorReading, IRepositorySensorReadingSpringData repositorySensorReadingSpringData){
        this._factorySensorReading = factorySensorReading;
        this._repositorySensorReadingSpringData = repositorySensorReadingSpringData;
    }

    /**
     * Saves the given sensor reading into the data store.
     *
     * @param sensorReading The sensor reading to be saved. Must not be null.
     * @return The saved sensor reading.
     * @throws IllegalArgumentException if the provided sensor reading is null.
     */
    public SensorReading save(SensorReading sensorReading) {
        if (sensorReading == null) {
            throw new IllegalArgumentException("SensorReading cannot be null");
        }
        if( containsOfIdentity(sensorReading.identity()) ){
            throw new DataIntegrityViolationException("SensorReading already exists");
        }
        SensorReadingDataModel sensorReadingDataModel = new SensorReadingDataModel(sensorReading);
        SensorReadingDataModel sensorReadingDataModelSaved = _repositorySensorReadingSpringData.save(sensorReadingDataModel);

        return SensorReadingDataModel.toDomain(_factorySensorReading, sensorReadingDataModelSaved);
    }


    /**
     * Retrieves all sensor readings from the data store.
     *
     * @return An iterable collection of sensor readings.
     */

    public Iterable<SensorReading> findAll() {
        List<SensorReadingDataModel> listSensorReadingDataModelSaved = this._repositorySensorReadingSpringData.findAll();

        return SensorReadingDataModel.toDomainList(_factorySensorReading, listSensorReadingDataModelSaved);
    }

    /**
     * Retrieves a sensor reading from the data store based on its identity.
     *
     * @param id The identity of the sensor reading to retrieve.
     * @return An optional containing the sensor reading if found, or empty if not found.
     */
    public Optional<SensorReading> ofIdentity(SensorReadingID id) {

        Optional<SensorReadingDataModel> sensorReadingDataModelSaved = this._repositorySensorReadingSpringData.findById(id.toString());

        if (sensorReadingDataModelSaved.isPresent()) {
            SensorReading SensorReadingDomain = SensorReadingDataModel.toDomain(_factorySensorReading, sensorReadingDataModelSaved.get());
            return Optional.of(SensorReadingDomain);

        } else
            return Optional.empty();
    }


    /**
     * Checks if a sensor reading with the given identity exists in the data store.
     *
     * @param id The identity of the sensor reading to check for existence.
     * @return true if a sensor reading with the given identity exists, false otherwise.
     */
    public boolean containsOfIdentity(SensorReadingID id) {
        return _repositorySensorReadingSpringData.existsById(id.toString());
    }

    /**
     * Retrieves sensor readings from the data store for a specific device within a given time period.
     *
     * @param deviceID The ID of the device for which sensor readings are to be retrieved.
     * @param start    The start timestamp of the period.
     * @param end      The end timestamp of the period.
     * @return A list of sensor readings from the specified device within the given time period.
     */
    public List<SensorReading> getMeasurementsFromDeviceWithinPeriod(DeviceId deviceID, Timestamp start, Timestamp end) {
        List<SensorReadingDataModel> sensorReadingDataModels = this._repositorySensorReadingSpringData.findByDeviceIDAndTimeStampBetween(deviceID.toString(),start,end);
        return SensorReadingDataModel.toDomainList(_factorySensorReading, sensorReadingDataModels);

    }

    /**
     * Retrieves sensor readings from the data store for a specific device within a given time period.
     *
     * @param deviceID The ID of the device for which sensor readings are to be retrieved.
     * @param sensorIDIndoors The ID of the sensor for which sensor readings are to be retrieved.
     * @param start The start timestamp of the period.
     * @param end The end timestamp of the period.
     * @return A list of sensor readings from the specified device within the given time period.
     */
    @Override
    public List<SensorReading> getSensorReadingsBetweenTimestamp(DeviceId deviceID, SensorID sensorIDIndoors, Timestamp start, Timestamp end) {
        List<SensorReadingDataModel> sensorReadingDataModels = this._repositorySensorReadingSpringData.findByDeviceIDAndSensorIDAndTimeStampBetween(deviceID.toString(), sensorIDIndoors.toString(), start, end);
        List<SensorReading> sensorReadings = SensorReadingDataModel.toDomainList(_factorySensorReading, sensorReadingDataModels);
        List<SensorReading> readingsBetweenTimeStamp = new ArrayList<>();
        readingsBetweenTimeStamp.addAll(sensorReadings);
        return readingsBetweenTimeStamp;
    }

    /**
     * Retrieves the latest sensor reading for a specific sensor.
     *
     * @param sensorID The ID of the sensor for which the latest reading is to be retrieved.
     * @return The latest sensor reading for the specified sensor.
     */
    @Override
    public Optional<SensorReading> getLatestReadingFromSensor(SensorID sensorID) {
        SensorReadingDataModel sensorReadingDataModel = this._repositorySensorReadingSpringData.findTopBySensorIDOrderByTimeStampDesc(sensorID.toString());
        SensorReading latestReading = SensorReadingDataModel.toDomain(_factorySensorReading, sensorReadingDataModel);
        return Optional.of(latestReading);
    }

    @Override
    public List<SensorReading> getSensorReadingsByDeviceId(DeviceId deviceId) {
        List<SensorReadingDataModel> sensorReadingDataModels = this._repositorySensorReadingSpringData.findByDeviceID(deviceId.toString());
        return SensorReadingDataModel.toDomainList(_factorySensorReading, sensorReadingDataModels);
    }

}
