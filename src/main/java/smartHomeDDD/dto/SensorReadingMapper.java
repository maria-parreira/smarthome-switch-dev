package smartHomeDDD.dto;

import smartHomeDDD.domain.sensorReading.SensorReading;
import smartHomeDDD.domain.valueobject.Reading;
import smartHomeDDD.domain.valueobject.SensorReadingID;

import java.util.ArrayList;
import java.util.List;

/**
 * SensorReadingMapper is responsible for mapping data between SensorReading objects and their corresponding data representations,
 * such as DTOs (Data Transfer Objects) or database entities.
 */
public class SensorReadingMapper
{

    /**
     * Given an object sensorReading, it transforms it into a DTO object.
     *
     * @param sensorReading the sensorReading object provided by controller object.
     * @return The SensorReadingDTO object.
     */
    public static SensorReadingDTO sensorReadingToDTO(SensorReading sensorReading)
    {
        String sensorReadingID = String.valueOf(sensorReading.identity());
        String value = sensorReading.getReading().toString();
        String deviceID = String.valueOf(sensorReading.getDeviceID());
        String sensorID = String.valueOf(sensorReading.getSensorID());
        String timeStamp = String.valueOf(sensorReading.getTimeStamp());
        return new SensorReadingDTO(sensorReadingID, value, deviceID, sensorID, timeStamp);
    }

    /**
     * Given a list comprised of SensorReading objects, it transforms it into a list of SensorReadingDTO objects.
     *
     * @param sensorReadingList List of SensorReading Objects.
     * @return the list of SensorReadingDTO objects.
     */
    public static List<SensorReadingDTO> domainListToDTO(List<SensorReading> sensorReadingList)
    {
        List<SensorReadingDTO> sensorReadingDTOList = new ArrayList<>();
        for (SensorReading sensorReading : sensorReadingList) {
            sensorReadingDTOList.add(sensorReadingToDTO(sensorReading));
        }
        return sensorReadingDTOList;
    }


    /**
     * Given a SensorReading object, it transforms it into a SensorReadingIDExitWebDTO object.
     * @param sensorReading The SensorReading object to convert.
     * @return The SensorReadingIDExitWebDTO object containing the SensorReadingID.
     */
    public static SensorReadingIDExitWebDTO sensorReadingIDToExitWebDTO(SensorReading sensorReading) {
        String sensorReadingID =sensorReading.identity().toString();
        return new SensorReadingIDExitWebDTO(sensorReadingID);
    }


    /**
     * Converts a string representation of a SensorReadingID into a SensorReadingID object.
     * @param sensorReading The string representation of the SensorReadingID.
     * @return The SensorReadingID object.
     */
    public static SensorReadingID DTOToSensorReadingID(String sensorReading){
        return new SensorReadingID(sensorReading);
    }

    /**
     * Converts a string representation of a Reading into a Reading object.
     * @param reading The string representation of the Reading.
     * @return The Reading object.
     */
    public static Reading DTOToReading(String reading){
        return new Reading(reading);
    }


    /**
     * Converts a sensorReading object into a SensorReadingExitWebDTO object.
     * @param sensorReading The sensorReading object to convert.
     * @return The SensorReadingExitWebDTO object.
     */
    public static SensorReadingExitWebDTO sensorReadingToExitWebDTO(SensorReading sensorReading) {
        String sensorReadingID = sensorReading.identity().toString();
        String reading = sensorReading.getReading().toString();
        String deviceID = sensorReading.getDeviceID().toString();
        String sensorID = sensorReading.getSensorID().toString();
        String timeStamp = sensorReading.getTimeStamp().toString();
        return new SensorReadingExitWebDTO(sensorReadingID, reading, deviceID, sensorID, timeStamp);
    }
}