package br.com.desafio.payableaccount.core.mapstruct;

import br.com.desafio.payableaccount.api.v1.model.input.PayableAccountInput;
import br.com.desafio.payableaccount.api.v1.model.view.PayableAccountView;
import br.com.desafio.payableaccount.domain.model.PayableAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayableAccountMapper {

    PayableAccount toModel(PayableAccountInput payableAccountInput);

    PayableAccountView toResponse(PayableAccount payableAccountSaved);

    default void toModelExceptyId(PayableAccountInput payableAccountInput, PayableAccount payableAccountFound) {
        payableAccountFound.setDueDate(payableAccountInput.dueDate());
        payableAccountFound.setPaymentDate(payableAccountInput.paymentDate());
        payableAccountFound.setAmount(payableAccountInput.amount());
        payableAccountFound.setDescription(payableAccountInput.description());
        payableAccountFound.setStatus(payableAccountInput.status());
    }

}
