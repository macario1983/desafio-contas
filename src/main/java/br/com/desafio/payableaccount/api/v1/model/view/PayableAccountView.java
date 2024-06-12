package br.com.desafio.payableaccount.api.v1.model.view;

import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PayableAccountView(UUID id,
                                 LocalDate dueDate,
                                 LocalDate paymentDate,
                                 BigDecimal amount,
                                 String description,
                                 PayableAccountStatus status) {
}
