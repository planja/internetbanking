package infrastructure.service.payment;

import domain.entity.payment.Payment;
import domain.entity.payment.PaymentStatus;
import domain.entity.user.Invoice;
import domain.entity.user.User;
import infrastructure.repository.IInvoiceRepository;
import infrastructure.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Никита on 26.11.2016.
 */
@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    public Payment savePayment(Payment payment, User user) {
        payment.setUser(user);
        payment.setInvoice(user.getInvoices().stream()
                .filter(o -> Objects.equals(o.getId(), payment.getInvoice().getId())).findFirst().get());
        payment.setCreated(new Date());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment, User user) {
        Payment find = paymentRepository.findOne(payment.getId());
        Invoice invoice = invoiceRepository.findOne(payment.getInvoice().getId());
        if (invoice.getMoney() < payment.getMoney()) {
            find.setCause("Not enough money");
            find.setPaymentStatus("QUEUE");
        } else if (payment.getPaymentStatus().equals("SUCCESSFUL")) {
            invoice.setMoney(invoice.getMoney() - payment.getMoney());
            invoiceRepository.save(invoice);
            find.setCause(payment.getCause());
            find.setPaymentStatus(Arrays.stream(PaymentStatus.values())
                    .filter(o -> Objects.equals(o.getText(), payment.getPaymentStatus())).findFirst().get().getText());
        } else {
            find.setCause(payment.getCause());
            find.setPaymentStatus(payment.getPaymentStatus());
        }
        return paymentRepository.save(find);
    }

    @Override
    public List<Payment> findPayments() {
        return paymentRepository.findAll();
    }
}
