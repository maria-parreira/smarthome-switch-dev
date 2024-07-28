package smartHomeDDD.domain.actuator;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SPV300Value;

/**
 * The SPV300 class represents the SPV300 actuator.
 * This actuator sets a decimal value in the range defined by [lower limit, upper limit] and predefined precision.
 * It implements the actuator interface and the AggregateRoot interface with ActuatorID as the identity type.
 */

public class SPV300 implements Actuator, AggregateRoot<ActuatorID> {

    /**
     * The Value of the actuator. This is a Value Object.
     */
    private SPV300Value _actuatorValue;
    /**
     * The unique identifier of the actuator.
     */
    private final ActuatorID _actuatorID;
    /**
     * The unique identifier of the device which the actuator belongs to.
     */
    private final DeviceId _deviceID;
    /**
     * The model of the actuator.
     */
    private final ActuatorModelID _actuatorModelID;

    /**
     * Constructs a new SPV300 with the specified actuator value, actuator ID, device ID, actuator model ID and actuator type ID.
     * If any of the arguments are null, an IllegalArgumentException is thrown.
     *
     * @param actuatorID the unique identifier of the actuator
     * @param deviceID the unique identifier of the device which the actuator belongs to
     * @param actuatorModelID the model of the actuator
     * @throws IllegalArgumentException if any of the arguments are null
     */

    public SPV300(ActuatorID actuatorID, DeviceId deviceID, ActuatorModelID actuatorModelID)
    {
        this._actuatorID = actuatorID;
        this._deviceID = deviceID;
        this._actuatorModelID = actuatorModelID;
    }


    /**
     * Returns the identity of the actuator.
     */
    @Override
    public ActuatorID identity() {
        return _actuatorID;
    }

    /**
     * Checks if the given object is the same as this actuator.
     * @return true if the given object is a SPV300 and has the same values for all properties except the actuatorId, false otherwise.
     */
    @Override
    public boolean sameAs(Object object)
    {
        if (!(object instanceof SPV300 spv300)) {
            return false;
        }
        if (this._actuatorValue == null) {
            return this._actuatorID.equals(spv300._actuatorID) && this._deviceID == spv300._deviceID &&
                    this._actuatorModelID.equals(spv300._actuatorModelID);
        }
        return false;
    }

    /**
     * Checks if the given object is equal to this actuator.
     * @return true if the given object is a SPV300 and has the same ActuatorID, false otherwise.
     */

    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object instanceof SPV300 spv300) {

            return this._actuatorID.equals(spv300._actuatorID);
        }
        return false;
    }

    /**
     * Sets the value of the actuator.
     * @return the new Value if it is a SPV300Value, null otherwise.
     */
    @Override
    public Value setValue(Value value) {
        if (value instanceof SPV300Value v) {
            this._actuatorValue = v;
            return this._actuatorValue;
        }
        else {
            return null;
        }
    }

    /**
     * Returns the DeviceId of the device which the actuator belongs to.
     */
    @Override
    public DeviceId getDeviceID() {
        return _deviceID;
    }

    /**
     * Returns the model of the actuator.
     */
    @Override
    public ActuatorModelID getActuatorModelID() {
        return _actuatorModelID;
    }

    @Override
    public Value getValue()
    {
        return _actuatorValue;
    }

    /**
     * Returns the hash code of the actuator ID.
     *
     * @return The hash code of the actuator ID.
     */
    @Override
    public int hashCode() {
        return _actuatorID.hashCode();
    }
}
