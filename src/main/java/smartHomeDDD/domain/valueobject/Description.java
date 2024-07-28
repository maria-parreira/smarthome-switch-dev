package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * The Description class represents a description in the Smart Home domain.
 * It implements the ValueObject interface.
 */
public class Description implements ValueObject {

    /**
     * The description value.
     */
    private final String _description;

    /**
     * Constructs a new Description object with the specified description value.
     * Throws an IllegalArgumentException if the description value is null or empty.
     *
     * @param description The description value to set.
     * @throws IllegalArgumentException If the description value is null or empty.
     */
    public Description(String description)  {
        if (isDescriptionValid(description))
            _description = description;
        else
            throw new IllegalArgumentException("Description cannot be null or empty");
    }

    /**
     * Checks if the provided description value is valid.
     *
     * @param description The description value to validate.
     * @return true if the description value is not null, not empty, and not blank; false otherwise.
     */

    private boolean isDescriptionValid(String description) {
        return description != null && !description.isBlank() && !description.isEmpty();
    }

    /**
     * Checks if this Description object is equal to another object.
     * Two Description objects are considered equal if they have the same description value.
     *
     * @param object The object to compare with this Description.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof Description description1) {

            return _description.equals(description1._description);
        }
        return false;
    }

    /**
     * Returns a string representation of the Description object.
     *
     * @return The description value as a string.
     */
    @Override
    public String toString() {
        return _description;
    }

    /**
     * Returns the hash code value for this Description object.
     *
     * @return The hash code value for this Description object.
     */
    @Override
    public int hashCode() {
        return _description.hashCode();
    }
}
