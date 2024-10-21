package foot_court.place.domain.spi;

public interface IUserPersistencePort {
    Boolean validateRoleOwner(Long id);
}
