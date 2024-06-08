package br.com.contas.infrastructure.adapters.output.persitence.mapper;

import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.input.rest.data.request.PayableAccountRequest;
import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PayableAccountMapper {

    PayableAccount toModel(PayableAccountRequest payableAccountRequest);

    PayableAccount toModel(PayableAccountResponse payableAccountResponse);

    PayableAccount toModel(PayableAccountEntity payableAccountEntity);

    PayableAccountEntity toEntity(PayableAccount payableAccount);

    default void toEntityExceptyId(PayableAccount payableAccount, PayableAccountEntity payableAccountEntity) {

        payableAccountEntity.setDueDate(payableAccount.dueDate());
        payableAccountEntity.setPaymentDate(payableAccount.paymentDate());
        payableAccountEntity.setAmount(payableAccount.amount());
        payableAccountEntity.setDescription(payableAccount.description());
        payableAccountEntity.setStatus(payableAccount.status());
    }

}
