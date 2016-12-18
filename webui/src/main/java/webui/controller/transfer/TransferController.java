package webui.controller.transfer;

import domain.entity.transfer.Transfer;
import domain.entity.user.User;
import infrastructure.service.transfer.ITransferService;
import infrastructure.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import webui.viewmodel.payment.PaymentViewModel;
import webui.viewmodel.transfer.TransferViewModel;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Никита on 11.12.2016.
 */

@Controller
public class TransferController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITransferService transferService;

    @RequestMapping(value = {"/transfer"}, method = RequestMethod.GET)
    public String payments() {
        return "transfer/transferPage";
    }

    @RequestMapping(value = "/getTransfer", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<TransferViewModel> getTransfers(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        List<Transfer> transfers = transferService.findTransfers();
        List<TransferViewModel> result;
        List<Transfer> addedTransfers = transfers.stream().filter(transfer -> Objects.equals(transfer.getStatus(), "SUCCESSFUL") && Objects.equals(transfer.getUser().getId(), user.getId())).collect(Collectors.toList());
        if (user.getRoles().stream().filter(o -> o.getRoleName().equals("OPERATOR")).findFirst().isPresent()
                || user.getRoles().stream().filter(o -> o.getRoleName().equals("ADMIN")).findFirst().isPresent()) {
            result = transfers.stream().map(TransferViewModel::new).collect(Collectors.toList());
            result=result.stream().filter(o->o.getStatus()!=3).collect(Collectors.toList());
            result.addAll(addedTransfers.stream().map(TransferViewModel::new).collect(Collectors.toList()));
            return result;
        } else {
            return user.getTransfers().stream().map(TransferViewModel::new).collect(Collectors.toList());
        }
    }

    @RequestMapping(value = "/createTransfer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    TransferViewModel saveTransfer(@RequestBody TransferViewModel transferViewModel, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        return new TransferViewModel(transferService.saveTransfer(transferViewModel.toTransfer(), user));
    }

    @RequestMapping(value = "/updateTransfer",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    TransferViewModel updateTransfer(@RequestBody TransferViewModel transferViewModel, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        return new TransferViewModel(transferService.updateTransfer(transferViewModel.toTransfer(), user));
    }


}
