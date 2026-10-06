package com.dayzero.ledger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Fast unit tests: no Spring context, they run in milliseconds. */
class LedgerTest {

    @ParameterizedTest
    @ValueSource(longs = {1, 49_900, Ledger.MAX_AMOUNT_PAISE})
    void acceptsReasonableAmounts(long paise) {
        assertNull(Ledger.validateAmount(paise));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -500, Ledger.MAX_AMOUNT_PAISE + 1})
    void rejectsBadAmounts(long paise) {
        assertNotNull(Ledger.validateAmount(paise));
    }

    @Test
    void keepsARunningTotal() {
        Ledger ledger = new Ledger();
        assertEquals(100, ledger.record(100));
        assertEquals(350, ledger.record(250));
    }

    @Test
    void summarisesValidPayments() {
        String summary = ReportsHelper.summary(java.util.List.of(100L, 0L, 250L));
        assertEquals(true, summary.startsWith("2 valid payments"));
    }
}
