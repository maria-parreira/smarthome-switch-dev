package smartHomeDDD.domain.house;

import smartHomeDDD.ddd.AggregateRoot;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.Location;

/**
 * The {@code House} class represents a residential property. It implements the {@link AggregateRoot} interface
 * with a generic type of {@link HouseId}.
 * Each house is uniquely identified by a {@link HouseId} and is associated with a {@link Location}.
 * The class provides methods to retrieve the house ID and to compare houses for equality based on their ID and location.
 */
public class House implements AggregateRoot<HouseId> {

    /** The unique identifier for the house. */
    private final HouseId _houseId;

    /** The location of the house. */
    private Location _houseLocation;

    /**
     * Constructs a new House with the specified house ID and location.
     * @param houseId       the unique identifier of the house
     * @param houseLocation the location of the house
     */
    public House(HouseId houseId, Location houseLocation)
    {
        this._houseId = houseId;
        this._houseLocation = houseLocation;
    }

    /**
     * Returns the unique identifier of the house.
     *
     * @return the house ID
     */
    @Override
    public HouseId identity() {

        return this._houseId;
    }

    /**
     * Compares this house to the specified object for equality.
     * Houses are considered equal if they have the same ID and location.
     * @param object the object to compare to
     * @return {@code true} if the houses are equal, {@code false} otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof House house) {

            return this._houseLocation.equals(house._houseLocation) && equals(object);
        }
        return false;
    }

    /**
     * This method compares the houseId of the house with the houseId of the object passed as a parameter.
     * Since the houseId is unique, it is sufficient to compare the houseId to determine if two houses are the same.
     * @param object The object to compare.
     * @return True if the houses are the same, false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof House house) {

            return this._houseId.equals(house._houseId);
        }
        return false;
    }

    /**
     * Sets the location of the house.
     * @param location The location of the house to be configured.
     * @throws IllegalArgumentException if location is null.
     */
    public void configureLocation(Location location){
        this._houseLocation = location;
    }

    /**
     * Retrieves the location of the house.
     * @return The location of the house.
     */
    public Location getHouseLocation(){
        return this._houseLocation;
    }

    /**
     * Returns the hash code of the house ID.
     *
     * @return The hash code of the house ID.
     */
    @Override
    public int hashCode() {
        return _houseId.hashCode();
    }
}
