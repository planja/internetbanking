package webui.viewmodel.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import domain.entity.payment.*;
import domain.entity.user.Invoice;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by admin on 22.11.2016.
 */
public class PaymentViewModel {

    private Long id;

    private Integer operator;

    private Integer paymentType;

    private String number;

    private Integer money;

    private Integer paymentStatus;

    private String cause;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    private Date created;

    private Long invoiceId;

    public PaymentViewModel(Payment payment) {
        this.id = payment.getId();
        this.operator = payment.getPaymentType().equals(PaymentType.INTERNET.getText())
                ? Arrays.stream(InternetOperator.values()).filter(o -> o.getText().equals(payment.getOperator())).findFirst().get().getValue()
                : Arrays.stream(MobileOperator.values()).filter(o -> o.getText().equals(payment.getOperator())).findFirst().get().getValue();
        this.paymentType = Arrays.stream(PaymentType.values()).filter(o -> o.getText().equals(payment.getPaymentType())).findFirst().get().getValue();
        this.number = payment.getNumber();
        this.money = payment.getMoney();
        this.paymentStatus = Arrays.stream(PaymentStatus.values()).filter(o -> o.getText().equals(payment.getPaymentStatus())).findFirst().get().getValue();
        this.cause = payment.getCause();
        this.created = payment.getCreated();
        this.invoiceId = payment.getInvoice().getId();
    }

    public Payment toPayment() {
        Payment payment = new Payment();
        payment.setId(this.id);
        payment.setOperator(this.paymentType == 0 ? Arrays.stream(MobileOperator.values()).filter(o -> o.getValue() == this.operator).findFirst().get().getText()
                : Arrays.stream(InternetOperator.values()).filter(o -> o.getValue() == this.operator).findFirst().get().getText());
        payment.setPaymentType(Arrays.stream(PaymentType.values()).filter(o -> o.getValue() == this.paymentType).findFirst().get().getText());
        payment.setNumber(this.number);
        payment.setMoney(money);
        payment.setPaymentStatus(this.paymentStatus == null ? null : Arrays.stream(PaymentStatus.values()).filter(o->o.getValue()==this.getPaymentStatus()).findFirst().get().getText());
        payment.setCause(this.cause == null ? null : this.cause);
        payment.setCreated(this.created == null ? null : new Date());
        payment.setInvoice(Invoice.invoiceBuilder.setId(this.invoiceId).build());
        return payment;

    }

    public PaymentViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String  getNumber() {
        return number;
    }

    public void setNumber(String  number) {
        this.number = number;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
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
}
