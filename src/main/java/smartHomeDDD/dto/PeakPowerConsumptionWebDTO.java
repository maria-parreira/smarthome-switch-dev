package smartHomeDDD.dto;
import org.springframework.hateoas.RepresentationModel;


/**
 * Represents a Data Transfer Object (DTO) for peak power consumption in a web context.
 */
public class PeakPowerConsumptionWebDTO extends RepresentationModel<PeakPowerConsumptionWebDTO>{

    /**
     * Peak power consumption value
     */
    private String peakPowerConsumption;

    /**
     * Constructs a new PeakPowerConsumptionWebDTO with specified peak power consumption value.
     *
     * @param peak Peak power consumption value.
     */
    public PeakPowerConsumptionWebDTO(String peak) {
        this.peakPowerConsumption = peak;
    }


    /**
     * Gets the peak power consumption value.
     * @return The peak power consumption value.
     */
    public String getPeakPowerConsumption() {
        return peakPowerConsumption;
    }
}
