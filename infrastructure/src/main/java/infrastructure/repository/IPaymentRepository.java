package infrastructure.repository;

import domain.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Никита on 26.11.2016.
 */
public interface IPaymentRepository extends JpaRepository<Payment,Long> {
}
