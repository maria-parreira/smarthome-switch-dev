import React from 'react';
import '../styles/Button.css';

/**
 * `EditDetailsButton` is a React component that renders an edit button.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Function} props.onEdit - The function to be called when the button is clicked.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <EditDetailsButton onEdit={handleEdit} />
 *
 * @returns {React.Element} Returns a React element that renders an edit button. When the button is clicked, it calls the `onEdit` function passed as a prop.
 */

// EditDetailsButton component
const EditDetailsButton = ({ onEdit }) => {
    return (
        <button className="edit-button" onClick={onEdit}>
            Edit
        </button>
    );
};


export default EditDetailsButton;