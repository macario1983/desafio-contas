package br.com.contas.application.ports.input;

import br.com.contas.domain.model.PayableAccount;

import java.util.UUID;

public interface CreatePayableAccountUseCase {

    PayableAccount createPayableAccount(PayableAccount payableAccount);

    PayableAccount updatePayableAccount(UUID id, PayableAccount payableAccount);

}
