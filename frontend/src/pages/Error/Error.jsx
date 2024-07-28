import React from 'react';
import '../../styles/Error.css';

/**
 * ErrorPage is a functional component that renders an error page.
 * It displays a message indicating that something went wrong.
 *
 * @component
 * @example
 * return (
 *   <ErrorPage />
 * )
 */
const ErrorPage = () => {

    /**
     * The component returns a div element with a heading and a paragraph.
     * The heading displays the text 'Error Page' and the paragraph displays the text 'Sorry, something went wrong.'.
     */
    return (
        <div className='errorPage'>
            <h1>Error Page</h1>
            <p>Sorry, something went wrong.</p>
        </div>
    );
};

export default ErrorPage;
