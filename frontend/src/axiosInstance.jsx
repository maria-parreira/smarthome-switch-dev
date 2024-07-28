// This file is used to create an axios instance with the base url of our api.
import axios from 'axios';

/**
 * Axios instance with the base url of the SmartHome API.
 * @type {axios.AxiosInstance}
 */
const axiosInstance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1', // Base api url
    timeout: 1000,
    headers: {
        'Content-Type': 'application/json',
    },
});

export default axiosInstance;
