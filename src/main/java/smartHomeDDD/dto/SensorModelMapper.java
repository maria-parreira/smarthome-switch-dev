package smartHomeDDD.dto;

import smartHomeDDD.domain.sensorModel.SensorModel;
import smartHomeDDD.domain.valueobject.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting between sensorModel and SensorModelDTO objects.
 */
public class SensorModelMapper {

    /**
     * Converts a sensor model ID string to a SensorModelID object.
     *
     * @param sensorModelId The sensor model ID string.
     * @return The SensorModelID object.
     */
    public static SensorModelID DTOToSensorModelId(String sensorModelId) {
        return new SensorModelID(sensorModelId);
    }

    /**
     * Converts a sensorModel object to a SensorModelDTO object.
     *
     * @param sensorModel The sensorModel object to convert.
     * @return The converted SensorModelDTO object.
     */
    public static SensorModelDTO sensorModelToDTO(SensorModel sensorModel) {
        if (sensorModel == null)
            return null;
        String sensorModelID = sensorModel.identity().toString();
        String sensorTypeID = sensorModel.getSensorTypeID().toString();
        return new SensorModelDTO(sensorModelID, sensorTypeID);
    }

    /**
     * Converts a list of sensorModel objects to a list of SensorModelDTO objects.
     *
     * @param sensorModels The list of sensorModel objects to convert.
     * @return The list of converted SensorModelDTO objects.
     */
    public static List<SensorModelDTO> sensorModelsListToDTO(List<SensorModel> sensorModels) {
        List<SensorModelDTO> sensorModelsDTO = new ArrayList<>();
        for (SensorModel sensorModel : sensorModels) {
            sensorModelsDTO.add(sensorModelToDTO(sensorModel));
        }
        return sensorModelsDTO;
    }

    public static SensorModelExitWebDTO domainToExitWebDTO(SensorModel sensorModel) {
        if (sensorModel == null)
            return null;
        String sensorModelID = sensorModel.identity().toString();
        return new SensorModelExitWebDTO(sensorModelID);
    }

}
