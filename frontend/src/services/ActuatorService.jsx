import axiosInstance from '../axiosInstance.jsx';

/**
 * Retrieves an actuator by its ID
 * @param actuatorID actuator ID
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} actuator
 */
const getActuatorById = (actuatorID) => {
    return axiosInstance.get(`/actuators/${actuatorID}`);
};

/**
 * Creates and adds an actuator to a device
 * @param actuatorEntryWebDTO actuator entry DTO
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} actuator
 */
const addActuatorToDevice = (actuatorEntryWebDTO) => {
    return axiosInstance.post('/actuators', actuatorEntryWebDTO);
};

/**
 * Updates the value of a roller blind actuator
 * @param actuatorID The ID of the actuator to update
 * @param entryDTO The data to update the actuator with
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} The updated actuator
 */
const updateRollerBlindValue = (actuatorID, entryDTO) => {
    return axiosInstance.patch(`/actuators/${actuatorID}`, entryDTO);
};

export {
    getActuatorById,
    addActuatorToDevice,
    updateRollerBlindValue
};
