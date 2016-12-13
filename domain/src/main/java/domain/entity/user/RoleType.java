package domain.entity.user;

/**
 * Created by admin on 08.11.2016.
 */
public enum RoleType {
    ROLE_ADMIN(0, "ADMIN"),
    ROLE_USER(1, "USER"),
    ROLE_OPERATOR(2, "OPERATOR");

    private final int value;
    private final String text;

    RoleType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
