package foot_court.place.infrastructure.configuration;

import foot_court.place.domain.api.IOrderServicePort;
import foot_court.place.domain.api.IPlatesServicePort;
import foot_court.place.domain.api.IRestaurantsServicePort;
import foot_court.place.domain.api.usecase.OrderUseCase;
import foot_court.place.domain.api.usecase.PlatesUseCase;
import foot_court.place.domain.api.usecase.RestaurantsUseCase;
import foot_court.place.domain.spi.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantsPersistencePort restaurantsPersistencePort;
    private final IUserPersistencePort userPersistencePort;
    private final IPlatesPersistencePort platesPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IMessagingFeignPersistencePort messagingFeignPersistencePort;

    @Bean
    public IRestaurantsServicePort restaurantsServicePort() {
        return new RestaurantsUseCase(restaurantsPersistencePort, userPersistencePort);
    }

    @Bean
    IOrderServicePort orderPersistencePort() {
        return new OrderUseCase(orderPersistencePort, userPersistencePort, restaurantsPersistencePort, messagingFeignPersistencePort);
    }

    @Bean
    public IPlatesServicePort platesPersistencePort() {
        return new PlatesUseCase(platesPersistencePort, userPersistencePort, restaurantsPersistencePort);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}