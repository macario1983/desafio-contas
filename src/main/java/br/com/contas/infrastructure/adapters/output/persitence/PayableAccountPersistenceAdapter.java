package br.com.contas.infrastructure.adapters.output.persitence;

import br.com.contas.application.ports.output.PayableAccountOutputPort;
import br.com.contas.domain.exception.PayableAccountNotFoundException;
import br.com.contas.domain.model.PayableAccount;
import br.com.contas.domain.model.PayableAccountStatus;
import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
import br.com.contas.infrastructure.adapters.output.persitence.exception.FieldStatusNotFoundException;
import br.com.contas.infrastructure.adapters.output.persitence.exception.PayableAccountStatusNotFoundException;
import br.com.contas.infrastructure.adapters.output.persitence.mapper.PayableAccountMapper;
import br.com.contas.infrastructure.adapters.output.persitence.repository.PayableAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    public PayableAccount updatePayableAccountStatus(UUID id, Map<String, Object> fields) {

        this.logger.info("Iniciando a alteração do status do payable account com o ID: {}", id);
        Optional.ofNullable(fields.get("status")).orElseThrow(() -> new FieldStatusNotFoundException("status"));
        PayableAccountStatus statusConverted = Arrays.stream(PayableAccountStatus.values())
                .filter(payableAccountStatus -> payableAccountStatus.toString().equals(fields.get("status")))
                .findFirst()
                .orElseThrow(() -> new PayableAccountStatusNotFoundException(fields.get("status").toString()));
        this.repository.updateStatusById(id, statusConverted);
        PayableAccountEntity payableAccountEntity = this.repository.findById(id).orElseThrow(PayableAccountNotFoundException::new);
        this.logger.info("Alteração do status do payable account com o ID {} concluída", id);

        return this.mapper.toModel(payableAccountEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public PageImpl<PayableAccount> getPayableAccountsFiltered(int page, int size, LocalDate dueDate, String description) {

        this.logger.info("Iniciando a busca por contas a pagar com filtros. Parâmetros: page={}, size={}, dueDate={}, description={}", page, size, dueDate, description);
        Pageable pageable = PageRequest.of(page, size);
        this.logger.info("Busca por contas a pagar com filtros concluída");
        Page<PayableAccountEntity> payableAccountEntitiesPaginated = this.repository.findByDueDateOrDescriptionContainingIgnoreCase(pageable, dueDate, description);
        List<PayableAccount> payableAccounts = payableAccountEntitiesPaginated.map(mapper::toModel).stream().toList();

        return new PageImpl<>(payableAccounts, PageRequest.of(page, size), payableAccountEntitiesPaginated.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public PayableAccount getPayableAccountById(UUID id) {

        this.logger.info("Iniciando a busca por uma conta a pagar com o ID: {}", id);
        PayableAccountEntity payableAccountEntityFound = this.repository.findById(id).orElseThrow(PayableAccountNotFoundException::new);
        PayableAccount payableAccount = this.mapper.toModel(payableAccountEntityFound);
        this.logger.info("Busca por uma conta a pagar com o ID {} concluída", id);

        return payableAccount;
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getPayableAccountsAmountByPeriod(LocalDate startDate, LocalDate endDate) {

        this.logger.info("Iniciando a busca por valor total de contas pagas no período de {} a {}", startDate, endDate);
        this.logger.info("Busca por valor total de contas pagas no período de {} a {} concluída", startDate, endDate);

        return this.repository.findByDueDateBetween(startDate, endDate);
    }

    @Override
    public void importCSV(MultipartFile file) {

        StringBuilder stringBuilder = readStream(file);

        String[] contas = stringBuilder.toString().split("\n");

        List<PayableAccountEntity> payableAccountEntitys = Arrays.stream(contas).map(s -> {
            String[] contaArray = s.split(",");
            return buildPayableAccountEntity(contaArray);
        }).collect(Collectors.toList());

        this.repository.saveAll(payableAccountEntitys);
    }

    private StringBuilder readStream(MultipartFile file) {

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (Objects.nonNull((line = bufferedReader.readLine()))) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringBuilder;
    }

    private PayableAccountEntity buildPayableAccountEntity(String[] contaArray) {

        PayableAccountEntity payableAccountEntity = new PayableAccountEntity();

        payableAccountEntity.setDueDate(convertStringToLocalDate(contaArray[0]));
        payableAccountEntity.setPaymentDate(convertStringToLocalDate(contaArray[1]));
        payableAccountEntity.setAmount(new BigDecimal(contaArray[2]));
        payableAccountEntity.setDescription(contaArray[3]);
        payableAccountEntity.setStatus(PayableAccountStatus.valueOf(contaArray[4]));

        return payableAccountEntity;
    }

    private LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

}
