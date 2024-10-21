package foot_court.place.ports.application.http.controller;

import foot_court.place.domain.api.IRestaurantsServicePort;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.ports.application.http.dto.CreateRestaurantRequest;
import foot_court.place.ports.application.http.mapper.CreateRestaurantRequestMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class RestaurantsController {
    private final IRestaurantsServicePort restaurantServicePort;
    private final CreateRestaurantRequestMapper createRestaurantRequestMapper;

    @PostMapping("/create-restaurant")
    public ResponseEntity<Void> createRestaurant(
            @RequestBody @Parameter(required = true) CreateRestaurantRequest request) {
        Restaurant restaurant = createRestaurantRequestMapper.toDomain(request);
        restaurantServicePort.registerRestaurant(restaurant);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
