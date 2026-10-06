package com.dayzero.ledger;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController {

    private final Ledger ledger;

    public PaymentsController(Ledger ledger) {
        this.ledger = ledger;
    }

    @PostMapping("/payments")
    public ResponseEntity<Map<String, Object>> record(@RequestBody Map<String, Object> body) {
        Object raw = body.get("amount_paise");
        if (!(raw instanceof Number)) {
            return ResponseEntity.badRequest().body(Map.of("error", "amount_paise must be a number"));
        }
        long paise = ((Number) raw).longValue();
        String problem = Ledger.validateAmount(paise);
        if (problem != null) {
            return ResponseEntity.badRequest().body(Map.of("error", problem));
        }
        return ResponseEntity.ok(Map.of("total_paise", ledger.record(paise)));
    }
}
