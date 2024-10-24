package foot_court.place.domain.spi;

import foot_court.place.domain.model.Plate;

public interface IPlatesPersistencePort {
    void savePlate(Plate plate);
    void updatePlate(Plate plate);
    Plate findPlateById(Long id);
}