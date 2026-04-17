package com.rcasani.controller;

import com.rcasani.chaos.ChaosConfig;
import com.rcasani.chaos.ChaosState;
import com.rcasani.chaos.ChaosType;
import com.rcasani.dto.CobroResponse;
import com.rcasani.dto.CobroResquest;
import com.rcasani.dto.ConsultarSaldoRequest;
import com.rcasani.dto.ConsultarSaldoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cuenta")
public class MasteCardController {

    private final ChaosState chaosState = new ChaosState();

    private void applyChaos() throws InterruptedException {
        ChaosConfig chaos = chaosState.getConfig();

        if (!chaos.enabled() || chaos.type() == ChaosType.NONE) {
            return;
        }

        if (chaos.type() == ChaosType.DELAY) {
            Thread.sleep(chaos.delayMs());
        }

        if (chaos.type() == ChaosType.ERROR) {
            throw new RuntimeException("Chaos: forced error");
        }

        if (chaos.type() == ChaosType.ERROR_N_TIMES && chaosState.shouldFail()) {
            throw new RuntimeException("Chaos: temporary error");
        }
    }

    @PostMapping("/consultar-saldo")
    public ResponseEntity<ConsultarSaldoResponse> checkBalance(@RequestBody ConsultarSaldoRequest request) throws InterruptedException {

        applyChaos();

        BigDecimal balance = BigDecimal.valueOf(500);
        boolean sufficient = balance.compareTo(request.cantidadRequerida()) >= 0;

        return ResponseEntity.ok(
                new ConsultarSaldoResponse(request.cuentaId(), balance, sufficient)
        );
    }

    @PostMapping("/cobro")
    public ResponseEntity<CobroResponse> charge(@RequestBody CobroResquest request) throws InterruptedException {

        applyChaos();

        if (request.cantidad().compareTo(BigDecimal.valueOf(500)) > 0) {
            return ResponseEntity.badRequest()
                    .body(new CobroResponse(request.cuentaId(), request.cantidad(), "INSUFFICIENT_FUNDS"));
        }

        return ResponseEntity.ok(
                new CobroResponse(request.cuentaId(), request.cantidad(), "SUCCESS")
        );
    }
}
