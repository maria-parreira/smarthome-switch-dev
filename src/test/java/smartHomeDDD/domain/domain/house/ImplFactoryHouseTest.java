package smartHomeDDD.domain.domain.house;

import smartHomeDDD.domain.house.House;
import smartHomeDDD.domain.house.ImplFactoryHouse;
import smartHomeDDD.domain.valueobject.HouseId;
import smartHomeDDD.domain.valueobject.Location;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ImplFactoryHouse class, focusing on ensuring proper house creation functionality.
 * This test suite verifies that the ImplFactoryHouse class correctly creates instances of House objects
 * and handles invalid input scenarios.
 */
class ImplFactoryHouseTest {

    /**
     * Test to ensure that a new instance of House is created successfully.
     */
    @Test
    void validArguments_shouldInstantiateHouse() {
        // Arrange
        ImplFactoryHouse implFactoryHouse = new ImplFactoryHouse();
        HouseId houseIdDouble = mock(HouseId.class);
        Location locationDouble = mock(Location.class);

        // Act
        try (MockedConstruction<House> houseDouble = Mockito.mockConstruction(House.class,
                (mock, context) -> {
                    HouseId houseId = (HouseId) context.arguments().get(0);
                    Location location = (Location) context.arguments().get(1);
                    when(mock.identity()).thenReturn(houseId);
                    when(mock.getHouseLocation()).thenReturn(location);
                })) {

            House house = implFactoryHouse.createHouse(houseIdDouble, locationDouble);

            List<House> mockedHouses = houseDouble.constructed();
            House mockedHouse = mockedHouses.get(0);

            // Assert
            assertEquals(1, mockedHouses.size());
            assertEquals(houseIdDouble, mockedHouse.identity());
            assertEquals(locationDouble, mockedHouse.getHouseLocation());
            assertEquals(house, mockedHouse);
        }
    }

}
