package foot_court.place.ports.persistency.mysql.mapper;

import foot_court.place.domain.model.Category;
import foot_court.place.ports.persistency.mysql.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryEntity toEntity(Category category);

    Category toModel(CategoryEntity categoryEntity);
}