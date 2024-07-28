package smartHomeDDD.domain.valueobject;
import smartHomeDDD.ddd.ValueObject;

/**
 * Represents an address value object.
 */
public class Address implements ValueObject {

    /**
     * The address value.
     */
    private final String _address;

    /**
     * Constructs an Address object with the given string representation of the address.
     * @param stringAddress A string representing the address.
     */
    public Address(String stringAddress){
        if (stringAddress == null) {
            throw new IllegalArgumentException("Address can't be null");
        }
        if (stringAddress.isEmpty() || stringAddress.isBlank()) {
            throw new IllegalArgumentException("Address can't be empty");
        }
        this._address=stringAddress;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param object The reference object with which to compare.
     * @return True if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof Address address1) {

            return this._address.equals(address1._address);
        }
        return false;
    }

    /**
     * Returns a string representation of the Address object.
     * @return The address value as a string.
     */
    @Override
    public String toString() {
        return _address;
    }

    /**
     * Returns the hash code value for this Address object.
     * @return The hash code value for this Address object.
     */
    @Override
    public int hashCode() {
        return _address.hashCode();
    }
}
