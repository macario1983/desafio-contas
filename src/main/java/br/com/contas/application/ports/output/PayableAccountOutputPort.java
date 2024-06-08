package br.com.contas.application.ports.output;

import br.com.contas.domain.model.PayableAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PayableAccountOutputPort {

    PayableAccount createPayableAccount(PayableAccount payableAccount);

    PayableAccount updatePayableAccount(UUID id, PayableAccount payableAccount);

    PayableAccount updatePayableAccountStatus(UUID id);

    PayableAccount getPayableAccount(UUID id);

    List<PayableAccount> getPayableAccounts();

    BigDecimal getPayableAccountsAmountByPeriod();

}
