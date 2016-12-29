package webui.controller.admin;

import domain.entity.user.Role;
import domain.entity.user.RoleType;
import domain.entity.user.User;
import infrastructure.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webui.viewmodel.user.UserViewModel;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by admin on 04.11.2016.
 */
@Controller
public class AdminController {

    @Autowired
    private IUserService userService;


    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage() {
        return "admin/adminPage";
    }


    @RequestMapping(value = "/findAllUser", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<UserViewModel> read() {
        return userService.findAll().stream().map(UserViewModel::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/saveUserByAdmin", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    UserViewModel saveUser(@RequestBody UserViewModel userViewModel) {
        List<Role> roles = userViewModel.getRoles().stream().map(o -> new Role(null, Arrays.stream(RoleType.values())
                .filter(r -> r.getValue() == o).findFirst().get().getText())).collect(Collectors.toList());
        User user = userViewModel.toUser();
        user.setRoles(new HashSet<>(roles));
        return new UserViewModel(userService.saveUserByAdmin(user));
    }

    @RequestMapping(value = "/updateRoles", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    UserViewModel updateUser(@RequestBody UserViewModel userViewModel) {
        List<Role> roles = userViewModel.getRoles().stream().map(o -> new Role(null, Arrays.stream(RoleType.values())
                .filter(r -> r.getValue() == o).findFirst().get().getText())).collect(Collectors.toList());
        User user = userViewModel.toUser();
        user.setRoles(new HashSet<>(roles));
        return new UserViewModel(userService.updateUserByAdmin(user));
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
