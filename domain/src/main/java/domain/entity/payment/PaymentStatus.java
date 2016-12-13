package domain.entity.payment;

/**
 * Created by admin on 21.11.2016.
 */
public enum PaymentStatus {

    ACCEPT(0, "ACCEPT"),
    REJECTED(1, "REJECTED"),
    QUEUE(2, "QUEUE"),
    SUCCESSFUL(3, "SUCCESSFUL");

    PaymentStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    private final int value;
    private final String text;

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
