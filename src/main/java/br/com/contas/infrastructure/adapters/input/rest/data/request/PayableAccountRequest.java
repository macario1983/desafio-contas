package br.com.contas.infrastructure.adapters.input.rest.data.request;

import br.com.contas.domain.model.PayableAccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PayableAccountRequest(UUID id,
                                    @FutureOrPresent LocalDate dueDate,
                                    LocalDate paymentDate,
                                    @Positive BigDecimal amount,
                                    @NotBlank String description,
                                    @Enumerated(EnumType.STRING) @NotNull PayableAccountStatus status) {
}
