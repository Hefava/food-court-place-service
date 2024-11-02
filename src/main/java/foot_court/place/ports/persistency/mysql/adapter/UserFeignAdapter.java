package foot_court.place.ports.persistency.mysql.adapter;

import foot_court.place.domain.spi.IUserPersistencePort;
import foot_court.place.ports.persistency.mysql.repository.IUserFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFeignAdapter implements IUserPersistencePort {
    private final IUserFeign userFeign;

    @Override
    public Boolean validateRoleOwner(Long userID) {
        return userFeign.validateRoleOwner(userID);
    }

    @Override
    public Long getUserId() {
        UserDetails userId = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(userId.getUsername());
    }

    @Override
    public String getPhoneNumber(Long userID) {
        return userFeign.getPhoneNumber(userID);
    }
}
