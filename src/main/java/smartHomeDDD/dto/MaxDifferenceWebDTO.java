package smartHomeDDD.dto;

import org.springframework.hateoas.RepresentationModel;

public class MaxDifferenceWebDTO extends RepresentationModel<MaxDifferenceWebDTO> {

    private int maxDifference;

    public static MaxDifferenceWebDTO domainToDTO(int maxDifference) {
        MaxDifferenceWebDTO maxDifferenceWebDTO = new MaxDifferenceWebDTO();
        maxDifferenceWebDTO.maxDifference = maxDifference;
        return maxDifferenceWebDTO;
    }

    public int getMaxDifference() {
        return maxDifference;
    }

}
