package smartHomeDDD.domain.actuatorModel;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.ActuatorModelID;
import smartHomeDDD.domain.valueobject.ActuatorTypeID;

/**
 * This class represents the actuatorModel, comprised of its ID and the ActuatorTypeID.
 * It implements the Aggregate root interface.
 */
public class ActuatorModel implements AggregateRoot<ActuatorModelID> {

    /**
     * The name of the associated actuator model, derived from an existing list of models.
     */
    private final ActuatorModelID _actuatorModelID;
    /**
     * The unique identifier of the associated actuator model.
     */
    private final ActuatorTypeID _actuatorTypeID;

    /**
     * Constructs an actuatorModel object with the specified ActuatorModelID and ActuatorTypeID.
     * Throws an IllegalArgumentException if any of the specified parameters are null.
     *
     * @param actuatorModelID The unique identifier for the actuator model.
     */
    public ActuatorModel(ActuatorModelID actuatorModelID, ActuatorTypeID actuatorTypeID) {
        this._actuatorModelID = actuatorModelID;
        this._actuatorTypeID = actuatorTypeID;
    }

    /**
     * Returns the Identifier of the ActuatorModelID
     */
    @Override
    public ActuatorModelID identity() {
        return this._actuatorModelID;
    }

    /**
     * Tests if this ActuatorType is equal to another object by having the same Name and ID.
     *
     * @param object The object to be compared with this ActuatorType.
     * @return returns true if the objects are equal, false otherwise.
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof ActuatorModel actuatorModel) {
            return this._actuatorModelID.equals(actuatorModel._actuatorModelID) &&
                    this._actuatorTypeID.equals(actuatorModel._actuatorTypeID);
        }
        return false;
    }

    /**
     * Checks if the given object is equal to this actuatorModel.
     *
     * @param object object to be compared.
     * @return true if the given object is a actuatorModel and has the same ActuatorModelID, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object instanceof ActuatorModel actuatorModel1) {

            return this._actuatorModelID.equals(actuatorModel1._actuatorModelID);
        }
        return false;
    }

    /**
     * Returns the ActuatorTypeID.
     *
     * @return the ActuatorTypeID.
     */
    public ActuatorTypeID getActuatorTypeID() {
        return _actuatorTypeID;
    }

    /**
     * Returns the hashCode of the actuatorModel ID.
     *
     * @return the hashCode of the actuatorModel ID.
     */
    @Override
    public int hashCode() {
        return _actuatorModelID.hashCode();
    }
}
