package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.ValueObject;

/**
 * The DeviceModel class represents the model of a device in the smartHomeDDD domain.
 * This class is a value object in the domain-driven design context.
 * A DeviceModel instance is immutable once created and ensures that the device model is not null or empty.
 * This class implements the ValueObject interface, which means it has a method to check the equality with other objects.
 * The class also overrides the toString method to return the device model as a string.
 */
public class DeviceModel implements ValueObject {

    /**
     * The model of the device.
     */
    private final String _model;

    /**
     * Constructs a new DeviceModel object.
     * @param model the model of the device, it cannot be null or empty.
     * @throws IllegalArgumentException if the provided model is null or empty.
     */

    public DeviceModel(String model) {
        if (model == null || model.trim().isEmpty())
            throw new IllegalArgumentException("Device Model cannot be null or empty.");
        else
            this._model = model;
    }

    /**
     * Compares this DeviceModel object to another object.
     * @param object the object to compare this DeviceModel to.
     * @return true if the objects are equal, false otherwise.
     */

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof DeviceModel deviceModel) {

            return this._model.equals(deviceModel._model);
        }
        return false;
    }

    /**
     * Returns the string representation of this DeviceModel object.
     * @return the string representation of this DeviceModel object.
     */
    @Override
    public String toString() {
        return _model;
    }

    /**
     * Returns the hash code of this DeviceModel object.
     * @return the hash code of this DeviceModel object.
     */
    @Override
    public int hashCode() {
        return _model.hashCode();
    }
}
