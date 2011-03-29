package com.xerox.amazonws.fps;

/**
 * @author J. Bernard
 * @author Elastic Grid, LLC.
 * @author jerome.bernard@elastic-grid.com
 */
public enum PaymentMethod {
    AMAZON("ABT"),
    BANK("ACH"),
    CREDIT_CARD("CC"),
    DEBT("Debt"),
    PREPAID("Prepaid");

    private final String value;

    PaymentMethod(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PaymentMethod fromValue(String v) {
        for (PaymentMethod c: PaymentMethod.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
