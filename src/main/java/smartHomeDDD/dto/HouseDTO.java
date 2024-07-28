package smartHomeDDD.dto;

/**
 * Data transfer object (DTO) representing information about a house.
 */
public class HouseDTO {

    /**
     * The unique identifier for a house.
     */
    private final String _id;

    /**
     * Constructs a new HouseDTO object with the specified ID.
     * @param id The ID of the house.
     */
    public HouseDTO(String id){
        this._id=id;
    }

    /**
     * Retrieves the ID of the house.
     * @return The ID of the house.
     */
    public String getId(){
        return this._id;
    }


}
