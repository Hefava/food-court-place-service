package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.model.RestaurantsWorkers;
import foot_court.place.domain.spi.IRestaurantsPersistencePort;
import foot_court.place.domain.utils.pagination.PageRequestUtil;
import foot_court.place.domain.utils.pagination.PagedResult;
import foot_court.place.domain.utils.pagination.SortUtil;
import foot_court.place.ports.persistency.mysql.entity.RestaurantsEntity;
import foot_court.place.ports.persistency.mysql.entity.RestaurantsWorkersEntity;
import foot_court.place.ports.persistency.mysql.mapper.PlateEntityMapper;
import foot_court.place.ports.persistency.mysql.mapper.RestaurantsEntityMapper;
import foot_court.place.ports.persistency.mysql.repository.PlatesRepository;
import foot_court.place.ports.persistency.mysql.repository.RestaurantRepository;
import foot_court.place.ports.persistency.mysql.repository.RestaurantsWorkersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantsAdapter implements IRestaurantsPersistencePort {
    private final RestaurantRepository restaurantRepository;
    private final PlatesRepository platesRepository;
    private final RestaurantsWorkersRepository restaurantsWorkersRepository;
    private final RestaurantsEntityMapper restaurantMapper;
    private final PlateEntityMapper plateMapper;

    @Override
    public void registerRestaurant(Restaurant restaurant) {
        RestaurantsEntity restaurantEntity = restaurantMapper.toEntity(restaurant);
        restaurantRepository.save(restaurantEntity);
    }

    @Override
    public boolean isOwnerOfRestaurant(Long ownerId, Long restaurantId) {
        return restaurantRepository.existsByIdAndOwnerId(restaurantId, ownerId.toString());
    }

    @Override
    public Long getRestaurantOfEmployee(Long employeeId) {
        return restaurantsWorkersRepository.findByEmployedId(employeeId)
                .map(RestaurantsWorkersEntity::getRestaurantId)
                .orElse(null);
    }

    @Override
    public void enterEmployee(RestaurantsWorkers restaurantsWorkers) {
        RestaurantsWorkersEntity restaurantsWorkersEntity = new RestaurantsWorkersEntity();
        restaurantsWorkersEntity.setRestaurantId(restaurantsWorkers.getRestaurant());
        restaurantsWorkersEntity.setEmployedId(restaurantsWorkers.getEmployedId());
        restaurantsWorkersRepository.save(restaurantsWorkersEntity);
    }

    @Override
    public PagedResult<Restaurant> getRestaurants(SortUtil sortDomain, PageRequestUtil pageRequestDomain) {
        Sort sort = Sort.by(sortDomain.getDirection() == SortUtil.Direction.DESC ? Sort.Direction.DESC : Sort.Direction.ASC,
                sortDomain.getProperty());
        PageRequest pageRequest = PageRequest.of(pageRequestDomain.getPage(), pageRequestDomain.getSize(), sort);

        var page = restaurantRepository.findAll(pageRequest);
        List<Restaurant> content = page.getContent().stream()
                .map(restaurantMapper::toDomain)
                .toList();

        return new PagedResult<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @Override
    public PagedResult<Plate> getMenu(Long restaurantId, Long categoryId, SortUtil sortDomain, PageRequestUtil pageRequestDomain) {
        Sort sort = Sort.by(sortDomain.getDirection() == SortUtil.Direction.DESC ? Sort.Direction.DESC : Sort.Direction.ASC,
                sortDomain.getProperty());
        PageRequest pageRequest = PageRequest.of(pageRequestDomain.getPage(), pageRequestDomain.getSize(), sort);

        var page = platesRepository.findByRestaurantIdAndCategoryId(restaurantId, categoryId, pageRequest);
        List<Plate> content = page.getContent().stream()
                .map(plateMapper::toPlate)
                .toList();

        return new PagedResult<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @Override
    public boolean existsByName(String name) {
        return restaurantRepository.existsByName(name);
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toDomain)
                .orElse(null);
    }
}
