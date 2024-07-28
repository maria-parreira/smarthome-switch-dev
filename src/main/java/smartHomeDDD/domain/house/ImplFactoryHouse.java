package smartHomeDDD.domain.house;
import org.springframework.stereotype.Component;
import smartHomeDDD.domain.valueobject.*;

/**
 * Implementation of the FactoryHouse interface for creating house instances.
 * This implementation simply creates a new instance of House using the provided house ID and location.
 */
@Component
public class ImplFactoryHouse implements FactoryHouse{

    /**
     * Creates a new instance of House with the specified house ID and location.
     * @param houseId   The unique identifier of the house to be created.
     * @param location  The location of the house to be created.
     * @return  A new instance of House with the specified house ID and location.
     */
    public House createHouse(HouseId houseId, Location location){
        return new House(houseId, location);
    }


}
