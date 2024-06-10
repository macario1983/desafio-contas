package br.com.contas.application.ports.input;

import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface GetPayableAccountUseCase {

    PayableAccountResponse getPayableAccountById(UUID id);

    PageImpl<PayableAccountResponse> getPayableAccountsFiltered(int page, int size, LocalDate dueDate, String description);

    BigDecimal getPayableAccountsAmountByPeriod(LocalDate startDate, LocalDate endDate);

}
