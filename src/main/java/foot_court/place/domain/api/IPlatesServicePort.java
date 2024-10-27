package foot_court.place.domain.api;

import foot_court.place.domain.model.Plate;

public interface IPlatesServicePort {
    void createPlate(Plate plate);
    void updatePlate(Plate plate);
    void changeAvailability(Long plateId);
}
