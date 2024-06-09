package br.com.contas.application.ports.input;

import br.com.contas.domain.model.PayableAccount;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface GetPayableAccountUseCase {

    PayableAccount getPayableAccountById(UUID id);

    PageImpl<PayableAccount> getPayableAccountsFiltered(int page, int size, LocalDate dueDate, String description);

    BigDecimal getPayableAccountsAmountByPeriod(LocalDate startDate, LocalDate endDate);

}
