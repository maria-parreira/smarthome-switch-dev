import React from 'react';
import '../styles/AttributeCard.css';

/**
 * `AttributeCard` is a React component that renders a card with a list of attributes.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Array} props.attributes - An array of attribute objects. Each object should have an `id`, `name`, and `value` property.
 *
 * @example
 * // To use this component, import it and use it in the JSX of the parent component like this:
 * <AttributeCard attributes={[{ id: 1, name: 'Temperature', value: '25°C' }, { id: 2, name: 'Humidity', value: '60%' }]} />
 *
 * @returns {React.Element} Returns a React element that renders a card with a list of attributes. Each attribute is displayed with its name and value.
 */

// Functional component AttributeCard responsible for rendering the attribute card.
const AttributeCard = ({ attributes}) => {
    return (
        <div className="attribute-card">
            <div className="attributes">
                {attributes.map(attr => (
                    <div key={attr.id} className="attribute">
                        <span>{attr.name}: </span>
                        <span>{attr.value}</span>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default AttributeCard;
