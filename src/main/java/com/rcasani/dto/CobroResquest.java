package com.rcasani.dto;

import java.math.BigDecimal;

public record CobroResquest(String cuentaId, BigDecimal cantidad) {
}
