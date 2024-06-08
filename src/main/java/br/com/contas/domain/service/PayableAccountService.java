package br.com.contas.domain.service;

import br.com.contas.application.ports.input.CreatePayableAccountUseCase;
import br.com.contas.application.ports.input.GetPayableAccountUseCase;
import br.com.contas.application.ports.input.UpdatePayableAccountUseCase;
import br.com.contas.application.ports.output.PayableAccountOutputPort;
import br.com.contas.domain.model.PayableAccount;

import java.util.UUID;

public class PayableAccountService implements CreatePayableAccountUseCase, GetPayableAccountUseCase, UpdatePayableAccountUseCase {

    private final PayableAccountOutputPort payableAccountOutputPort;

    public PayableAccountService(PayableAccountOutputPort payableAccountOutputPort) {
        this.payableAccountOutputPort = payableAccountOutputPort;
    }

    @Override
    public PayableAccount createPayableAccount(PayableAccount payableAccount) {
        return this.payableAccountOutputPort.createPayableAccount(payableAccount);
    }

    @Override
    public PayableAccount updatePayableAccount(UUID id, PayableAccount payableAccount) {
        return this.payableAccountOutputPort.updatePayableAccount(id, payableAccount);
    }

}
