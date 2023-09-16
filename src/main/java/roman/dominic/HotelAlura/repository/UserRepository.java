package roman.dominic.HotelAlura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import roman.dominic.HotelAlura.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
