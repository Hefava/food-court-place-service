package foot_court.place.domain.api.usecase;

import foot_court.place.domain.api.IRestaurantsServicePort;
import foot_court.place.domain.exception.MultipleRestaurantValidationExceptions;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.spi.IRestaurantsPersistencePort;
import foot_court.place.domain.spi.IUserPersistencePort;
import foot_court.place.domain.utils.RestaurantUtils;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsUseCase implements IRestaurantsServicePort {
    private final IRestaurantsPersistencePort restaurantsPersistencePort;
    private final IUserPersistencePort userPersistencePort;

    public RestaurantsUseCase(IRestaurantsPersistencePort restaurantsPersistencePort, IUserPersistencePort userPersistencePort) {
        this.restaurantsPersistencePort = restaurantsPersistencePort;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void registerRestaurant(Restaurant restaurant) {
        validateInfo(restaurant);
        restaurantsPersistencePort.registerRestaurant(restaurant);
    }

    private void validateInfo(Restaurant restaurant) {
        List<String> errors = new ArrayList<>();
        if (restaurantsPersistencePort.existsByName(restaurant.getName())) {
            errors.add(RestaurantUtils.RESTAURANT_ALREADY_EXISTS);
        }
        if (restaurant.getName() == null || restaurant.getName().length() > RestaurantUtils.MAX_RESTAURANT_NAME_LENGHT
                || !restaurant.getName().matches(RestaurantUtils.VALID_RESTAURANT_NAME_REGEX)) {
            errors.add(RestaurantUtils.INVALID_RESTAURANT_NAME);
        }
        if (restaurant.getNit() == null || !restaurant.getNit().matches(RestaurantUtils.ONLY_NUMBER_REGEX)) {
            errors.add(RestaurantUtils.INVALID_NIT);
        }
        if (restaurant.getPhone() == null || restaurant.getPhone().length() > RestaurantUtils.MAX_PHONE_NUMBER_LENGTH
                || !restaurant.getPhone().matches(RestaurantUtils.ONLY_NUMBER_REGEX)) {
            errors.add(RestaurantUtils.INVALID_PHONE_NUMBER);
        }
        Long ownerIdLong = Long.parseLong(restaurant.getOwnerId());
        if (Boolean.FALSE.equals(userPersistencePort.validateRoleOwner(ownerIdLong))) {
            errors.add(RestaurantUtils.INVALID_OWNER);
        }
        if (!errors.isEmpty()) {
            throw new MultipleRestaurantValidationExceptions(errors);
        }
    }
}