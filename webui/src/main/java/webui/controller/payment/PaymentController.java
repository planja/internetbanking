package webui.controller.payment;

import domain.entity.payment.*;
import domain.entity.services.Service;
import domain.entity.user.User;
import infrastructure.service.invoice.IInvoiceService;
import infrastructure.service.payment.IPaymentService;
import infrastructure.service.service.IServiceService;
import infrastructure.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webui.viewmodel.common.InfoViewModel;
import webui.viewmodel.payment.PaymentViewModel;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by admin on 21.11.2016.
 */
@Controller
public class PaymentController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IServiceService serviceService;

    @Autowired
    private IInvoiceService invoiceService;

    @RequestMapping(value = {"/payment"}, method = RequestMethod.GET)
    public String payments() {
        return "payment/paymentPage";
    }

    @RequestMapping(value = "/getInvoicesInfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InfoViewModel> getUserInvoicesInfo(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        if (user.getRoles().stream().filter(o -> o.getRoleName().equals("OPERATOR")).findFirst().isPresent()
                || user.getRoles().stream().filter(o -> o.getRoleName().equals("ADMIN")).findFirst().isPresent()) {
            return invoiceService.findAll().stream().map(o -> new InfoViewModel(o.getNumber().toString(), o.getId().intValue())).collect(Collectors.toList());
        } else {
            return user.getInvoices()
                    .stream().map(o -> new InfoViewModel(String.valueOf(o.getNumber()), o.getId().intValue())).collect(Collectors.toList());
        }
    }

    @RequestMapping(value = "/getPaymentTypesInfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InfoViewModel> getPaymentTypesInfo() {
        return serviceService.findAll().stream().map(o -> new InfoViewModel(o.getName(), o.getId().intValue())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getMobileOperatorsInfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InfoViewModel> getMobileOperatorsInfo() {
        return Arrays.stream(MobileOperator.values()).map(o -> new InfoViewModel(o.getText(), o.getValue())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getInternetOperatorsInfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InfoViewModel> getInternetOperatorsInfo() {
        return Arrays.stream(InternetOperator.values()).map(o -> new InfoViewModel(o.getText(), o.getValue())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getPaymentStatusInfo", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<InfoViewModel> getPaymentStatusInfo() {
        return Arrays.stream(PaymentStatus.values()).map(o -> new InfoViewModel(o.getText(), o.getValue())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getPayments", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<PaymentViewModel> getPayments(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        List<Service> services = serviceService.findAll();
        List<Payment> payments = paymentService.findPayments();
        List<PaymentViewModel> result;
        List<Payment> addedPayments = payments.stream().filter(payment -> Objects.equals(payment.getPaymentStatus(), "SUCCESSFUL") && Objects.equals(payment.getUser().getId(), user.getId())).collect(Collectors.toList());
        if (user.getRoles().stream().filter(o -> o.getRoleName().equals("OPERATOR")).findFirst().isPresent()
                || user.getRoles().stream().filter(o -> o.getRoleName().equals("ADMIN")).findFirst().isPresent()) {
            result = payments.stream().map(o -> new PaymentViewModel(o, services)).collect(Collectors.toList());
            result = result.stream().filter(o -> o.getPaymentStatus() != 3).collect(Collectors.toList());
            result.addAll(addedPayments.stream().map(o -> new PaymentViewModel(o, services)).collect(Collectors.toList()));
            return result;
        } else {
            return user.getPayments().stream().map(o -> new PaymentViewModel(o, services)).collect(Collectors.toList());
        }

    }

    @RequestMapping(value = "/createPayment",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    PaymentViewModel savePayment(@RequestBody PaymentViewModel paymentViewModel, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        List<Service> services = serviceService.findAll();
        return new PaymentViewModel(paymentService.savePayment(paymentViewModel.toPayment(services), user), services);
    }

    @RequestMapping(value = "/updatePayment",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    PaymentViewModel updatePayment(@RequestBody PaymentViewModel paymentViewModel, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        List<Service> services = serviceService.findAll();
        return new PaymentViewModel(paymentService.updatePayment(paymentViewModel.toPayment(services), user), services);
    }

    @RequestMapping(value = "/deletePayment/{id}", method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
