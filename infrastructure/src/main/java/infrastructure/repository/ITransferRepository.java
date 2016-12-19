package infrastructure.repository;

import domain.entity.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Никита on 11.12.2016.
 */
public interface ITransferRepository extends JpaRepository<Transfer,Long> {

    @Modifying
    @Query("DELETE FROM Transfer i WHERE i.id = :id")
    void deleteTransfer(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Transfer i WHERE i.invoice.id = :id")
    void deleteTransferByInvoice(@Param("id") Long id);

}
