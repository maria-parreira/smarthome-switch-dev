import axiosInstance from '../axiosInstance.jsx';

/**
 * Get sensor models by sensor type ID
 * @param sensorTypeID Sensor type ID
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} List of sensor models
 */
const getSensorModelsByType = (sensorTypeID) => {
    return axiosInstance.get(`/sensor-models`, {
        params: {
            sensorTypeID: sensorTypeID
        }
    });
};

export {
    getSensorModelsByType,
};