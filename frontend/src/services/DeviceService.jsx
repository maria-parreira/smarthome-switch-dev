import axiosInstance from '../axiosInstance.jsx';

/**
 * Adds a new device to the database
 * @param deviceEntryWebDTO - the device to add
 * @returns {Promise<axios.AxiosResponse<any>>} - the added device
 */
const addDevice = (deviceEntryWebDTO) => {
    return axiosInstance.post('/devices', deviceEntryWebDTO); // Ensure the path starts with a "/"
};

/**
 * Retrieves a device by its id
 * @param deviceID - id of the device to retrieve
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} - the device with the given id
 */
const getDeviceById = (deviceID) => {
    return axiosInstance.get(`/devices/${deviceID}`);
};

/**
 * Retrieves all sensors associated with a device
 * @param deviceID - id of the device to retrieve sensors from
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} - all sensors associated with the device
 */
const getSensorsByDevice = (deviceID) => {
    return axiosInstance.get(`/devices/${deviceID}/sensors`);
};

/**
 * Retrieves all actuators associated with a device
 * @param deviceID - id of the device to retrieve actuators from
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} - all actuators associated with the device
 */
const getActuatorsByDevice = (deviceID) => {
    return axiosInstance.get(`/devices/${deviceID}/actuators`);
};

/**
 * Retrieves all sensor readings associated with a device
 * @param deviceID - id of the device to retrieve sensor readings from
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} - all sensor readings associated with the device
 */
const getSensorReadingsByDevice = (deviceID) => {
    return axiosInstance.get(`/devices/${deviceID}/sensor-readings`);
};

/**
 * Deactivates a device
 * @param deviceID - id of the device to deactivate
 * @returns {Promise<axios.AxiosResponse<any>>} - the deactivated device
 */
const deactivateDevice = (deviceID) => {
    return axiosInstance.patch(`/devices?getBy=active`, null, {
        params: {
            deviceID: deviceID,
        },
    });
};


/**
 * Gets the name of a device by its id
 * @param deviceId - id of the device to retrieve the name
 * @returns {Promise<string>} - the name of the device
 */
const getDeviceNameById = async (deviceId) => {
    try {
        const response = await getDeviceById(deviceId);
        return response.data.deviceName; // Assuming deviceName is available in response.data
    } catch (error) {
        console.error('Failed to fetch device name:', error);
        throw error; // Handle error appropriately in your component
    }
};


export {
    addDevice,
    getDeviceById,
    getSensorsByDevice,
    getActuatorsByDevice,
    getSensorReadingsByDevice,
    deactivateDevice,
    getDeviceNameById
};