import React, { useState } from 'react';
import ReactiveButton from 'reactive-button';
import '../styles/ReactiveButton.css'; // Import the ReactiveButton.css file

/**
 * `ReactiveButton` is a React component that renders a button with different states.
 *
 * @component
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <ReactiveButton />
 *
 * @returns {React.Element} Returns a React element that renders a button with different states. The button starts in the 'idle' state with the text 'Save'. When clicked, it goes into the 'loading' state with the text 'Loading', and after 2 seconds, it goes into the 'success' state with the text 'Done'.
 */

// ReactiveButton component
function App() {
    // State variable to store the state of the button
    const [state, setState] = useState('idle');

    // Function to handle the button click
    const onClickHandler = () => {
        setState('loading');

        // send an HTTP request
        setTimeout(() => {
            setState('success');
        }, 2000);
    };

    return (
        <ReactiveButton

            style={{
                borderRadius: '20px',
                color: 'black',
            }}

            buttonState={state}
            idleText="Save"
            loadingText="Loading"
            successText="Done"
            onClick={onClickHandler}
            className="reactive-button" // Add the class name from the ReactiveButton.css file
        />
    );
}

export default App;