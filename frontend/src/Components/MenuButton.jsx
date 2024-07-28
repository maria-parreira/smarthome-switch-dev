import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axiosInstance from "../axiosInstance.jsx";
import '../styles/MenuButton.css';
import { getRoomById } from "../services/RoomService.jsx";

/**
 * `MenuButton` is a React component that fetches and displays a list of rooms in a menu.
 *
 * @component
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <MenuButton />
 *
 * @returns {React.Element} Returns a React element that displays a list of rooms in a menu. Each room is a button that navigates to the devices page of that room when clicked. The button of the current room is highlighted.
 */

// MenuButton component
const MenuButton = () => {
    // State variables
    const [rooms, setRooms] = useState([]);
    // State variable to store the details of each room
    const [roomDetails, setRoomDetails] = useState([]);
    // Custom hooks
    const navigate = useNavigate();
    // Custom hooks
    const location = useLocation();
    // Get the current room ID from the URL
    const currentRoomId = location.pathname.split('/')[2];

    useEffect(() => {
        // Function to fetch the rooms
        const fetchRooms = async () => {
            try {
                // Fetch the rooms
                const response = await axiosInstance.get(`/houses/H1/rooms`);
                console.log('API response:', response.data); // Log the API response
                setRooms(response.data);

                // Fetch the details of each room
                const details = await Promise.all(response.data.map(room => getRoomById(room.roomId)));
                setRoomDetails(details.map(detail => detail.data));
            } catch (error) {
                console.error('Failed to fetch rooms:', error);
            }
        };

        fetchRooms();
    }, []);

    // Function to navigate to the devices page of a room
    const handleNavigation = (roomId) => {
        navigate(`/rooms/${roomId}/devices`);
    };

    return (
        <div className="menu-container">
            <ul className="menu-list">
                {roomDetails.map((room) => (
                    <li
                        key={room.roomId}
                        onClick={() => handleNavigation(room.roomId)}
                        className="menu-item"
                    >
                        <button className={`menu-button ${room.roomId === currentRoomId ? 'active' : ''}`}>
                            {room.roomName || 'No Name'} {/* Display the roomName directly from the room object */}
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default MenuButton;
