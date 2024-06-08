package br.com.contas.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PayableAccount(UUID id,
                             LocalDate dueDate,
                             LocalDate paymentDate,
                             BigDecimal amount,
                             String description,
                             PayableAccountStatus status) {
}
