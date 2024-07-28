package smartHomeDDD.domain.valueobject;

import smartHomeDDD.ddd.DomainId;

/**
 * Represents a house identifier value object.
 */
public class HouseId implements DomainId {

    /**
     * The house identifier value.
     */
    private final String _id;


    /**
     * Constructs a HouseId object with the given string representation of the identifier.
     * @param id A string representing the house identifier.
     * @throws IllegalArgumentException If the provided stringId is null, empty, or blank.
     */
    public HouseId (String id) {
        if(id == null){
            throw new IllegalArgumentException("HouseId can't be null");
        }
        if(id.isBlank() || id.isEmpty()){
            throw new IllegalArgumentException("HouseId can't be empty");
        }
        this._id=id;
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

        if (object instanceof HouseId houseId) {

            return this._id.equals(houseId._id);
        }
        return false;
    }

    /**
     * Returns the hash code value for this object. The default implementation
     * of this method returns the hash code of the _id object associated with this object.
     * @return the hash code value for this object
     */
    @Override
    public int hashCode(){
        return this._id.hashCode();
    }

    /**
     * Returns a string representation of the HouseId object.
     * @return The house identifier value as a string.
     */
    @Override
    public String toString() {
        return _id;
    }
}
