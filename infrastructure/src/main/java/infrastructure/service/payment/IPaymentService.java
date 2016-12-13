package infrastructure.service.payment;

import domain.entity.payment.Payment;
import domain.entity.user.User;

import java.util.List;

/**
 * Created by Никита on 26.11.2016.
 */
public interface IPaymentService {

    Payment savePayment(Payment payment,User user);

    Payment updatePayment(Payment payment,User user);

    List<Payment>  findPayments();

}
