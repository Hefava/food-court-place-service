package foot_court.place.ports.application.http.controller;

import foot_court.place.domain.api.IOrderServicePort;
import foot_court.place.domain.utils.OrdersWithPlates;
import foot_court.place.domain.utils.pagination.PagedResult;
import foot_court.place.ports.application.http.dto.ClientOrderRequest;
import foot_court.place.ports.application.http.dto.ViewOrdersResponse;
import foot_court.place.ports.application.http.mapper.ViewOrdersResponseMapper;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class OrderController {
    private final IOrderServicePort orderServicePort;
    private final ViewOrdersResponseMapper viewOrdersResponseMapper;

    @PostMapping("/create-order")
    public ResponseEntity<Void> createOrder(
            @RequestBody @Parameter(required = true) ClientOrderRequest request) {
        orderServicePort.createOrder(request.getRestaurantId(), request.getPlateOrders());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/view-orders")
    public ResponseEntity<PagedResult<ViewOrdersResponse>> viewOrders(
            @RequestParam @Parameter String status,
            @PageableDefault(size = 5) @Parameter Pageable pageable) {
        PagedResult<OrdersWithPlates> result = orderServicePort.viewOrders(status, pageable.getPageNumber(), pageable.getPageSize());
        PagedResult<ViewOrdersResponse> response = new PagedResult<>(
                viewOrdersResponseMapper.toResponseList(result.getContent()),
                result.getPage(),
                result.getPageSize(),
                result.getTotalPages(),
                result.getTotalCount()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/assign-order")
    public ResponseEntity<Void> assingOrder(
            @RequestParam @Parameter Long orderId) {
        orderServicePort.updateOrderStatus(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}