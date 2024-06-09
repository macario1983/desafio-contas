package br.com.contas.application.ports.input;

import br.com.contas.domain.model.PayableAccount;

import java.util.Map;
import java.util.UUID;

public interface UpdatePayableAccountUseCase {

    PayableAccount updatePayableAccountStatus(UUID id, Map<String, Object> fields);

}
