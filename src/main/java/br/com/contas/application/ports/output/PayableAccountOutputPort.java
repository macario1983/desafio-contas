package br.com.contas.application.ports.output;

import br.com.contas.domain.model.PayableAccount;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface PayableAccountOutputPort {

    PayableAccount createPayableAccount(PayableAccount payableAccount);

    PayableAccount updatePayableAccount(UUID id, PayableAccount payableAccount);

    PayableAccount updatePayableAccountStatus(UUID id, Map<String, Object> fields);

    PageImpl<PayableAccount> getPayableAccountsFiltered(int page, int size, LocalDate dueDate, String description);

    PayableAccount getPayableAccountById(UUID id);

    BigDecimal getPayableAccountsAmountByPeriod(LocalDate startDate, LocalDate endDate);

    void importCSV(MultipartFile file);

}
