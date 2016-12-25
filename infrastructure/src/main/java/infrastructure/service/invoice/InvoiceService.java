package infrastructure.service.invoice;

import domain.entity.user.Invoice;
import domain.entity.user.User;
import infrastructure.repository.IInvoiceRepository;
import infrastructure.repository.IPaymentRepository;
import infrastructure.repository.ITransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    @Transactional
    public Invoice update(Invoice invoice) {
        Invoice find = invoiceRepository.findOne(invoice.getId());
        find.setCanUse(invoice.getCanUse());
        find.setMoney(invoice.getMoney());
        return invoiceRepository.save(find);
    }

    @Override
    @Transactional
    public Invoice save(Invoice invoice, User user) {
        invoice.setUser(user);
        List<Invoice> invoices = invoiceRepository.findAll();
        Long id = invoices.get(invoices.size() - 1).getId() + 1;
        invoice.setNumber(Long.valueOf("15" + "123321" + id.toString()));
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
