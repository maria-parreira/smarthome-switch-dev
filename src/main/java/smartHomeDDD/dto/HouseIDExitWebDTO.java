package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;


/**
 * This class represents a Data Transfer Object (DTO) for the House entity.
 * It extends the RepresentationModel class from Spring HATEOAS to include links to other resources.
 * The class contains fields for the house's id and location.
 */
public class HouseIDExitWebDTO extends RepresentationModel<HouseIDExitWebDTO> {
    /**
     * The unique identifier for a house.
     */
    private final String _id;

    /**
     * Constructs a new HouseWebDTO object with the specified ID.
     * @param id The ID of the house.
     */
    public HouseIDExitWebDTO(String id){
        this._id=id;
    }

    /**
     * Retrieves the ID of the house.
     * @return The ID of the house.
     */
    public String getId(){
        return _id;
    }

}