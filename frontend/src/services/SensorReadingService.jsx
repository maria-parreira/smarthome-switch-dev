import axiosInstance from '../axiosInstance.jsx';


/**
 * Retrieves a sensor reading by its ID
 * @param sensorReadingID Sensor reading ID
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} the corresponding Sensor reading
 */
const getSensorReadingByID = (sensorReadingID) => {
    return axiosInstance.get(`/sensor-readings/${sensorReadingID}`);
};

export {
    getSensorReadingByID,
};