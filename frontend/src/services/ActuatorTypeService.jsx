import axiosInstance from '../axiosInstance.jsx';

/**
 * Retrieves all existing actuator types from the database
 * @returns {Promise<axiosInstance.AxiosResponse<any>>}
 */
const getActuatorTypes = () => {
    return axiosInstance.get('/actuator-types');
};

/**
 * Retrieves an actuator type by its id
 * @param actuatorTypeID - id of the actuator type to retrieve
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} - the actuator type with the given id
 */
const getActuatorTypeById = (actuatorTypeID) => {
    return axiosInstance.get(`/actuator-types/${actuatorTypeID}`);
};

export {
    getActuatorTypes,
    getActuatorTypeById,
};