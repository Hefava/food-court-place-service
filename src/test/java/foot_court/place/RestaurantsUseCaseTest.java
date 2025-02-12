package foot_court.place;

import foot_court.place.domain.api.usecase.RestaurantsUseCase;
import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.model.RestaurantsWorkers;
import foot_court.place.domain.spi.IRestaurantsPersistencePort;
import foot_court.place.domain.spi.IUserPersistencePort;
import foot_court.place.domain.utils.pagination.PagedResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;

import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantsUseCaseTest {

    private IRestaurantsPersistencePort restaurantsPersistencePort;
    private IUserPersistencePort userPersistencePort;
    private RestaurantsUseCase restaurantsUseCase;

    @BeforeEach
    void setUp() {
        restaurantsPersistencePort = mock(IRestaurantsPersistencePort.class);
        userPersistencePort = mock(IUserPersistencePort.class);
        restaurantsUseCase = new RestaurantsUseCase(restaurantsPersistencePort, userPersistencePort);
    }

    @Test
    void registerRestaurant_ShouldRegisterValidRestaurant() {
        // Given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Valid Restaurant");
        restaurant.setNit("123456789");
        restaurant.setPhone("1234567890");
        restaurant.setOwnerId("1");

        when(restaurantsPersistencePort.existsByName(restaurant.getName())).thenReturn(false);
        when(userPersistencePort.validateRoleOwner(1L)).thenReturn(true);

        // When
        restaurantsUseCase.registerRestaurant(restaurant);

        // Then
        verify(restaurantsPersistencePort).registerRestaurant(restaurant);
    }

    @Test
    void registerRestaurant_ShouldThrowException_WhenRestaurantAlreadyExists() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Pizza Place");

        when(restaurantsPersistencePort.existsByName(restaurant.getName())).thenReturn(true);

        Executable action = () -> restaurantsUseCase.registerRestaurant(restaurant);

        assertThrows(IllegalArgumentException.class, action);

        verify(restaurantsPersistencePort, never()).registerRestaurant(any(Restaurant.class));
    }

    @Test
    void enterEmployee_ShouldAddEmployee_WhenOwnerIsValid() {
        // Given
        Long ownerId = 1L;
        Long restaurantId = 2L;
        Long employeeId = 3L;

        when(restaurantsPersistencePort.isOwnerOfRestaurant(ownerId, restaurantId)).thenReturn(true);

        // When
        restaurantsUseCase.enterEmployee(ownerId, restaurantId, employeeId);

        // Then
        ArgumentCaptor<RestaurantsWorkers> captor = ArgumentCaptor.forClass(RestaurantsWorkers.class);
        verify(restaurantsPersistencePort).enterEmployee(captor.capture());
        assertEquals(restaurantId, captor.getValue().getRestaurant());
        assertEquals(employeeId, captor.getValue().getEmployedId());
    }

    @Test
    void enterEmployee_ShouldThrowException_WhenNotOwner() {
        // Given
        Long ownerId = 1L;
        Long restaurantId = 2L;
        Long employeeId = 3L;

        when(restaurantsPersistencePort.isOwnerOfRestaurant(ownerId, restaurantId)).thenReturn(false);

        // When
        Executable action = () -> restaurantsUseCase.enterEmployee(ownerId, restaurantId, employeeId);

        // Then
        assertThrows(InvalidParameterException.class, action);
        verify(restaurantsPersistencePort, never()).enterEmployee(any());
    }

    @Test
    void getEmployeesByOwnerId_ShouldReturnEmployees_WhenOwnerIsValid() {
        // Given
        String ownerId = "1";
        List<Long> employees = List.of(100L, 101L, 102L);

        when(userPersistencePort.validateRoleOwner(1L)).thenReturn(true);
        when(restaurantsPersistencePort.findEmployeesByOwnerId(ownerId)).thenReturn(employees);

        // When
        List<Long> result = restaurantsUseCase.getEmployeesByOwnerId(ownerId);

        // Then
        assertEquals(employees, result);
    }

    @Test
    void getEmployeesByOwnerId_ShouldThrowException_WhenNotOwner() {
        // Given
        String ownerId = "1";

        when(userPersistencePort.validateRoleOwner(1L)).thenReturn(false);

        // When
        Executable action = () -> restaurantsUseCase.getEmployeesByOwnerId(ownerId);

        // Then
        assertThrows(InvalidParameterException.class, action);
        verify(restaurantsPersistencePort, never()).findEmployeesByOwnerId(any());
    }

    @Test
    void getRestaurants_ShouldReturnPagedResult() {
        // Given
        String order = "ASC";
        int page = 0;
        int size = 10;
        PagedResult<Restaurant> mockResult = new PagedResult<>(List.of(), 0, 10, 1, 0L);

        when(restaurantsPersistencePort.getRestaurants(any(), any())).thenReturn(mockResult);

        // When
        PagedResult<Restaurant> result = restaurantsUseCase.getRestaurants(order, page, size);

        // Then
        assertEquals(mockResult, result);
    }

    @Test
    void getMenu_ShouldReturnPagedResult() {
        // Given
        Long restaurantId = 1L;
        Long categoryId = 2L;
        String order = "ASC";
        int page = 0;
        int size = 10;
        PagedResult<Plate> mockResult = new PagedResult<>(List.of(), 0, 10, 1, 0L);

        when(restaurantsPersistencePort.getMenu(eq(restaurantId), eq(categoryId), any(), any())).thenReturn(mockResult);

        // When
        PagedResult<Plate> result = restaurantsUseCase.getMenu(restaurantId, categoryId, order, page, size);

        // Then
        assertEquals(mockResult, result);
    }
}