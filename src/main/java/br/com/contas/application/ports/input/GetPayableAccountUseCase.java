package br.com.contas.application.ports.input;

import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface GetPayableAccountUseCase {

    PayableAccountResponse getPayableAccountById(UUID id);

    BigDecimal getPayableAccountsAmountByPeriod(LocalDate startDate, LocalDate endDate);

    Page<PayableAccountResponse> getPayableAccountsFiltered(Pageable pageable, LocalDate dueDate, String description);

}
