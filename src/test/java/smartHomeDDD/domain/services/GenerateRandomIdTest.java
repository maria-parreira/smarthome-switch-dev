package smartHomeDDD.domain.services;

import org.junit.jupiter.api.Test;
import smartHomeDDD.services.GenerateRandomId;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Test class for GenerateRandomId.
 * Includes the following test methods:
 * testGenerateID: Test to generate a random ID.
 */
public class GenerateRandomIdTest {


    /**
     * Test to generate a random ID.
     */
    @Test
    void testGenerateID() {
        GenerateRandomId generateRandomId = new GenerateRandomId();
        String randomId = generateRandomId.generateID();
        assertNotNull(randomId);
    }

}
