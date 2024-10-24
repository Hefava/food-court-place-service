package foot_court.place.infrastructure.configuration;

import foot_court.place.domain.api.IPlatesServicePort;
import foot_court.place.domain.api.IRestaurantsServicePort;
import foot_court.place.domain.api.usecase.PlatesUseCase;
import foot_court.place.domain.api.usecase.RestaurantsUseCase;
import foot_court.place.domain.spi.IPlatesPersistencePort;
import foot_court.place.domain.spi.IRestaurantsPersistencePort;
import foot_court.place.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantsPersistencePort restaurantsPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IPlatesPersistencePort platesPersistencePort;

    @Bean
    public IRestaurantsServicePort restaurantsServicePort() {
        return new RestaurantsUseCase(restaurantsPersistencePort, userPersistencePort);
    }

    @Bean
    public IPlatesServicePort platesPersistencePort() {
        return new PlatesUseCase(platesPersistencePort);
    }
}