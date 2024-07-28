import axios from '../axiosInstance.jsx';

/**
 * Fetches the current temperature from the backend API.
 * @param {number} lat - The latitude for which to fetch the current temperature.
 * @param {number} lon - The longitude for which to fetch the current temperature.
 * @returns {Promise<number>} A promise that resolves to the current temperature.
 */
export const getCurrentTemperature= async (lat,lon) => {
    const response = await axios.get('/weather/currentTemperature',{
        params: {
            latitude: lat,
            longitude: lon
        }
    });
    return response.data.measurement;
};

/**
 * Fetches the sunrise time from the backend API.
 * @param {number} lat - The latitude for which to fetch the sunrise time.
 * @param {number} lon - The longitude for which to fetch the sunrise time.
 * @returns {Promise<string>} A promise that resolves to the sunrise time.
 */
export const getSunrise= async (lat,lon) => {
    const response = await axios.get('/weather/sunrise',{
        params: {
            latitude: lat,
            longitude: lon
        }
    });
    return response.data.measurement;
};

/**
 * Fetches the sunset time from the backend API.
 * @param {number} lat - The latitude for which to fetch the sunset time.
 * @param {number} lon - The longitude for which to fetch the sunset time.
 * @returns {Promise<string>} A promise that resolves to the sunset time.
 */
export const getSunset= async (lat,lon) => {
    const response = await axios.get('/weather/sunset',{
        params: {
            latitude: lat,
            longitude: lon
        }
    });
    return response.data.measurement;
};

