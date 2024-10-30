package foot_court.place.ports.persistency.mysql.repository;

import foot_court.place.domain.utils.RestaurantUtils;
import foot_court.place.ports.persistency.mysql.entity.PlateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlatesRepository extends JpaRepository<PlateEntity, Long> {
    Optional<PlateEntity> findById(Long plateID);

    @Query(value = RestaurantUtils.PLATES_QUERY, nativeQuery = true)
    Page<PlateEntity> findByRestaurantIdAndCategoryId(
            @Param("restaurantId") Long restaurantId,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}