package webui.viewmodel.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import domain.entity.payment.*;
import domain.entity.services.Service;
import domain.entity.user.Invoice;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date created;

    private Long invoiceId;

    private Long userId;

    public PaymentViewModel(Payment payment, List<Service> serviceList) {
        this.id = payment.getId();
        switch (payment.getPaymentType()) {
            case "MOBILE":
                this.operator = Arrays.stream(MobileOperator.values()).filter(o -> o.getText().equals(payment.getOperator())).findFirst().get().getValue();
                break;
            case "INTERNET":
                this.operator = Arrays.stream(InternetOperator.values()).filter(o -> o.getText().equals(payment.getOperator())).findFirst().get().getValue();
                break;
            default:
                this.operator = MobileOperator.LIFE.getValue();
                break;
        }
        this.paymentType = serviceList.stream().filter(o -> o.getName().equals(payment.getPaymentType())).findFirst().get().getId().intValue();
        this.number = payment.getNumber();
        this.money = payment.getMoney();
        this.paymentStatus = Arrays.stream(PaymentStatus.values()).filter(o -> o.getText().equals(payment.getPaymentStatus())).findFirst().get().getValue();
        this.cause = payment.getCause();
        this.created = payment.getCreated();
        this.invoiceId = payment.getInvoice().getId();
        this.userId = payment.getUser() != null ? payment.getUser().getId() : null;
    }

    public Payment toPayment(List<Service> services) {
        Payment payment = new Payment();
        payment.setId(this.id);
        payment.setPaymentType(services.stream().filter(o -> o.getId().intValue() == this.paymentType).findFirst().get().getName());
        switch (payment.getPaymentType()) {
            case "MOBILE":
                payment.setOperator(Arrays.stream(MobileOperator.values()).filter(o -> o.getValue() == this.operator).findFirst().get().getText());
                break;
            case "INTERNET":
                payment.setOperator(Arrays.stream(InternetOperator.values()).filter(o -> o.getValue() == this.operator).findFirst().get().getText());
                break;
            default:
                payment.setOperator("NONE");
                break;
        }
        payment.setNumber(this.number);
        payment.setMoney(money);
        payment.setPaymentStatus(this.paymentStatus == null ? null : Arrays.stream(PaymentStatus.values()).filter(o -> o.getValue() == this.getPaymentStatus()).findFirst().get().getText());
        payment.setCause(this.cause == null ? null : this.cause);
        payment.setCreated(this.created == null ? null : new Date());
        payment.setInvoice(Invoice.invoiceBuilder.setId(this.invoiceId).build());
        return payment;

    }

    public PaymentViewModel() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
