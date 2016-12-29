package infrastructure.service.user;

import domain.entity.user.Role;
import domain.entity.user.User;

import java.util.List;

/**
 * Created by admin on 01.11.2016.
 */
public interface IUserService {

    User findById(Long id);

    void saveUser(User user) throws Exception;

    User findByUserName(String name);

    User updateUser(User user) throws Exception;

    List<User> findAll();

    void delete(Long id);

    User updateRoles(List<Role> roles, Long userId);

    User saveUserByAdmin(User user);

    User updateUserByAdmin(User user);

}
