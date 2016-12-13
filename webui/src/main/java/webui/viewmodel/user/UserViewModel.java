package webui.viewmodel.user;

import domain.entity.user.Role;
import domain.entity.user.RoleType;
import domain.entity.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by admin on 02.11.2016.
 */
public class UserViewModel {

    private Long id;

    private String userName;

    private String userPassword;

    private String name;

    private String mail;

    private String passportNumber;

    private String issuedPassport;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date passportIssuingDate;

    private List<Integer> roles = new ArrayList<>();

    public UserViewModel() {
    }


    public UserViewModel(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.userPassword = user.getUserPassword();
        this.name = user.getName();
        this.mail = user.getMail();
        this.passportNumber = user.getPassportNumber();
        this.issuedPassport = user.getIssuedPassport();
        this.passportIssuingDate = user.getPassportIssuingDate();
        this.roles = user.getRoles().stream().map(o -> Arrays.stream(RoleType.values())
                .filter(t -> Objects.equals(t.getText(), o.getRoleName())).findFirst().get().getValue()).collect(Collectors.toList());
    }

    public User toUser() {
        User user = new User();
        user.setId(this.getId());
        user.setMail(this.mail);
        user.setUserPassword(this.userPassword);
        user.setUserName(this.userName);
        user.setName(this.name);
        if (user.getRoles().size() == 0)
            user.setRoles(Collections.singletonList(new Role(user, "USER")).stream().collect(Collectors.toSet()));
        else
            user.setRoles(roles.stream()
                    .map(o -> new Role(user, Arrays.stream(RoleType.values()).filter(v -> v.getValue() == o).findFirst().get().getText())).collect(Collectors.toSet()));
        user.setPassportNumber(this.passportNumber);
        user.setIssuedPassport(this.issuedPassport);
        user.setPassportIssuingDate(this.passportIssuingDate);
        return user;
    }

    public UserViewModel(Long id, String userName, String userPassword, String name, String mail, String passportNumber, String issuedPassport, Date passportIssuingDate) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.name = name;
        this.mail = mail;
        this.passportNumber = passportNumber;
        this.issuedPassport = issuedPassport;
        this.passportIssuingDate = passportIssuingDate;
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

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
