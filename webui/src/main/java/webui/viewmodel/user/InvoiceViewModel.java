package webui.viewmodel.user;

import domain.entity.user.Invoice;

/**
 * Created by Никита on 13.11.2016.
 */
public class InvoiceViewModel {

    public Long id;

    public Long money;

    private Long userId;

    private Boolean canUse;

    private Long number;

    private Boolean canAddMoney;

    private Boolean isDeleted;

    public InvoiceViewModel() {
    }

    public InvoiceViewModel(Invoice invoice) {
        this.id = invoice.getId();
        this.money = invoice.getMoney();
        this.userId = invoice.getUser().getId();
        this.canUse = invoice.getCanUse();
        this.number = invoice.getNumber();
        this.isDeleted = invoice.getIsDeleted();
        this.canAddMoney = invoice.getCanAddMoney();
    }

    public Invoice toInvoice() {
        Invoice invoice = new Invoice();
        invoice.setId(this.getId());
        invoice.setMoney(this.getMoney());
        invoice.setCanUse(this.getCanUse() == null ? false : this.getCanUse());
        invoice.setNumber(this.getNumber());
        invoice.setCanAddMoney(this.getCanAddMoney() == null ? false : this.getCanAddMoney());
        invoice.setIsDeleted(this.getIsDeleted() == null ? false : this.getIsDeleted());
        return invoice;
    }

    public Boolean getCanAddMoney() {
        return canAddMoney;
    }

    public void setCanAddMoney(Boolean canAddMoney) {
        this.canAddMoney = canAddMoney;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }
}
