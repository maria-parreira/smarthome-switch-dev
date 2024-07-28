import axiosInstance from '../axiosInstance.jsx';

/**
 * Retrieves a sensor by ID
 * @param id The sensor ID
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} - The response
 */
const getSensorById = (id) => {
    return axiosInstance.get(`/sensors/${id}`);
};

/**
 * Adds a new sensor to a device
 * @param sensorEntryWebDTO {Object} - The sensor entry web DTO, containing the sensor's information
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} - The response
 */
const addSensorToDevice = (sensorEntryWebDTO) => {
    return axiosInstance.post('/sensors', sensorEntryWebDTO);
};

export {
    getSensorById,
    addSensorToDevice,
};