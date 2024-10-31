package foot_court.place.ports.application.http.controller;

import foot_court.place.domain.api.IOrderServicePort;
import foot_court.place.ports.application.http.dto.ClientOrderRequest;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class OrderController {
    private final IOrderServicePort orderServicePort;

    @PostMapping("/create-order")
    public ResponseEntity<Void> createOrder(
            @RequestBody @Parameter(required = true) ClientOrderRequest request) {
        orderServicePort.createOrder(request.getRestaurantId(), request.getPlateOrders());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
