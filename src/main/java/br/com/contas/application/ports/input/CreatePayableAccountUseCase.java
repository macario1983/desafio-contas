package br.com.contas.application.ports.input;

import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CreatePayableAccountUseCase {

    PayableAccountResponse createPayableAccount(PayableAccount payableAccount);

    PayableAccountResponse updatePayableAccount(UUID id, PayableAccount payableAccount);

    void importCSV(MultipartFile file);

}
