package domain.entity.payment;

/**
 * Created by admin on 21.11.2016.
 */
public enum MobileOperator {
    VELCOM(0, "VELCOM"),
    LIFE(1, "LIFE"),
    MTC(2, "MTC");

    private final int value;
    private final String text;

    MobileOperator(int value, String text) {
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
