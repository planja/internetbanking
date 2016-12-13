package webui.viewmodel.user;

import domain.entity.user.Invoice;

/**
 * Created by Никита on 13.11.2016.
 */
public class InvoiceViewModel {

    public Long id;

    public Long money;

    private Long userId;

    public InvoiceViewModel() {
    }

    public InvoiceViewModel(Invoice invoice) {
        this.id = invoice.getId();
        this.money = invoice.getMoney();
        this.userId = invoice.getUser().getId();
    }

    public Invoice toInvoice() {
        Invoice invoice = new Invoice();
        invoice.setId(this.getId());
        invoice.setMoney(this.getMoney());
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
}
