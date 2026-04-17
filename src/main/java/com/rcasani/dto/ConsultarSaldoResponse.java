package com.rcasani.dto;

import java.math.BigDecimal;

public record ConsultarSaldoResponse(String cuentaId, BigDecimal balance, boolean suficiente) {
}
