package infrastructure.repository;

import domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Никита on 30.10.2016.
 */
public interface IUserRepository extends JpaRepository<User, Long> {

    User findByuserName(String name);
}
