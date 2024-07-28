import axios from 'axios';

const SUN_API_BASE_URL = 'https://api.sunrise-sunset.org/json';

export const getSunTimes = async (lat, lng) => {
    const response = await axios.get(SUN_API_BASE_URL, {
        params: {
            lat: lat,
            lng: lng,
            formatted: 0
        }
    });
    return response.data.results;
};