package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.model.Plate;
import foot_court.place.domain.spi.IPlatesPersistencePort;
import foot_court.place.ports.persistency.mysql.entity.PlateEntity;
import foot_court.place.ports.persistency.mysql.mapper.PlateEntityMapper;
import foot_court.place.ports.persistency.mysql.repository.PlatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlatesAdapter implements IPlatesPersistencePort {
    private final PlatesRepository platesRepository;
    private final PlateEntityMapper plateMapper;

    @Override
    public void savePlate(Plate plate) {
        PlateEntity plateEntity = plateMapper.toEntity(plate);
        platesRepository.save(plateEntity);
    }
}
