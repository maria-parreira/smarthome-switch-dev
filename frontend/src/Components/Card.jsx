import React from 'react';
import '../styles/Card.css';

/**
 * `Card` is a React component that renders a card with a title, an image, and any children components.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {string} props.title - The title to display on the card.
 * @param {string} props.image - The URL of the image to display as the background of the card.
 * @param {React.Node} props.children - The children components to render inside the card.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <Card title="Card Title" image="image_url">
 *   <p>This is a child component</p>
 * </Card>
 *
 * @returns {React.Element} Returns a React element that renders a card with a title, an image, and any children components. The title is displayed at the top of the card, the image is displayed as the background of the card, and the children components are rendered inside the card.
 */

// Card component
const Card = ({ title, image, children }) => {
    return (
        <div className="card" style={{ backgroundImage: `url(${image})` }}>
            <h1>{title}</h1>
            <div className="card-content">
                {children}
            </div>
        </div>
    );
};

export default Card;