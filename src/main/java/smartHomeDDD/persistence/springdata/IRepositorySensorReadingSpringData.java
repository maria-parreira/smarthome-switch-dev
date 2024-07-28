package smartHomeDDD.persistence.springdata;

import smartHomeDDD.persistence.jpa.datamodel.SensorReadingDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * This interface defines methods for accessing sensor reading data using Spring Data JPA.
 */
public interface IRepositorySensorReadingSpringData extends JpaRepository<SensorReadingDataModel, String> {

    /**
     * Retrieves a list of sensor reading data models based on the device ID
     *
     * @param deviceId The ID of the device for which sensor readings are to be retrieved.
     * @param start The start of the time period.
     * @param end The end of the time period.
     * @return A list of sensor reading data models associated with the specified device ID.
     */
    List<SensorReadingDataModel> findByDeviceIDAndTimeStampBetween(String deviceId,Timestamp start, Timestamp end);

    /**
     * Retrieves a list of sensor reading data models based on the device ID and sensor ID.
     *
     * @param deviceId The ID of the device for which sensor readings are to be retrieved.
     * @param sensorID The ID of the sensor for which sensor readings are to be retrieved.
     * @param start The start of the time period.
     * @param end The end of the time period.
     * @return A list of sensor reading data models associated with the specified device ID and sensor ID.
     */
    List<SensorReadingDataModel> findByDeviceIDAndSensorIDAndTimeStampBetween(String deviceId, String sensorID, Timestamp start, Timestamp end);

    /**
     * Retrieves the latest sensor reading data model based on the sensor ID
     *
     * @param sensorID The ID of the sensor for which sensor reading is to be retrieved.
     * @return The latest sensor reading data model associated with the specified sensor ID.
     */
    SensorReadingDataModel findTopBySensorIDOrderByTimeStampDesc(String sensorID);

    List<SensorReadingDataModel> findByDeviceID(String deviceId);

}
