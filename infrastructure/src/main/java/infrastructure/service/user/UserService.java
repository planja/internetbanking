package infrastructure.service.user;

import domain.entity.user.Role;
import domain.entity.user.User;
import infrastructure.repository.*;
import infrastructure.service.payment.IPaymentService;
import infrastructure.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by admin on 01.11.2016.
 */
@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    @Transactional
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByuserName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User " + name + " was not found in the database");
        }
        List<GrantedAuthority> grantList = user.getRoles().stream().map(o -> new SimpleGrantedAuthority("ROLE_" + o.getRoleName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), grantList);
    }

    @Override
    @Transactional
    public void saveUser(User user) throws Exception {
        List<User> users = userRepository.findAll().stream().filter(o -> Objects.equals(o.getUserName(), user.getUserName())).collect(Collectors.toList());
        if (users.size() != 0)
            throw new Exception("User with current user name already exist. " + "<a href=\"/registration\">Try again</a>");
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) throws Exception {
        final User finalUser = user;
        Optional<User> userFromDataBase = userRepository.findAll().stream().filter(o -> Objects.equals(o.getUserName(), finalUser.getUserName())).findFirst();
        if (userFromDataBase.isPresent() && !Objects.equals(user.getUserName(), userFromDataBase.get().getUserName()))
            throw new Exception("User with current user name already exist. " + "<a href=\"/userInfo\">Try again</a>");
        User find = userRepository.findOne(user.getId());
        user.setRoles(find.getRoles());
        user = userRepository.save(user);
        UserDetails userDetails = this.loadUserByUsername(user.getUserName());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                userDetails, user.getUserPassword(), userDetails.getAuthorities()));
        return user;
    }

    @Override
    @Transactional
    public User updateRoles(List<Role> roles, Long userId) {
        User user = userRepository.findOne(userId);
        roles.forEach(o -> o.setUser(user));
        roleRepository.deleteRolesByUserId(userId);
        user.setRoles(new HashSet<>(roles));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUserByAdmin(User user) {
        roleRepository.deleteRolesByUserId(user.getId());
        User find = userRepository.findOne(user.getId());
        find.setUserName(user.getUserName());
        find.setUserPassword(user.getUserPassword());
        find.setName(user.getName());
        find.setMail(user.getMail());
        find.setPassportNumber(user.getPassportNumber());
        find.setIssuedPassport(user.getIssuedPassport());
        find.setRoles(user.getRoles());
        find.getRoles().forEach(o -> o.setUser(find));
        return userRepository.save(find);
    }

    @Override
    @Transactional
    public User saveUserByAdmin(User user) {
        user.getRoles().forEach(o -> o.setUser(user));
        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        List<User> users = userRepository.findAll();
        return users.stream().filter(o -> o.getUserName().equals(userName)).findFirst().get();
        //return userRepository.findByuserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findOne(id);
        userRepository.delete(user);
    }
}
