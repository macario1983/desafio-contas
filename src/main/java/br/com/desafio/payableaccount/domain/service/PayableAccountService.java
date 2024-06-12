package br.com.desafio.payableaccount.domain.service;

import br.com.desafio.payableaccount.api.v1.model.input.PayableAccountInput;
import br.com.desafio.payableaccount.api.v1.model.view.PayableAccountView;
import br.com.desafio.payableaccount.core.mapstruct.PayableAccountMapper;
import br.com.desafio.payableaccount.domain.exception.FieldStatusNotFoundException;
import br.com.desafio.payableaccount.domain.exception.PayableAccountNotFoundException;
import br.com.desafio.payableaccount.domain.exception.PayableAccountStatusNotFoundException;
import br.com.desafio.payableaccount.domain.model.PayableAccount;
import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;
import br.com.desafio.payableaccount.domain.repository.PayableAccountRepository;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
@Service
public class PayableAccountService {

    private final Logger logger = LoggerFactory.getLogger(PayableAccountService.class);

    private final PayableAccountMapper mapper;

    private final PayableAccountRepository repository;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public PayableAccountService(PayableAccountMapper mapper, PayableAccountRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public PayableAccountView createPayableAccount(@Valid PayableAccountInput payableAccountInput) {

        this.logger.info("Iniciando a persistência do payable account: {}", payableAccountInput);
        try {
            PayableAccount payableAccount = mapper.toModel(payableAccountInput);
            Set<ConstraintViolation<PayableAccount>> violations = validator.validate(payableAccount);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            PayableAccount payableAccountSaved = this.repository.save(payableAccount);
            this.logger.info("Payable account persistido com sucesso: {}", payableAccountSaved);
            return this.mapper.toResponse(payableAccountSaved);
        } catch (Exception ex) {
            this.logger.error("Erro ao persistir o payable account: {}", payableAccountInput, ex);
            throw ex;
        }
    }

    public PayableAccountView updatePayableAccount(UUID id, PayableAccountInput payableAccountInput) {
        this.logger.info("Iniciando a alteração do payable account: {}", payableAccountInput);
        try {
            PayableAccount payableAccountFound = this.repository.findById(id)
                    .orElseThrow(PayableAccountNotFoundException::new);
            this.mapper.toModelExceptyId(payableAccountInput, payableAccountFound);
            Set<ConstraintViolation<PayableAccount>> violations = validator.validate(payableAccountFound);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            PayableAccount payableAccountSaved = this.repository.save(payableAccountFound);
            this.logger.info("Payable account alterado com sucesso: {}", payableAccountSaved);
            return this.mapper.toResponse(payableAccountSaved);
        } catch (Exception ex) {
            this.logger.error("Erro ao alterar o payable account: {}", payableAccountInput, ex);
            throw ex;
        }
    }

    public PayableAccountView updatePayableAccountStatus(UUID id, Map<String, Object> fields) {
        this.logger.info("Iniciando a alteração do status do payable account com o ID: {}", id);
        try {
            Optional.ofNullable(fields.get("status"))
                    .orElseThrow(() -> new FieldStatusNotFoundException("status"));
            PayableAccountStatus statusConverted = Arrays.stream(PayableAccountStatus.values())
                    .filter(payableAccountStatus -> payableAccountStatus.toString().equals(fields.get("status")))
                    .findFirst()
                    .orElseThrow(() -> new PayableAccountStatusNotFoundException(fields.get("status").toString()));
            this.repository.updateStatusById(id, statusConverted);
            PayableAccount payableAccount = this.repository.findById(id)
                    .orElseThrow(PayableAccountNotFoundException::new);
            this.logger.info("Alteração do status do payable account com o ID {} concluída", id);
            return this.mapper.toResponse(payableAccount);
        } catch (Exception ex) {
            this.logger.error("Erro ao alterar o status do payable account com o ID: {}", id, ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public  Page<PayableAccountView> getPayableAccountsFiltered(Pageable pageable, LocalDate dueDate, String description) {
        this.logger.info("Iniciando a busca por contas a pagar com filtros. Parâmetros: page={}, size={}, dueDate={}, description={}", pageable.getPageNumber(), pageable.getPageSize(), dueDate, description);
        try {
            Page<PayableAccount> payableAccountEntitiesPaginated = this.repository.findByDueDateOrDescriptionContainingIgnoreCase(pageable, dueDate, description);
            this.logger.info("Busca por contas a pagar com filtros concluída");
            return payableAccountEntitiesPaginated.map(mapper::toResponse);
        } catch (Exception ex) {
            this.logger.error("Erro ao buscar contas a pagar com filtros. Parâmetros: page={}, size={}, dueDate={}, description={}", pageable.getPageNumber(), pageable.getPageSize(), dueDate, description, ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public PayableAccountView getPayableAccountById(UUID id) {
        this.logger.info("Iniciando a busca por uma conta a pagar com o ID: {}", id);
        try {
            PayableAccount payableAccountFound = this.repository.findById(id)
                    .orElseThrow(PayableAccountNotFoundException::new);
            PayableAccountView payableAccount = this.mapper.toResponse(payableAccountFound);
            this.logger.info("Busca por uma conta a pagar com o ID {} concluída", id);
            return payableAccount;
        } catch (Exception ex) {
            this.logger.error("Erro ao buscar conta a pagar com o ID: {}", id, ex);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal getPayableAccountsAmountByPeriod(LocalDate startDate, LocalDate endDate) {
        this.logger.info("Iniciando a busca por valor total de contas pagas no período de {} a {}", startDate, endDate);
        try {
            BigDecimal totalAmount = this.repository.findByDueDateBetween(startDate, endDate);
            this.logger.info("Busca por valor total de contas pagas no período de {} a {} concluída", startDate, endDate);
            return totalAmount;
        } catch (Exception ex) {
            this.logger.error("Erro ao buscar valor total de contas pagas no período de {} a {}", startDate, endDate, ex);
            throw ex;
        }
    }

    public void importCSV(MultipartFile file) {
        this.logger.info("Iniciando a importação de contas a pagar a partir de um arquivo CSV.");
        try {
            StringBuilder stringBuilder = readStream(file);
            String[] contas = stringBuilder.toString().split("\n");
            List<PayableAccount> payableAccountEntities = Arrays.stream(contas)
                    .map(s -> {
                        String[] contaArray = s.split(",");
                        return buildPayableAccountEntity(contaArray);
                    }).collect(Collectors.toList());
            this.repository.saveAll(payableAccountEntities);
            this.logger.info("Importação de contas a pagar a partir de um arquivo CSV concluída com sucesso.");
        } catch (Exception ex) {
            this.logger.error("Erro ao importar contas a pagar a partir de um arquivo CSV.", ex);
            throw ex;
        }
    }

    private StringBuilder readStream(MultipartFile file) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (Objects.nonNull((line = bufferedReader.readLine()))) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            this.logger.error("Erro ao ler o arquivo CSV.", e);
            throw new RuntimeException(e);
        }
        return stringBuilder;
    }

    private PayableAccount buildPayableAccountEntity(String[] contaArray) {
        PayableAccount payableAccount = new PayableAccount();
        payableAccount.setDueDate(convertStringToLocalDate(contaArray[0]));
        payableAccount.setPaymentDate(convertStringToLocalDate(contaArray[1]));
        payableAccount.setAmount(new BigDecimal(contaArray[2]));
        payableAccount.setDescription(contaArray[3]);
        payableAccount.setStatus(PayableAccountStatus.valueOf(contaArray[4]));
        return payableAccount;
    }

    private LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
