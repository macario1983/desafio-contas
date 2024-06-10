package br.com.contas.domain.service;

import br.com.contas.application.ports.input.CreatePayableAccountUseCase;
import br.com.contas.application.ports.input.GetPayableAccountUseCase;
import br.com.contas.application.ports.input.UpdatePayableAccountUseCase;
import br.com.contas.application.ports.output.PayableAccountOutputPort;
import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class PayableAccountService implements CreatePayableAccountUseCase, GetPayableAccountUseCase, UpdatePayableAccountUseCase {

    private final PayableAccountOutputPort payableAccountOutputPort;

    public PayableAccountService(PayableAccountOutputPort payableAccountOutputPort) {
        this.payableAccountOutputPort = payableAccountOutputPort;
    }

    @Override
    public PayableAccountResponse createPayableAccount(PayableAccount payableAccount) {
        return this.payableAccountOutputPort.createPayableAccount(payableAccount);
    }

    @Override
    public PayableAccountResponse updatePayableAccount(UUID id, PayableAccount payableAccount) {
        return this.payableAccountOutputPort.updatePayableAccount(id, payableAccount);
    }

    @Override
    public PayableAccountResponse getPayableAccountById(UUID id) {
        return this.payableAccountOutputPort.getPayableAccountById(id);
    }

    @Override
    public PageImpl<PayableAccountResponse> getPayableAccountsFiltered(int page, int size, LocalDate dueDate, String description) {
        return this.payableAccountOutputPort.getPayableAccountsFiltered(page, size, dueDate, description);
    }

    @Override
    public BigDecimal getPayableAccountsAmountByPeriod(LocalDate startDate, LocalDate endDate) {
        return this.payableAccountOutputPort.getPayableAccountsAmountByPeriod(startDate, endDate);
    }

    @Override
    public PayableAccountResponse updatePayableAccountStatus(UUID id, Map<String, Object> fields) {
        return this.payableAccountOutputPort.updatePayableAccountStatus(id, fields);
    }

    @Override
    public void importCSV(MultipartFile file) {
        this.payableAccountOutputPort.importCSV(file);
    }

}
