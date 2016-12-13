package domain.entity.payment;

/**
 * Created by admin on 21.11.2016.
 */
public enum PaymentType {
    MOBILE(0, "MOBILE"),
    INTERNET(1, "INTERNET");

    private final int value;
    private final String text;

    PaymentType(int value, String text) {
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
