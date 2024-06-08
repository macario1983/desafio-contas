package br.com.contas.infrastructure.adapters.output.persitence;

import br.com.contas.application.ports.output.PayableAccountOutputPort;
import br.com.contas.domain.exception.PayableAccountNotFoundException;
import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
import br.com.contas.infrastructure.adapters.output.persitence.mapper.PayableAccountMapper;
import br.com.contas.infrastructure.adapters.output.persitence.repository.PayableAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Transactional
public class PayableAccountPersistenceAdapter implements PayableAccountOutputPort {

    private final Logger logger = LoggerFactory.getLogger(PayableAccountPersistenceAdapter.class);

    private final PayableAccountMapper mapper;

    private final PayableAccountRepository repository;

    public PayableAccountPersistenceAdapter(PayableAccountMapper mapper, PayableAccountRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public PayableAccount createPayableAccount(PayableAccount payableAccount) {

        this.logger.info("Iniciando a persistência do payable account: {}", payableAccount);
        PayableAccountEntity payableAccountEntity = mapper.toEntity(payableAccount);
        PayableAccountEntity payableAccountEntitySaved = this.repository.save(payableAccountEntity);
        this.logger.info("Payable account persistido com sucesso: {}", payableAccountEntitySaved);

        return this.mapper.toModel(payableAccountEntitySaved);
    }

    @Override
    public PayableAccount updatePayableAccount(UUID id, PayableAccount payableAccount) {

        this.logger.info("Iniciando a alteração do payable account: {}", payableAccount);
        PayableAccountEntity payableAccountEntityFound = this.repository.findById(id).orElseThrow(PayableAccountNotFoundException::new);
        this.mapper.toEntityExceptyId(payableAccount, payableAccountEntityFound);
        PayableAccountEntity payableAccountEntitySaved = this.repository.save(payableAccountEntityFound);
        this.logger.info("Payable account alterado com sucesso: {}", payableAccountEntitySaved);

        return this.mapper.toModel(payableAccountEntitySaved);

    }

    @Override
    public PayableAccount updatePayableAccountStatus(UUID id) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public PayableAccount getPayableAccount(UUID id) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PayableAccount> getPayableAccounts() {
        return List.of();
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getPayableAccountsAmountByPeriod() {
        return null;
    }
}
