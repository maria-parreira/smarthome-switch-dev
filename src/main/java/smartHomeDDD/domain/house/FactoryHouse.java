package smartHomeDDD.domain.house;

import smartHomeDDD.domain.valueobject.*;

/**
 * A factory interface for creating instances of {@link House}.
 */
public interface FactoryHouse {
    /**
     * Creates a new instance of {@link House} with the specified house ID and location.
     *
     * @param houseId  The unique identifier for the house.
     * @param location The location of the house.
     * @return A newly created instance of {@link House}.
     */
    House createHouse(HouseId houseId, Location location);

}