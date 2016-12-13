package domain.entity.payment;

/**
 * Created by admin on 21.11.2016.
 */
public enum InternetOperator {
    COSMOS(0, "COSMOS"),
    BYFLY(1, "BYFLY"),
    DOMOVIK(2, "DOMOVIK"),
    ATLANT(3, "ATLANT");

    private final int value;
    private final String text;

    InternetOperator(int value, String text) {
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
