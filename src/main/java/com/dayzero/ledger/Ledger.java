package com.dayzero.ledger;

import org.springframework.stereotype.Component;

/**
 * A running total of recorded payments. Plain business logic, kept out of the
 * web layer so the fast unit suite can test it without Spring.
 */
@Component
public class Ledger {

    /** The largest single payment accepted: Rs 1,00,000 in paise. */
    public static final long MAX_AMOUNT_PAISE = 100_000_00L;

    private long totalPaise = 0;

    /**
     * Returns a problem string, or null when the amount is acceptable.
     */
    public static String validateAmount(long paise) {
        if (paise <= 0) {
            return "amount must be a positive number of paise";
        }
        if (paise > MAX_AMOUNT_PAISE) {
            return "amount exceeds the single-payment limit";
        }
        return null;
    }

    /** Adds an already-validated amount and returns the new total. */
    public synchronized long record(long paise) {
        totalPaise += paise;
        return totalPaise;
    }
}
