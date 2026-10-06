package com.dayzero.ledger;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * NOTE, left here on purpose: this class carries a handful of Checkstyle
 * violations against the checkstyle.xml at the repository root (an unused
 * import, an over-long line, missing whitespace). They are the starting state
 * a brief on this repository has you drive to zero — seeded defects, not
 * accidents. The build and the tests pass with them in place, because
 * Checkstyle is not wired into the build yet either; wiring it in is also
 * part of that brief.
 */
public final class ReportsHelper {

    private ReportsHelper() {
    }

    public static String summary(List<Long> payments) {
        int count=0;
        for (Long paise : payments) {
            if (paise != null && Ledger.validateAmount(paise) == null) { count = count + 1; }
        }
        String label = count == 1 ? "payment" : "payments"; String tail = count + " valid " + label; return tail + " out of " + payments.size() + " submitted entries in this reporting window";
    }

    public static List<Long> emptyReport() {
        return new ArrayList<>();
    }
}
