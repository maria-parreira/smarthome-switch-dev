import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {getSunTimes} from '../../services/SunService';
import {getCurrentTemperatureExtra} from '../../services/WeatherService';
import {getCurrentTemperature, getSunrise, getSunset} from "../../services/TempWeatherService.jsx";
import '../../styles/Home.css';
import '@fortawesome/fontawesome-free/css/all.min.css'; // Import Font Awesome

/**
 * Home is a functional component that renders the home page.
 * It uses the useState and useEffect hooks to manage the state of the sun times, temperature, current time, and current year.
 * It also uses the useNavigate hook from react-router-dom to navigate to the rooms page.
 *
 * @component
 * @example
 * return (
 *   <Home />
 * )
 */
const Home = () => {
    /**
     * @type {Object} sunTimes - This state variable holds the sun times.
     * @type {function} setSunTimes - This function is used to update the state variable sunTimes.
     */
    const [sunTimes, setSunTimes] = useState({});
    /**
     * @type {string} sunrise - This state variable holds the sunrise time.
     * @type {function} setSunrise - This function is used to update the state variable sunrise.
     */
    const [sunrise, setSunrise] = useState(null);
    /**
     * @type {string} sunset - This state variable holds the sunset time.
     * @type {function} setSunset - This function is used to update the state variable sunset.
     */
    const [sunset, setSunset] = useState(null);
    /**
     * @type {number} temperature - This state variable holds the current temperature.
     * @type {function} setTemperature - This function is used to update the state variable temperature.
     */
    const [temperature, setTemperature] = useState(null);
    /**
     * @type {Date} currentTime - This state variable holds the current time.
     * @type {function} setCurrentTime - This function is used to update the state variable currentTime.
     */
    const [currentTime, setCurrentTime] = useState(new Date());
    /**
     * @type {number} currentYear - This variable holds the current year.
     */
    const currentYear = new Date().getFullYear();
    /**
     * @type {function} navigate - This function is used to navigate to different routes.
     */
    const navigate = useNavigate();

    useEffect(() => {
        /**
         * fetchSunTimes is an asynchronous function that fetches the sunrise and sunset times.
         * It first tries to get the sunrise and sunset times using the getSunrise and getSunset functions.
         * If it fails, it tries to get the sun times using the getSunTimes function.
         * It then sets the sunrise and sunset state variables with the fetched times.
         * If it fails again, it logs the error to the console.
         */
        const fetchSunTimes = async () => {
            const lat = 41.1496; // Latitude for Porto
            const lng = -8.6110; // Longitude for Porto
            try {
                const sunrise = await getSunrise(lat, lng);
                setSunrise(sunrise);
                const sunset = await getSunset(lat, lng);
                setSunset(sunset);
            } catch (error) {
                try {
                    const data = await getSunTimes(lat, lng);
                    setSunrise(new Date(data.sunrise).toLocaleTimeString([], {
                            hour: '2-digit',
                            minute: '2-digit'}));
                    setSunset(new Date(data.sunset).toLocaleTimeString([], {
                        hour: '2-digit',
                        minute: '2-digit'}));
                } catch (error) {
                    console.error('Error fetching sun times:', error);
                }
            }
        };
        fetchSunTimes();
    }, []);

    useEffect(() => {
        /**
         * fetchTemperature is an asynchronous function that fetches the current temperature.
         * It first tries to get the temperature using the getCurrentTemperature function.
         * If it fails, it tries to get the temperature using the getCurrentTemperatureExtra function.
         * It then sets the temperature state variable with the fetched temperature.
         * If it fails again, it logs the error to the console.
         */
        const fetchTemperature = async () => {
            const lat = 41.1496; // Latitude for Porto
            const lon = -8.6110; // Longitude for Porto
            try {
                const temp = await getCurrentTemperature(lat, lon);
                setTemperature(temp);
            } catch (error) {
                try {
                    const temp = await getCurrentTemperatureExtra(lat, lon);
                    setTemperature(temp);
                } catch (error) {
                    console.error('Error fetching temperature:', error);
                }
            }
        };

        fetchTemperature(); // Initial fetch

        /**
         * intervalId is a variable that holds the ID of the interval set by setInterval.
         * The interval fetches the current temperature every 15 minutes (900000 milliseconds).
         */
        const intervalId = setInterval(fetchTemperature, 15 * 60 * 1000); // Update every 15 minutes
        /**
         * The return function is a cleanup function that clears the interval when the component unmounts.
         */
        return () => clearInterval(intervalId); // Cleanup interval on component unmount
    }, []);

    useEffect(() => {
        /**
         * intervalId is a variable that holds the ID of the interval set by setInterval.
         * The interval updates the current time every second (1000 milliseconds).
         */
        const intervalId = setInterval(() => {
            setCurrentTime(new Date());
        }, 1000); // 1000 milliseconds = 1 second

        /**
         * The return function is a cleanup function that clears the interval when the component unmounts.
         */
        return () => clearInterval(intervalId); // Cleanup interval on component unmount
    }, []);


    /**
     * This function is used to navigate to the rooms page.
     */
    const navigateToRoomMenu = () => {
        navigate('/rooms');
    };

    /**
     * The component returns a div element that contains a home card with the current time, date, temperature, and sun times,
     * a button that navigates to the rooms page, and a footer with the current year.
     * If the temperature or sun times are not yet loaded, it displays a loading message.
     */
    return (
        <div className="container">
            <div className="home-card">
                <div className="header">
                    <h1>Smart Home</h1>
                    <p className="time">{currentTime.toLocaleTimeString()}</p>
                </div>
                <div className="content">
                    <p className="date">{currentTime.toLocaleDateString(undefined, {
                        month: 'long',
                        day: 'numeric',
                        year: 'numeric'
                    })}</p>
                    {temperature !== null ? (
                        <p className="temperature">{temperature}°C</p>
                    ) : (
                        <p className="loading">Loading temperature...</p>
                    )}
                    <button className="home-button" onClick={navigateToRoomMenu}>
                        <i className="fas fa-home"></i>
                    </button>
                </div>
                {sunrise && sunset ? (
                    <div className="sun-times">
                        <p className="sunrise">Sunrise: {sunrise}</p>
                        <p className="sunset">Sunset: {sunset}</p>
                    </div>
                ) : (
                    <p className="loading">Loading sun times...</p>
                )}
            </div>
            <footer className="footer">
                © {currentYear} SmartHome Grupo 2. All rights reserved.
            </footer>
        </div>
    );
};

export default Home;
