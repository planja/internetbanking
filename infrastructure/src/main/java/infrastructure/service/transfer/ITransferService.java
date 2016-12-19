package infrastructure.service.transfer;

import domain.entity.payment.Payment;
import domain.entity.transfer.Transfer;
import domain.entity.user.User;

import java.util.List;

/**
 * Created by Никита on 11.12.2016.
 */
public interface ITransferService {

    Transfer saveTransfer(Transfer transfer,User user);

    Transfer updateTransfer(Transfer transfer,User user);

    List<Transfer> findTransfers();

    void delete(Long id);

}
