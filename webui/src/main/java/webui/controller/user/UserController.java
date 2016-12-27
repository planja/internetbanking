package webui.controller.user;

import domain.entity.user.Invoice;
import domain.entity.user.RoleType;
import domain.entity.user.User;
import infrastructure.service.invoice.IInvoiceService;
import infrastructure.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webui.viewmodel.common.InfoViewModel;
import webui.viewmodel.user.InvoiceViewModel;
import webui.viewmodel.user.UserViewModel;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 04.11.2016.
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IInvoiceService invoiceService;

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ModelAndView userInfo(Principal principal) {
        String userName = principal.getName();
        User user = userService.findByUserName(userName);
        ModelAndView modelAndView = new ModelAndView("user/userInfoPage", "command", new UserViewModel(user));
        modelAndView.addObject("message", "Welcome " + user.getName());
        return modelAndView;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ModelAndView updateUser(@ModelAttribute("SpringWeb") UserViewModel userViewModel) throws Exception {
        User user = userService.updateUser(userViewModel.toUser());
        ModelAndView modelAndView = new ModelAndView("user/userInfoPage", "command", new UserViewModel(user));
        modelAndView.addObject("message", "Update successful");
        return modelAndView;
    }

    @RequestMapping(value = "/getRolesInfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InfoViewModel> getRolesInfo() {
        return Arrays.stream(RoleType.values()).map(o -> new InfoViewModel(o.getText(), o.getValue())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getUserInvoices", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InvoiceViewModel> getInvoices(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        List<InvoiceViewModel> result = user.getInvoices().stream().map(InvoiceViewModel::new).collect(Collectors.toList());
        result = result.stream().filter(o -> o.getCanUse() && !o.getIsDeleted()).collect(Collectors.toList());
        return result;

    }

    @RequestMapping(value = "/deleteInvoice/{id}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invoiceService.confirmDelete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/updateInvoice",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    InvoiceViewModel update(@RequestBody InvoiceViewModel invoiceViewModel) {
        Invoice invoice = invoiceViewModel.toInvoice();
        invoice.setCanAddMoney(false);
        return new InvoiceViewModel(invoiceService.update(invoice));
    }

    @RequestMapping(value = "/saveInvoice",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    InvoiceViewModel saveInvoice(@RequestBody InvoiceViewModel invoiceViewModel, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        return new InvoiceViewModel(invoiceService.save(invoiceViewModel.toInvoice(), user));

    }


}
