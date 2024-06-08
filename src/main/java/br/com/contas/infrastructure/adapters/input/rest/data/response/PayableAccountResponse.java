package br.com.contas.infrastructure.adapters.input.rest.data.response;

import br.com.contas.domain.model.PayableAccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PayableAccountResponse(UUID id,
                                     LocalDate dueDate,
                                     LocalDate paymentDate,
                                     BigDecimal amount,
                                     String description,
                                     PayableAccountStatus status) {
}
