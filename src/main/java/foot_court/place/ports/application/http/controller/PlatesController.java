package foot_court.place.ports.application.http.controller;

import foot_court.place.domain.api.IPlatesServicePort;
import foot_court.place.domain.model.Plate;
import foot_court.place.ports.application.http.dto.CreatePlateRequest;
import foot_court.place.ports.application.http.mapper.CreatePlateRequestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plates")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class PlatesController {
    private final IPlatesServicePort platesServicePort;
    private final CreatePlateRequestMapper createPlateRequestMapper;

    @Operation(summary = "Register new plate", description = "Register a new plate in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plate created successfully"),
            @ApiResponse(responseCode = "400", description = "Wrong request", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/create-plate")
    public ResponseEntity<Void> registerPlate(
            @RequestBody @Parameter(required = true) CreatePlateRequest request) {
        Plate plate = createPlateRequestMapper.toPlate(request);
        platesServicePort.createPlate(plate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
