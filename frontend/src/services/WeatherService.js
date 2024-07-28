import axios from 'axios';

const WEATHER_API_BASE_URL = 'https://api.openweathermap.org/data/2.5/weather';
const API_KEY = '59eb21cf11bb959595ce569a6694c7cd'; // Replace with your OpenWeatherMap API key

export const getCurrentTemperatureExtra = async (lat, lon) => {
    try {
        const response = await axios.get(WEATHER_API_BASE_URL, {
            params: {
                lat: lat,
                lon: lon,
                units: 'metric', // or 'imperial' for Fahrenheit
                appid: API_KEY
            }
        });
        console.log('Temperature response:', response.data);
        return response.data.main.temp;
    } catch (error) {
        console.error('Error fetching temperature:', error);
        throw error;
    }
};
