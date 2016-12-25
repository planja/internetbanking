package webui.controller.invoice;

import domain.entity.user.User;
import infrastructure.service.invoice.IInvoiceService;
import infrastructure.service.user.IUserService;
import infrastructure.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import webui.viewmodel.user.InvoiceViewModel;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Никита on 25.12.2016.
 */
@Controller
public class InvoiceController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IInvoiceService invoiceService;


    @RequestMapping(value = {"/invoice"}, method = RequestMethod.GET)
    public String payments() {
        return "invoice/invoicePage";
    }

    @RequestMapping(value = "/getUserInvoicesForNonUser", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InvoiceViewModel> getInvoices(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        List<InvoiceViewModel> result = user.getInvoices().stream().map(InvoiceViewModel::new).collect(Collectors.toList());
        result = result.stream().filter(o -> !o.getCanUse()).collect(Collectors.toList());
        return result;
    }

    @RequestMapping(value = "/updateInvoiceForNonUser",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    InvoiceViewModel update(@RequestBody InvoiceViewModel invoiceViewModel) {
        return new InvoiceViewModel(invoiceService.update(invoiceViewModel.toInvoice()));

    }

}
