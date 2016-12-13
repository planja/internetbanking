package infrastructure.repository;

import domain.entity.user.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Никита on 13.11.2016.
 */
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {

    @Modifying
    @Query("DELETE FROM Invoice i WHERE i.id = :id")
    void deleteInvoice(@Param("id") Long id);

}
