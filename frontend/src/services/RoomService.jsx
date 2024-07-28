import axiosInstance from '../axiosInstance.jsx';

/**
 * Get a room by its ID
 * @param id the ID of the room
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} a room object
 */
const getRoomById = (id) => {
    return axiosInstance.get(`/rooms/${id}`);
};

/**
 * Retrieves all devices from a room
 * @param roomID the ID of the room
 * @returns {Promise<axiosInstance.AxiosResponse<any>>} a promise that contains the response
 */
const getDevicesByRoom = (roomID) => {
    return axiosInstance.get(`rooms/${roomID}/devices`, {
        params: {
            RoomID: roomID
        }
    });
};

/**
 * Gets the name of a room by its ID
 * @param roomID - id of the room to retrieve the name
 * @returns {Promise<string>} - the name of the room
 */
const getRoomNameById = async (roomID) => {
    try {
        const response = await getRoomById(roomID);
        return response.data.roomName; // Assuming roomName is available in response.data
    } catch (error) {
        console.error('Failed to fetch room name:', error);
        throw error; // Handle error appropriately in your component
    }
};

export {
    getRoomById,
    getDevicesByRoom,
    getRoomNameById,
};