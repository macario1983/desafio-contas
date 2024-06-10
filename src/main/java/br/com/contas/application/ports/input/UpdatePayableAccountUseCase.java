package br.com.contas.application.ports.input;

import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;

import java.util.Map;
import java.util.UUID;

public interface UpdatePayableAccountUseCase {

    PayableAccountResponse updatePayableAccountStatus(UUID id, Map<String, Object> fields);

}
