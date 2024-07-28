package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * The DeviceName class represents the name of a device in the smartHomeDDD domain.
 * This class is a value object in the domain-driven design context.
 * A DeviceName instance is immutable once created and ensures that the device name is not null or empty.
 * This class implements the ValueObject interface, which means it has a method to check the equality with other objects.
 * The class also overrides the toString method to return the device name as a string.
 */
public class DeviceName implements ValueObject {
    /**
     * The name of the device.
     */
    private final String _name;

    /**
     * Constructs a new DeviceName object.
     * @param name the name of the device, in String format.
     * @throws IllegalArgumentException if the provided name is null or empty.
     */

    public DeviceName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Device Name cannot be null or empty.");
        else
            this._name = name;
    }

    /**
     * Compares this DeviceName object to another object.
     * @param object the object to compare this DeviceName object to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof DeviceName deviceName) {

            return this._name.equals(deviceName._name);
        }
        return false;
    }

    /**
     * @return the string representation of this DeviceName object.
     */
    @Override
    public String toString() {
        return _name;
    }

    /**
     * @return the hash code of this DeviceName object.
     */
    @Override
    public int hashCode() {
        return _name.hashCode();
    }
}
