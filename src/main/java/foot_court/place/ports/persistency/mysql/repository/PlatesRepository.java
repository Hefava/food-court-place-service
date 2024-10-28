package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.ports.persistency.mysql.entity.PlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatesRepository extends JpaRepository<PlateEntity, Long> {
    Optional<PlateEntity> findById(Long plateID);
}
