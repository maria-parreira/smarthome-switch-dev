package smartHomeDDD.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class to generate random IDs using UUID.
 */
@Service
public class GenerateRandomId {

    /**
     * Default constructor for GenerateRandomId.
     */
    public GenerateRandomId(){
    }

    /**
     * Generates a random ID using UUID.
     *
     * @return A randomly generated ID as a String.
     */
    public String  generateID(){
        UUID id1= UUID.randomUUID();
        return "" +id1;
    }
}
