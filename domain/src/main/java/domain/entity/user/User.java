package domain.entity.user;

import domain.entity.payment.Payment;
import domain.entity.transfer.Transfer;
import org.hibernate.annotations.*;


import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 25.10.2016.
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "issued_passport")
    private String issuedPassport;

    @Column(name = "passport_issuing_date")
    private Date passportIssuingDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Role> roles = new HashSet<>();

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Transfer> transfers = new HashSet<>();

    public User() {

    }

    public User(String userName, String userPassword, String name, String mail, String passportNumber, String issuedPassport, Date passportIssuingDate, Set<Role> roles, Set<Invoice> invoices, Set<Payment> payments, Set<Transfer> transfers) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.name = name;
        this.mail = mail;
        this.passportNumber = passportNumber;
        this.issuedPassport = issuedPassport;
        this.passportIssuingDate = passportIssuingDate;
        this.roles = roles;
        this.invoices = invoices;
        this.payments = payments;
        this.transfers = transfers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getIssuedPassport() {
        return issuedPassport;
    }

    public void setIssuedPassport(String issuedPassport) {
        this.issuedPassport = issuedPassport;
    }

    public Date getPassportIssuingDate() {
        return passportIssuingDate;
    }

    public void setPassportIssuingDate(Date passportIssuingDate) {
        this.passportIssuingDate = passportIssuingDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(Set<Transfer> transfers) {
        this.transfers = transfers;
    }
}
