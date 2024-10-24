package foot_court.place.domain.api.usecase;

import foot_court.place.domain.api.IPlatesServicePort;
import foot_court.place.domain.model.Plate;
import foot_court.place.domain.spi.IPlatesPersistencePort;

public class PlatesUseCase implements IPlatesServicePort {
    private final IPlatesPersistencePort platesPersistencePort;

    public PlatesUseCase(IPlatesPersistencePort platesPersistencePort) {
        this.platesPersistencePort = platesPersistencePort;
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
            throw new IllegalArgumentException("Plate not found with ID: " + plate.getId());
        }
        platesPersistencePort.updatePlate(plate);
    }

    private void validateInfo(Plate plate) {
        if (plate.getPrice() <= 0) {
            throw new IllegalArgumentException("Plate price must be greater than 0");
        }
    }
}
