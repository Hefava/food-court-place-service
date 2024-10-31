package foot_court.place.ports.application.http.mapper;

import foot_court.place.domain.model.Plate;
import foot_court.place.ports.application.http.dto.GetPlatesResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GetPlatesResponseMapper {
        List<GetPlatesResponse> toResponseList(List<Plate> plateList);
}