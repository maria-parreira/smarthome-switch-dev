import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Button.css';

/**
 * `BackButton` is a React component that renders a button which navigates to a specified path when clicked.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.path - The path to navigate to when the button is clicked.
 * @param {string} props.label - The label to display on the button.
 * @param {string} props.className - The CSS class to apply to the button.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <BackButton path="/home" label="Go Back" className="back-button" />
 *
 * @returns {React.Element} Returns a React element that renders a button. When this button is clicked, it navigates to the `path` passed as a prop.
 */

// BackButton component
const BackButton = ({ path, label, className }) => {
    const navigate = useNavigate();

    return (
        <button className={className} onClick={() => navigate(path)}>
            {label}
        </button>
    );
};

export default BackButton;