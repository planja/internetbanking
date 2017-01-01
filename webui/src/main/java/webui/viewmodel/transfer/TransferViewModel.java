package webui.viewmodel.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import domain.entity.payment.PaymentStatus;
import domain.entity.transfer.Transfer;
import domain.entity.user.Invoice;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Никита on 11.12.2016.
 */
public class TransferViewModel {

    public Long id;

    public Integer money;

    public Integer bankNumber;

    public Integer billNumber;

    private String receiverFullName;

    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date created;

    private Long invoiceId;

    private String cause;

    private Long userId;

    public TransferViewModel() {
    }

    public Transfer toTransfer() {
        Transfer transfer = new Transfer();
        transfer.setId(this.getId());
        transfer.setMoney(this.getMoney());
        transfer.setBankNumber(this.getBankNumber());
        transfer.setBillNumber(this.getBillNumber());
        transfer.setReceiverFullName(this.getReceiverFullName());
        transfer.setStatus(this.getStatus() == null ? null : Arrays.stream(PaymentStatus.values()).filter(o -> o.getValue() == this.getStatus()).findFirst().get().getText());
        transfer.setCreated(this.created == null ? null : new Date());
        transfer.setInvoice(Invoice.invoiceBuilder.setId(this.invoiceId).build());
        transfer.setCause(this.getCause());
        return transfer;
    }


    public TransferViewModel(Transfer transfer) {
        this.id = transfer.getId();
        this.money = transfer.getMoney();
        this.bankNumber = transfer.getBankNumber();
        this.billNumber = transfer.getBillNumber();
        this.receiverFullName = transfer.getReceiverFullName();
        this.status = Arrays.stream(PaymentStatus.values()).filter(o -> o.getText().equals(transfer.getStatus())).findFirst().get().getValue();
        this.created = transfer.getCreated();
        this.invoiceId = transfer.getInvoice().getId();
        this.cause = transfer.getCause();
        this.userId = transfer.getUser() != null ? transfer.getUser().getId() : null;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(Integer bankNumber) {
        this.bankNumber = bankNumber;
    }

    public Integer getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(Integer billNumber) {
        this.billNumber = billNumber;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public void setReceiverFullName(String receiverFullName) {
        this.receiverFullName = receiverFullName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
