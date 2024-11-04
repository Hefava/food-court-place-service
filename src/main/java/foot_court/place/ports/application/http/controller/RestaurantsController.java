package foot_court.place.ports.application.http.controller;

import foot_court.place.domain.api.IRestaurantsServicePort;
import foot_court.place.domain.model.Plate;
import foot_court.place.domain.model.Restaurant;
import foot_court.place.domain.utils.pagination.PagedResult;
import foot_court.place.ports.application.http.dto.CreateRestaurantRequest;
import foot_court.place.ports.application.http.dto.GetPlatesRequest;
import foot_court.place.ports.application.http.dto.GetPlatesResponse;
import foot_court.place.ports.application.http.dto.RestaurantsResponse;
import foot_court.place.ports.application.http.mapper.CreateRestaurantRequestMapper;
import foot_court.place.ports.application.http.mapper.GetPlatesResponseMapper;
import foot_court.place.ports.application.http.mapper.RestaurantsResponseMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static foot_court.place.domain.utils.PlaceUtils.ORDER_DEFAULT_ASC;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class RestaurantsController {
    private final IRestaurantsServicePort restaurantServicePort;
    private final CreateRestaurantRequestMapper createRestaurantRequestMapper;
    private final RestaurantsResponseMapper restaurantsResponseMapper;
    private final GetPlatesResponseMapper getPlatesResponseMapper;

    @PostMapping("/create-restaurant")
    public ResponseEntity<Void> createRestaurant(
            @RequestBody @Parameter(required = true) CreateRestaurantRequest request) {
        Restaurant restaurant = createRestaurantRequestMapper.toDomain(request);
        restaurantServicePort.registerRestaurant(restaurant);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/enter-employee")
    public ResponseEntity<Void> enterEmployee(
            @RequestParam @Parameter Long ownerId,
            @RequestParam @Parameter Long restaurantId,
            @RequestParam @Parameter Long employeeId) {
        restaurantServicePort.enterEmployee(ownerId, restaurantId, employeeId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-restaurants")
    public ResponseEntity<PagedResult<RestaurantsResponse>> getRestaurants(
            @RequestParam(defaultValue = ORDER_DEFAULT_ASC) @Parameter String order,
            @PageableDefault(size = 5) @Parameter Pageable pageable) {

        PagedResult<Restaurant> result = restaurantServicePort.getRestaurants(order, pageable.getPageNumber(), pageable.getPageSize());
        PagedResult<RestaurantsResponse> response = new PagedResult<>(
                restaurantsResponseMapper.toResponseList(result.getContent()),
                result.getPage(),
                result.getPageSize(),
                result.getTotalPages(),
                result.getTotalCount()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-menu")
    public ResponseEntity<PagedResult<GetPlatesResponse>> getMenu(
            @RequestParam(defaultValue = ORDER_DEFAULT_ASC) @Parameter String order,
            @PageableDefault(size = 5) @Parameter Pageable pageable,
            @RequestBody @Parameter(required = true) GetPlatesRequest request) {
        PagedResult<Plate> result = restaurantServicePort.getMenu(request.getRestaurantId(), request.getCategoryId(), order, pageable.getPageNumber(), pageable.getPageSize());
        PagedResult<GetPlatesResponse> response = new PagedResult<>(
                getPlatesResponseMapper.toResponseList(result.getContent()),
                result.getPage(),
                result.getPageSize(),
                result.getTotalPages(),
                result.getTotalCount()
        );

        return ResponseEntity.ok(response);
    }
}
