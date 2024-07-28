package smartHomeDDD.domain.actuator;


import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.SIV280Value;

/**
 * The SIV280 class represents the SIV280 actuator.
 * It implements the actuator interface and the AggregateRoot interface with ActuatorID as the identity type.
 */

public class SIV280 implements Actuator, AggregateRoot<ActuatorID> {

    /**
     * The Value of the actuator.
     */
    private SIV280Value _actuatorValue;
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
     * Constructs a new SIV280 with the specified actuator value, actuator ID, device ID, actuator model ID and actuator type ID.
     * If any of the arguments are null, an IllegalArgumentException is thrown.
     *
     * @param actuatorID the unique identifier of the actuator
     * @param deviceID the unique identifier of the device which the actuator belongs to
     * @param actuatorModelID the model of the actuator
     * @throws IllegalArgumentException if any of the arguments are null
     */

    public SIV280(ActuatorID actuatorID, DeviceId deviceID, ActuatorModelID actuatorModelID)
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
     * Checks if the provided object is the same as this SIV280.
     * If the object is an instance of SIV280, it checks if the actuator ID, actuator value, device ID and actuator model ID are the same.
     *
     * @param object the object to be checked
     * @return true if the object is the same as this SIV280, false otherwise
     */

    @Override
    public boolean sameAs(Object object)
    {
        if (!(object instanceof SIV280 siv280)) {
            return false;
        }
        if (this._actuatorValue == null) {
            return this._actuatorID.equals(siv280._actuatorID) && this._deviceID == siv280._deviceID &&
                    this._actuatorModelID.equals(siv280._actuatorModelID);
        }
        return false;
    }

    /**
     * Checks if the provided object is equal to this SIV280.
     * If the object is an instance of SIV280, it checks if the actuator ID is the same.
     *
     * @param object the object to be checked
     * @return true if the object is equal to this SIV280, false otherwise
     */

    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object instanceof SIV280 siv280) {

            return this._actuatorID.equals(siv280._actuatorID);
        }
        return false;
    }

    /**
     * Sets the value of the actuator.
     * @return the new Value if it is a SIV280Value, null otherwise.
     */

    @Override
    public Value setValue(Value value) {
        if (value instanceof SIV280Value v) {
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
