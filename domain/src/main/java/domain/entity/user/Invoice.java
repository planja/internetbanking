package domain.entity.user;

import domain.entity.payment.Payment;

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

    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Payment> payments = new HashSet<>();

    public static InvoiceBuilder invoiceBuilder = new InvoiceBuilder();

    public Invoice() {
    }

    public Invoice(User user, Long money, Set<Payment> payments) {
        this.user = user;
        this.money = money;
        this.payments = payments;
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

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public static class InvoiceBuilder {
        private Long id;
        private User user;
        private Long money;

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

        public Invoice build() {
            Invoice invoice = new Invoice();
            invoice.setId(this.id);
            invoice.setUser(this.user);
            invoice.setMoney(this.money);
            return invoice;
        }
    }
}
