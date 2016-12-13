package infrastructure.service.transfer;

import domain.entity.payment.Payment;
import domain.entity.payment.PaymentStatus;
import domain.entity.transfer.Transfer;
import domain.entity.user.Invoice;
import domain.entity.user.User;
import infrastructure.repository.IInvoiceRepository;
import infrastructure.repository.ITransferRepository;
import infrastructure.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Никита on 11.12.2016.
 */
@Service
public class TransferService implements ITransferService {

    @Autowired
    private ITransferRepository transferRepository;

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Autowired
    private IUserService userService;

    @Override
    public Transfer saveTransfer(Transfer transfer, User user) {
        transfer.setUser(user);
        transfer.setInvoice(user.getInvoices().stream()
                .filter(o -> Objects.equals(o.getId(), transfer.getInvoice().getId())).findFirst().get());
        transfer.setCreated(new Date());
        transfer.setStatus(PaymentStatus.ACCEPT.getText());
        return transferRepository.save(transfer);
    }

    @Override
    public Transfer updateTransfer(Transfer transfer, User user) {
        Transfer find = transferRepository.findOne(transfer.getId());
        Invoice invoice = invoiceRepository.findOne(transfer.getInvoice().getId());
        if (invoice.getMoney() < transfer.getMoney()) {
            find.setCause("Not enough money");
            find.setStatus("QUEUE");
        } else if (transfer.getStatus().equals("SUCCESSFUL")) {
            if (transfer.getBankNumber().equals(123321)) {
                Invoice billInvoice = invoiceRepository.findOne((long) transfer.getBillNumber());
                if (billInvoice != null) {
                    billInvoice.setMoney(billInvoice.getMoney() + transfer.getMoney());
                    invoiceRepository.save(billInvoice);
                }

            }
            invoice.setMoney(invoice.getMoney() - transfer.getMoney());
            invoiceRepository.save(invoice);
            find.setCause(transfer.getCause());
            find.setStatus(Arrays.stream(PaymentStatus.values())
                    .filter(o -> Objects.equals(o.getText(), transfer.getStatus())).findFirst().get().getText());
        } else {
            find.setCause(transfer.getCause());
            find.setStatus(transfer.getStatus());
        }
        return transferRepository.save(find);
    }

    @Override
    public List<Transfer> findTransfers() {
        return transferRepository.findAll();
    }
}

