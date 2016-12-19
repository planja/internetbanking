package infrastructure.service.invoice;

import domain.entity.user.Invoice;
import domain.entity.user.User;
import infrastructure.repository.IInvoiceRepository;
import infrastructure.repository.IPaymentRepository;
import infrastructure.repository.ITransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Никита on 13.11.2016.
 */
@Service
public class InvoiceService implements IInvoiceService {

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private ITransferRepository transferRepository;

    @Override
    @Transactional
    public Invoice update(Invoice invoice) {
        Invoice find = invoiceRepository.findOne(invoice.getId());
        find.setMoney(invoice.getMoney());
        return invoiceRepository.save(find);
    }

    @Override
    @Transactional
    public Invoice save(Invoice invoice, User user) {
        invoice.setUser(user);
        return invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        transferRepository.deleteTransferByInvoice(id);
        paymentRepository.deletePaymentByInvoice(id);
        invoiceRepository.deleteInvoice(id);
    }

}
