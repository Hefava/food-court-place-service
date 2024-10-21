package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.spi.IUserPersistencePort;
import foot_court.place.ports.persistency.mysql.repository.IUserFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFeignAdapter implements IUserPersistencePort {
    private final IUserFeign userFeign;

    @Override
    public Boolean validateRoleOwner(Long userID) {
        return userFeign.validateRoleOwner(userID);
    }
}
