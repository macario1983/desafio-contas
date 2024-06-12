package br.com.desafio.payableaccount.api.v1.model.input;

import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PayableAccountInput(UUID id,
                                  LocalDate dueDate,
                                  LocalDate paymentDate,
                                  @Positive BigDecimal amount,
                                  @NotBlank String description,
                                  @Enumerated(EnumType.STRING) @NotNull PayableAccountStatus status) {
}
