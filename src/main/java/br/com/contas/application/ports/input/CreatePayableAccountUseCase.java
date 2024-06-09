package br.com.contas.application.ports.input;

import br.com.contas.domain.model.PayableAccount;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CreatePayableAccountUseCase {

    PayableAccount createPayableAccount(PayableAccount payableAccount);

    PayableAccount updatePayableAccount(UUID id, PayableAccount payableAccount);

    void importCSV(MultipartFile file);

}
