package br.com.contas.infrastructure.adapters.input.rest.data;

import br.com.contas.application.ports.input.CreatePayableAccountUseCase;
import br.com.contas.application.ports.input.GetPayableAccountUseCase;
import br.com.contas.application.ports.input.UpdatePayableAccountUseCase;
import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.input.rest.data.request.PayableAccountRequest;
import br.com.contas.infrastructure.adapters.output.persitence.mapper.PayableAccountMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/payable-accounts/")
public class PayableAccountController {

    private final Logger logger = LoggerFactory.getLogger(PayableAccountController.class);

    private final CreatePayableAccountUseCase createPayableAccountUseCase;

    private final GetPayableAccountUseCase getPayableAccountUseCase;

    private final UpdatePayableAccountUseCase updatePayableAccountUseCase;

    private final PayableAccountMapper mapper;

    public PayableAccountController(CreatePayableAccountUseCase createPayableAccountUseCase, GetPayableAccountUseCase getPayableAccountUseCase, UpdatePayableAccountUseCase updatePayableAccountUseCase, PayableAccountMapper mapper) {
        this.createPayableAccountUseCase = createPayableAccountUseCase;
        this.getPayableAccountUseCase = getPayableAccountUseCase;
        this.updatePayableAccountUseCase = updatePayableAccountUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PayableAccount> createPayableAccount(@RequestBody @Valid PayableAccountRequest payableAccountRequest) {

        this.logger.info("Iniciando a requisição na camada REST para inserir o payable account: {}", payableAccountRequest);
        PayableAccount payableAccount = this.mapper.toModel(payableAccountRequest);
        PayableAccount payableAccountSaved = this.createPayableAccountUseCase.createPayableAccount(payableAccount);
        this.logger.info("Terminando a requisição na camada REST para inserir o payable account: {}", payableAccountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(payableAccountSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayableAccount> updatePayableAccount(@PathVariable UUID id, @RequestBody @Valid PayableAccountRequest payableAccountRequest) {

        this.logger.info("Iniciando a requisição na camada REST para atualizar o payable account: {}", payableAccountRequest);
        PayableAccount payableAccount = this.mapper.toModel(payableAccountRequest);
        PayableAccount payableAccountSaved = this.createPayableAccountUseCase.updatePayableAccount(id, payableAccount);
        this.logger.info("Terminando a requisição na camada REST para atualizar o payable account: {}", payableAccountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(payableAccountSaved);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PayableAccount> updatePayableAccountStatus(@PathVariable UUID id, @RequestBody Map<String, Object> fields) {

        this.logger.info("Iniciando a requisição na camada REST para atualizar o status da conta a pagar com o ID: {}", id);
        PayableAccount payableAccountSaved = this.updatePayableAccountUseCase.updatePayableAccountStatus(id, fields);
        this.logger.info("Requisição na camada REST para atualizar o status da conta a pagar com o ID {} concluída", id);

        return ResponseEntity.status(HttpStatus.OK).body(payableAccountSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayableAccount> getPayableAccountById(@PathVariable UUID id) {

        this.logger.info("Iniciando a requisição na camada REST para obter a conta a pagar com o ID: {}", id);
        PayableAccount payableAccountFound = this.getPayableAccountUseCase.getPayableAccountById(id);
        this.logger.info("Requisição na camada REST para obter a conta a pagar com o ID {} concluída", id);

        return ResponseEntity.status(HttpStatus.OK).body(payableAccountFound);
    }

    @GetMapping
    public ResponseEntity<PageImpl<PayableAccount>> getPayableAccountsFiltered(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) LocalDate dueDate, @RequestParam(required = false) String description) {

        this.logger.info("Iniciando a requisição na camada REST para obter as contas a pagar. Parâmetros: dueDate={}, description={}", dueDate, description);
        PageImpl<PayableAccount> payableAccountsPageountFound = this.getPayableAccountUseCase.getPayableAccountsFiltered(page, size, dueDate, description);
        this.logger.info("Requisição na camada REST para obter as contas a pagar concluída");
        return ResponseEntity.status(HttpStatus.OK).body(payableAccountsPageountFound);
    }

    @GetMapping("/total-paid")
    public ResponseEntity<BigDecimal> getPayableAccountsAmountByPeriod(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        this.logger.info("Iniciando a requisição na camada REST para obter o total pago das contas a pagar por período. Parâmetros: startDate={}, endDate={}", startDate, endDate);
        BigDecimal amount = this.getPayableAccountUseCase.getPayableAccountsAmountByPeriod(startDate, endDate);
        this.logger.info("Requisição na camada REST para obter o total pago das contas a pagar por período concluída");
        return ResponseEntity.status(HttpStatus.OK).body(amount);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importCSV(@RequestParam("file") MultipartFile file) {
        try {
            this.createPayableAccountUseCase.importCSV(file);
            return ResponseEntity.ok("Arquivo importado com successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error ao importar o arquivo: " + e.getMessage());
        }
    }
}