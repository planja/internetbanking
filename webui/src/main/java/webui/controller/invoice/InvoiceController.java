package webui.controller.invoice;

import domain.entity.user.Invoice;
import domain.entity.user.User;
import infrastructure.service.invoice.IInvoiceService;
import infrastructure.service.user.IUserService;
import infrastructure.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private IInvoiceService invoiceService;


    @RequestMapping(value = {"/invoice"}, method = RequestMethod.GET)
    public String payments() {
        return "invoice/invoicePage";
    }

    @RequestMapping(value = "/getUserInvoicesForNonUser", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InvoiceViewModel> getInvoices() {
        List<InvoiceViewModel> result = invoiceService.findAll().stream().map(InvoiceViewModel::new).collect(Collectors.toList());
        result = result.stream().filter(o -> !o.getCanUse() || o.getIsDeleted() || !o.getCanAddMoney()).collect(Collectors.toList());
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
        Invoice invoice = invoiceViewModel.toInvoice();
        return new InvoiceViewModel(invoiceService.update(invoice));

    }

    @RequestMapping(value = "/deleteInvoiceForNonUser/{id}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invoiceService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
