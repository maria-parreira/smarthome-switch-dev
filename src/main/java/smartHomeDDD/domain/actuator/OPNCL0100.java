package smartHomeDDD.domain.actuator;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.OPNCL0100Value;

/**
 * This class represents an actuator that open/closes a blind roller (0% closed, 100% fully open).
 * It implements the actuator and AggregateRoot Interfaces.
 */
public class OPNCL0100 implements Actuator, AggregateRoot<ActuatorID> {
    /**
     * The Value of the actuator. This is a Value Object.
     */
    private OPNCL0100Value _actuatorValue;
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
     * @param actuatorID      The unique identifier of the actuator.
     * @param deviceID        The unique identifier of the device which the actuator belongs to.
     * @param actuatorModelID The model of the actuator.
     *                        //@param actuatorModelName The model name of the actuator.
     *                        //@param typeDescription   The description of the type of the actuator.
     */
    public OPNCL0100(ActuatorID actuatorID, DeviceId deviceID, ActuatorModelID actuatorModelID
    ) {
        this._actuatorID = actuatorID;
        this._deviceID = deviceID;
        this._actuatorModelID = actuatorModelID;
    }

    /**
     * Returns the ID of the actuator OPNCL0100 object.
     *
     * @return the ID of the actuator OPNCL0100 object.
     */
    @Override
    public ActuatorID identity() {
        return _actuatorID;
    }

    /**
     * Sets the value of this actuator if the provided value is an instance of OPNCL0100Value.
     * If the provided value is not an instance of OPNCL0100Value, it does not change the current value and returns null.
     *
     * @param value The Value to set.
     * @return The updated value if it was set, or null otherwise.
     */
    @Override
    public Value setValue(Value value) {
        if (value instanceof smartHomeDDD.domain.valueobject.OPNCL0100Value) {
            this._actuatorValue = (OPNCL0100Value) value;
            return this._actuatorValue;
        } else
            return null;

    }

    /**
     * Verifies if two actuator objects are the same.
     *
     * @param object The object to compare.
     * @return True if the actuator objects are the same, false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (!(object instanceof OPNCL0100 opncl0100)) {
            return false;
        }
        if (this._actuatorValue == null) {
            return this._actuatorID.equals(opncl0100._actuatorID) && this._deviceID == opncl0100._deviceID &&
                    this._actuatorModelID.equals(opncl0100._actuatorModelID);
        }
        return false;
    }





    /**
     * Checks if the given object is equal to this actuator.
     * @param object object to be compared.
     * @return true if the given object is a OPNCL0100 actuator and has the same ActuatorID, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof OPNCL0100 actuator1) {

            return this._actuatorID.equals(actuator1._actuatorID);
        }
        return false;
    }

    /**
     * Returns the device ID associated with the OPNCL0100 object.
     */
    @Override
    public DeviceId getDeviceID() {
        return _deviceID;
    }

    /**
     * Returns the actuatorModel ID associated with the OPNCL0100 object.
     */
    @Override
    public ActuatorModelID getActuatorModelID() {
        return _actuatorModelID;
    }

    /**
     * Returns the Value (Value Object) associated with the OPNCL0100 object.
     * @return the Value (Value Object) associated with the OPNCL0100 object.
     */
    @Override
    public OPNCL0100Value getValue()
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

