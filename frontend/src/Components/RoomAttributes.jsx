import React, { useEffect, useState } from 'react';
import { getRoomById } from '../services/RoomService';
import '../styles/RoomAttributes.css';

/**
 * `RoomAttributes` is a React component that fetches and displays the attributes of a room.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.roomId - The ID of the room whose attributes are to be displayed.
 * @param {Function} props.onRoomDataLoaded - The function to be called when the room data is loaded.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <RoomAttributes roomId={roomId} onRoomDataLoaded={handleRoomDataLoaded} />
 *
 * @returns {React.Element} Returns a React element that displays the attributes of a room. The attributes include the room name, floor number, length, width, and height. If the room data is loading, it displays a loading message. If there is an error in fetching the room data, it displays an error message.
 */

// RoomAttributes component
const RoomAttributes = ({ roomId, onRoomDataLoaded }) => {
    // State variables
    const [attributes, setAttributes] = useState({});
    // State variable to store the attributes of the room
    const [loading, setLoading] = useState(true);
    // State variable to store the loading state
    const [error, setError] = useState(null);

    useEffect(() => {
        // Function to fetch the attributes of the room
        const fetchRoomAttributes = async () => {
            try {
                setLoading(true);
                const response = await getRoomById(roomId);
                setAttributes(response.data);
                onRoomDataLoaded(response.data.roomName); // Notify parent component with roomName
                setLoading(false);
            } catch (error) {
                console.error('Failed to fetch room attributes:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchRoomAttributes();
    }, [roomId, onRoomDataLoaded]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    // Function to capitalize the first letter of a string
    const capitalizeFirstLetter = (string) => {
        return string.charAt(0).toUpperCase() + string.slice(1);
    };

    return (
        <div className="attribute-card">
            <ul>
                <li>Room : {capitalizeFirstLetter(attributes.roomName?.toString()) || 'N/A'}</li>
                <li>Floor : {attributes.floorNumber?.toString() || 'N/A'}</li>
                <li>Length: {(attributes.length?.toString()) + " m"|| 'N/A'}</li>
                <li>Width: {attributes.width?.toString() + " m"|| 'N/A'}</li>
                <li>Height: {attributes.height?.toString() + " m"|| 'N/A'}</li>
                <li>Location: Inside </li>
                {/*<li>Location: {attributes.isInside?.toString() || 'N/A'}</li>*/}
            </ul>
        </div>
    );
};

export default RoomAttributes;
