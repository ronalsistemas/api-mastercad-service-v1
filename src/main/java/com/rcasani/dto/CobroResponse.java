package com.rcasani.dto;

import java.math.BigDecimal;

public record CobroResponse(String cuentaId, BigDecimal cantidad, String estado) {
}
