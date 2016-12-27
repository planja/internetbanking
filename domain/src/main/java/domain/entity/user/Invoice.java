package domain.entity.user;

import domain.entity.payment.Payment;
import domain.entity.transfer.Transfer;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 11.11.2016.
 */

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "money")
    private Long money;

    @Column(name = "can_use")
    private Boolean canUse;

    @Column(name = "can_add_money")
    private Boolean canAddMoney;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "number")
    private Long number;

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Payment> payments = new HashSet<>();

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Transfer> transfers = new HashSet<>();


    public static InvoiceBuilder invoiceBuilder = new InvoiceBuilder();

    public Invoice() {
    }

    public Invoice(User user, Long money, Set<Payment> payments) {
        this.user = user;
        this.money = money;
        this.payments = payments;
    }

    @PreRemove
    private void preRemove() {
        transfers.forEach(o -> o.setInvoice(null));
        payments.forEach(o -> o.setInvoice(null));
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

    public Set<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(Set<Transfer> transfers) {
        this.transfers = transfers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public static class InvoiceBuilder {
        private Long id;
        private User user;
        private Long money;
        private Boolean canUse;
        private Boolean isDeleted;
        private Boolean canAddMoney;
        private Long number;

        public InvoiceBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public InvoiceBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public InvoiceBuilder setMoney(Long money) {
            this.money = money;
            return this;
        }

        public InvoiceBuilder canUse(Boolean canUse) {
            this.canUse = canUse;
            return this;
        }

        public InvoiceBuilder setNumber(Long number) {
            this.number = number;
            return this;
        }

        public InvoiceBuilder isDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public InvoiceBuilder canAddMoney(Boolean canAddMoney) {
            this.canAddMoney = canAddMoney;
            return this;
        }

        public Invoice build() {
            Invoice invoice = new Invoice();
            invoice.setId(this.id);
            invoice.setUser(this.user);
            invoice.setMoney(this.money);
            invoice.setCanUse(this.canUse);
            invoice.setIsDeleted(this.isDeleted);
            invoice.setCanAddMoney(this.canAddMoney);
            return invoice;
        }
    }
}
