package infrastructure.repository;

import domain.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Никита on 26.11.2016.
 */
public interface IPaymentRepository extends JpaRepository<Payment,Long> {

    @Modifying
    @Query("DELETE FROM Payment i WHERE i.id = :id")
    void deletePayment(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Payment i WHERE i.invoice.id = :id")
    void deletePaymentByInvoice(@Param("id") Long id);

}
