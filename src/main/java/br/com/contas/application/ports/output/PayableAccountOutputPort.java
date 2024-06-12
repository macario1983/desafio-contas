package br.com.contas.application.ports.output;

import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface PayableAccountOutputPort {

    PayableAccountResponse createPayableAccount(PayableAccount payableAccount);

    PayableAccountResponse updatePayableAccount(UUID id, PayableAccount payableAccount);

    PayableAccountResponse updatePayableAccountStatus(UUID id, Map<String, Object> fields);

    Page<PayableAccountResponse> getPayableAccountsFiltered(Pageable pageable, LocalDate dueDate, String description);

    PayableAccountResponse getPayableAccountById(UUID id);

    BigDecimal getPayableAccountsAmountByPeriod(LocalDate startDate, LocalDate endDate);

    void importCSV(MultipartFile file);

}
