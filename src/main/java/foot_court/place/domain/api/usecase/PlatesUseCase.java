package foot_court.place.domain.api.usecase;

import foot_court.place.domain.api.IPlatesServicePort;
import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.spi.IPlatesPersistencePort;
import foot_court.place.domain.spi.IRestaurantsPersistencePort;
import foot_court.place.domain.spi.IUserPersistencePort;

import static foot_court.place.domain.utils.PlateUtils.*;

public class PlatesUseCase implements IPlatesServicePort {
    private final IPlatesPersistencePort platesPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IRestaurantsPersistencePort restaurantsPersistencePort;

    public PlatesUseCase(IPlatesPersistencePort platesPersistencePort, IUserPersistencePort userPersistencePort, IRestaurantsPersistencePort restaurantsPersistencePort) {
        this.platesPersistencePort = platesPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.restaurantsPersistencePort = restaurantsPersistencePort;
    }

    @Override
    public void createPlate(Plate plate) {
        validateInfo(plate);
        plate.setActive(true);
        platesPersistencePort.savePlate(plate);
    }

    @Override
    public void updatePlate(Plate plate) {
        validateInfo(plate);
        Plate existingPlate = platesPersistencePort.findPlateById(plate.getId());
        if (existingPlate == null) {
            throw new IllegalArgumentException(PLATE_NOT_FOUND + plate.getId());
        }
        platesPersistencePort.updatePlate(plate);
    }

    @Override
    public void changeAvailability(Long plateId) {
        validatePlateOwner(plateId);
        platesPersistencePort.changeAvailability(plateId);
    }

    private void validateInfo(Plate plate) {
        if (plate.getPrice() <= 0) {
            throw new IllegalArgumentException(PLATE_PRICE_ERROR);
        }
    }

    private void validatePlateOwner(Long plateId) {
        Long ownerId = userPersistencePort.getUserId();
        Plate plate = platesPersistencePort.findPlateById(plateId);
        Restaurant restaurant = restaurantsPersistencePort.findRestaurantById(plate.getRestaurantId().getId());
        if (!restaurant.getOwnerId().equals(ownerId.toString())) {
            throw new IllegalArgumentException(NOT_OWNER);
        }
    }
}
