import React from 'react';
import '../styles/BlindRollerControl.css';

/**
 * `BlindRollerControl` is a React component that renders a control for a blind roller.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {number} props.percentage - The current percentage of the blind roller.
 * @param {Function} props.increase - The function to be called when the increase button is clicked.
 * @param {Function} props.decrease - The function to be called when the decrease button is clicked.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <BlindRollerControl percentage={50} increase={handleIncrease} decrease={handleDecrease} />
 *
 * @returns {React.Element} Returns a React element that renders a control for a blind roller. The control includes a display of the current percentage and buttons to increase and decrease the percentage. When the increase button is clicked, it calls the `increase` function passed as a prop. When the decrease button is clicked, it calls the `decrease` function passed as a prop.
 */

// BlindRollerControl component
const BlindRollerControl = ({ percentage, increase, decrease }) => {
    return (
        <div className="blind-roller-control">
            <div className="roller-square">{percentage}%</div> {/* Display the percentage */}
            <div className="roller-buttons">
                <button onClick={decrease}>-</button>
                <button onClick={increase}>+</button>
            </div>
        </div>
    );
};

export default BlindRollerControl;
