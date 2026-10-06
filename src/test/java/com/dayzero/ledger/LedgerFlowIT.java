package com.dayzero.ledger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

/**
 * The integration suite. It boots the whole application and it is
 * DELIBERATELY SLOW: each test simulates the settling wait a real
 * downstream dependency imposes. The slowness is the point — one of the
 * briefs on this repository is about getting it out of everyone's way.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LedgerFlowIT {

    private static final long SIMULATED_SETTLING_MILLIS = 8_000;

    @Autowired
    private TestRestTemplate rest;

    private static void waitForSettlement() throws InterruptedException {
        Thread.sleep(SIMULATED_SETTLING_MILLIS);
    }

    @Test
    void healthzAnswersOk() throws InterruptedException {
        waitForSettlement();
        ResponseEntity<Map> res = rest.getForEntity("/healthz", Map.class);
        assertEquals(200, res.getStatusCode().value());
        assertEquals("ok", res.getBody().get("status"));
    }

    @Test
    void paymentsRecordsARunningTotal() throws InterruptedException {
        waitForSettlement();
        ResponseEntity<Map> res = rest.postForEntity("/payments", Map.of("amount_paise", 500), Map.class);
        assertEquals(200, res.getStatusCode().value());
        assertTrue(((Number) res.getBody().get("total_paise")).longValue() >= 500);
    }

    @Test
    void paymentsRefusesABadAmount() throws InterruptedException {
        waitForSettlement();
        ResponseEntity<Map> res = rest.postForEntity("/payments", Map.of("amount_paise", 0), Map.class);
        assertEquals(400, res.getStatusCode().value());
    }
}
