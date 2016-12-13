package infrastructure.repository;

import domain.entity.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Никита on 11.12.2016.
 */
public interface ITransferRepository extends JpaRepository<Transfer,Long> {
}
