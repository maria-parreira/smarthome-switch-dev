import axiosInstance from '../axiosInstance.jsx';

/**
 * This function retrieves all sensor types.
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} A promise that resolves to an AxiosResponse object. The `data` property of this object will contain a list of all sensor types.
 */
const getSensorTypes = () => {
    return axiosInstance.get('/sensor-types');
};

/**
 * This function retrieves a sensor type by its ID.
 * @param {number} sensorTypeID - The ID of the sensor type.
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} A promise that resolves to an AxiosResponse object. The `data` property of this object will contain the sensor type corresponding to the provided ID.
 */
const getSensorTypeByID = (sensorTypeID) => {
    return axiosInstance.get(`/sensor-types/${sensorTypeID}`);
};


export {
    getSensorTypes,
    getSensorTypeByID,
};