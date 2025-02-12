package foot_court.place;

import foot_court.place.domain.api.usecase.PlatesUseCase;
import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.spi.IPlatesPersistencePort;
import foot_court.place.domain.spi.IRestaurantsPersistencePort;
import foot_court.place.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatesUseCaseTest {

    private IPlatesPersistencePort platesPersistencePort;
    private IUserPersistencePort userPersistencePort;
    private IRestaurantsPersistencePort restaurantsPersistencePort;
    private PlatesUseCase platesUseCase;

    @BeforeEach
    void setUp() {
        platesPersistencePort = mock(IPlatesPersistencePort.class);
        userPersistencePort = mock(IUserPersistencePort.class);
        restaurantsPersistencePort = mock(IRestaurantsPersistencePort.class);
        platesUseCase = new PlatesUseCase(platesPersistencePort, userPersistencePort, restaurantsPersistencePort);
    }

    @Test
    void createPlate_ShouldSavePlate_WhenValid() {
        // Given
        Plate plate = new Plate();
        plate.setPrice(10.0);

        // When
        platesUseCase.createPlate(plate);

        // Then
        assertTrue(plate.isActive());
        verify(platesPersistencePort).savePlate(plate);
    }

    @Test
    void createPlate_ShouldThrowException_WhenPriceIsInvalid() {
        // Given
        Plate plate = new Plate();
        plate.setPrice(-0.5); // Precio inválido

        // When
        Executable action = () -> platesUseCase.createPlate(plate);

        // Then
        assertThrows(IllegalArgumentException.class, action);
        verify(platesPersistencePort, never()).savePlate(any());
    }

    @Test
    void updatePlate_ShouldUpdate_WhenPlateExists() {
        // Given
        Plate plate = new Plate();
        plate.setId(1L);
        plate.setPrice(15.0);

        when(platesPersistencePort.findPlateById(1L)).thenReturn(plate);

        // When
        platesUseCase.updatePlate(plate);

        // Then
        verify(platesPersistencePort).updatePlate(plate);
    }

    @Test
    void updatePlate_ShouldThrowException_WhenPlateDoesNotExist() {
        // Given
        Plate plate = new Plate();
        plate.setId(1L);
        plate.setPrice(15.0);

        when(platesPersistencePort.findPlateById(1L)).thenReturn(null);

        // When
        Executable action = () -> platesUseCase.updatePlate(plate);

        // Then
        assertThrows(IllegalArgumentException.class, action);
        verify(platesPersistencePort, never()).updatePlate(any());
    }

    @Test
    void changeAvailability_ShouldChange_WhenOwnerIsValid() {
        // Given
        Long plateId = 1L;
        Long ownerId = 10L;
        Long restaurantId = 100L;

        Plate plate = new Plate();
        plate.setId(plateId);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(ownerId.toString());

        plate.setRestaurantId(restaurant);

        when(platesPersistencePort.findPlateById(plateId)).thenReturn(plate);
        when(restaurantsPersistencePort.findRestaurantById(restaurantId)).thenReturn(restaurant);
        when(userPersistencePort.getUserId()).thenReturn(ownerId);

        // When
        platesUseCase.changeAvailability(plateId);

        // Then
        verify(platesPersistencePort).changeAvailability(plateId);
    }

    @Test
    void changeAvailability_ShouldThrowException_WhenUserIsNotOwner() {
        // Given
        Long plateId = 1L;
        Long ownerId = 10L;
        Long restaurantId = 100L;
        Long otherUserId = 99L;

        Plate plate = new Plate();
        plate.setId(plateId);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(ownerId.toString());

        plate.setRestaurantId(restaurant);

        when(platesPersistencePort.findPlateById(plateId)).thenReturn(plate);
        when(restaurantsPersistencePort.findRestaurantById(restaurantId)).thenReturn(restaurant);
        when(userPersistencePort.getUserId()).thenReturn(otherUserId); // Usuario incorrecto

        // When
        Executable action = () -> platesUseCase.changeAvailability(plateId);

        // Then
        assertThrows(IllegalArgumentException.class, action);
        verify(platesPersistencePort, never()).changeAvailability(any());
    }
}