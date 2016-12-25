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

    public InvoiceViewModel() {
    }

    public InvoiceViewModel(Invoice invoice) {
        this.id = invoice.getId();
        this.money = invoice.getMoney();
        this.userId = invoice.getUser().getId();
        this.canUse = invoice.getCanUse();
    }

    public Invoice toInvoice() {
        Invoice invoice = new Invoice();
        invoice.setId(this.getId());
        invoice.setMoney(this.getMoney());
        invoice.setCanUse(this.getCanUse() == null ? false : this.getCanUse());
        return invoice;
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
