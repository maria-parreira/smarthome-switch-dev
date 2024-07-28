import React, { useState } from 'react';
import BackButton from '../../Components/BackButton.jsx';
import '../../styles/Button.css';
import '../../styles/IntroMenuRoom.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import RoomsList from "../../Components/RoomsList.jsx";
import WelcomeTitle from "../../Components/WelcomeTitle.jsx";

/**
 * RoomMenu is a functional component that renders the room menu page.
 * It uses the useState hook to manage the state of the rooms and the current year.
 * @component
 * @example
 * return (
 *   <RoomMenu />
 * )
 */

const RoomMenu = () => {
    /**
     * @type {Array} rooms - This state variable holds the list of rooms.
     * @type {function} setRooms - This function is used to update the state variable rooms.
     */
    const [rooms, setRooms] = useState([]);
    /**
     * @type {number} currentYear - This variable holds the current year.
     */
    const currentYear = new Date().getFullYear();

    /**
     * The component returns a div element that contains a BackButton component, a WelcomeTitle component, a RoomsList component, and a footer.
     * The RoomsList component is passed the rooms state variable as a prop.
     * The footer displays a copyright notice with the current year.
     */
    return (
        <div className="rooms-container">
            <BackButton path="/" label="Back to Home" className="back-button"/>
            <WelcomeTitle />
            <div className="rooms-list-container">
                <div className="buttons-container">
                    <RoomsList rooms={rooms} />
                </div>
                <footer className="footer">
                    © {currentYear} SmartHome Grupo 2. All rights reserved.
                </footer>
            </div>
        </div>
    );
};

export default RoomMenu;
