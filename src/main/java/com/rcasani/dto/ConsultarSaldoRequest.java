package com.rcasani.dto;

import java.math.BigDecimal;

public record ConsultarSaldoRequest(String cuentaId, BigDecimal cantidadRequerida) {
}
