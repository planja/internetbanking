package infrastructure.service.invoice;

import domain.entity.user.Invoice;
import domain.entity.user.User;

/**
 * Created by Никита on 13.11.2016.
 */
public interface IInvoiceService {

    Invoice update(Invoice invoice);

    Invoice save(Invoice invoice, User user);

    void delete(Long id);
}
