package foot_court.place.ports.application.http.mapper;

import foot_court.place.domain.model.Plate;
import foot_court.place.ports.application.http.dto.UpdatePlateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdatePlateRequestMapper {
    Plate toPlate(UpdatePlateRequest updatePlateRequest);
}
