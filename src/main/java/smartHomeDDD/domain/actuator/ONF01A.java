package smartHomeDDD.domain.actuator;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.ddd.Value;
import smartHomeDDD.domain.valueobject.ActuatorID;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.DeviceId;
import smartHomeDDD.domain.valueobject.ONF01AValue;

/**
 * The ONF01A class represents the ONF01A actuator, which switches a load ON/OFF.
 * It implements the actuator interface and the AggregateRoot interface with ActuatorID as the identity type.
 */
public class ONF01A implements Actuator, AggregateRoot<ActuatorID> {
    /**
     * The value of the actuator. This is a Value Object that represents a measurement of the actuator.
     */
    private ONF01AValue _actuatorValue;
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
     */
    public ONF01A(ActuatorID actuatorID, DeviceId deviceID, ActuatorModelID actuatorModelID)
    {
        this._actuatorID = actuatorID;
        this._deviceID = deviceID;
        this._actuatorModelID = actuatorModelID;

    }


    /**
     * Returns the ID of the actuator ONF01A object.
     *
     * @return the ID of the actuator ONF01A object.
     */
    @Override
    public ActuatorID identity() {
        return _actuatorID;
    }

    /**
     * Sets the current value of the actuator.
     *
     * @return The value of the actuator.
     */
    @Override
    public Value setValue(Value value) {
        if (value instanceof smartHomeDDD.domain.valueobject.ONF01AValue) {
            this._actuatorValue = (ONF01AValue) value;
            return this._actuatorValue;
        }
        else
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
        if (!(object instanceof ONF01A ONF01A)) {
            return false;
        }
        return this._actuatorID.equals(ONF01A._actuatorID) &&
                this._deviceID.equals(ONF01A._deviceID) &&
                this._actuatorModelID.equals(ONF01A._actuatorModelID) &&
                ((this._actuatorValue == null && ONF01A._actuatorValue == null) ||
                        (this._actuatorValue != null && this._actuatorValue.equals(ONF01A._actuatorValue)));
    }
    /**
     * Checks if the given object is equal to this actuator.
     * @param object object to be compared.
     * @return true if the given object is a ONFO1A actuator and has the same ActuatorID, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof ONF01A actuator1) {

            return this._actuatorID.equals(actuator1._actuatorID);
        }
        return false;
    }

    /**
     * Returns the device ID associated with the ONF01A object.
     */
    @Override
    public DeviceId getDeviceID() {
        return _deviceID;
    }

    /**
     * Returns the actuatorModel ID associated with the ONF01A object.
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

    public smartHomeDDD.domain.valueobject.ONF01AValue getONF01AValue() {
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


