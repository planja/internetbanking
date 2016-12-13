package webui.controller.registration;

import infrastructure.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import webui.viewmodel.user.UserViewModel;

/**
 * Created by admin on 02.11.2016.
 */


@Controller
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registrationPage() {
        return new ModelAndView("registration/registrationPage", "command", new UserViewModel());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("SpringWeb") UserViewModel userViewModel) throws Exception {
        userService.saveUser(userViewModel.toUser());
        return "registration/registrationSuccessfullPage";
    }

}
