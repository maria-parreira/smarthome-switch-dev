import axiosInstance from '../axiosInstance.jsx';

/**
 * This function retrieves an actuator model by the corresponding actuator type ID.
 * @param {number} id - The ID of the actuator type.
 * @returns {Promise<AxiosResponse<any>>} A promise that resolves to an AxiosResponse object. The `data` property of this object will contain a list of actuator models.
 */
const getActuatorModelsByType = (id) => {
    return axiosInstance.get(`/actuator-models`, {
        params: {
            actuatorTypeID: id
        }
    });
};

export {
    getActuatorModelsByType,
};