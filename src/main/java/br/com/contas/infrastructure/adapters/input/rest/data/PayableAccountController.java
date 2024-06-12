package br.com.contas.infrastructure.adapters.input.rest.data;

import br.com.contas.application.ports.input.CreatePayableAccountUseCase;
import br.com.contas.application.ports.input.GetPayableAccountUseCase;
import br.com.contas.application.ports.input.UpdatePayableAccountUseCase;
import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.input.rest.data.request.PayableAccountRequest;
import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import br.com.contas.infrastructure.adapters.output.persitence.mapper.PayableAccountMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PayableAccountController(CreatePayableAccountUseCase createPayableAccountUseCase,
                                    GetPayableAccountUseCase getPayableAccountUseCase,
                                    UpdatePayableAccountUseCase updatePayableAccountUseCase,
                                    PayableAccountMapper mapper) {
        this.createPayableAccountUseCase = createPayableAccountUseCase;
        this.getPayableAccountUseCase = getPayableAccountUseCase;
        this.updatePayableAccountUseCase = updatePayableAccountUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PayableAccountResponse> createPayableAccount(@RequestBody @Valid PayableAccountRequest payableAccountRequest) {
        this.logger.info("Iniciando a requisição na camada REST para inserir o payable account: {}", payableAccountRequest);
        try {
            PayableAccount payableAccount = this.mapper.toModel(payableAccountRequest);
            PayableAccountResponse payableAccountSaved = this.createPayableAccountUseCase.createPayableAccount(payableAccount);
            this.logger.info("Terminando a requisição na camada REST para inserir o payable account: {}", payableAccountRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(payableAccountSaved);
        } catch (Exception ex) {
            this.logger.error("Erro ao inserir o payable account: {}", payableAccountRequest, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayableAccountResponse> updatePayableAccount(@PathVariable UUID id, @RequestBody @Valid PayableAccountRequest payableAccountRequest) {
        this.logger.info("Iniciando a requisição na camada REST para atualizar o payable account: {}", payableAccountRequest);
        try {
            PayableAccount payableAccount = this.mapper.toModel(payableAccountRequest);
            PayableAccountResponse payableAccountUpdated = this.createPayableAccountUseCase.updatePayableAccount(id, payableAccount);
            this.logger.info("Terminando a requisição na camada REST para atualizar o payable account: {}", payableAccountRequest);
            return ResponseEntity.status(HttpStatus.OK).body(payableAccountUpdated);
        } catch (Exception ex) {
            this.logger.error("Erro ao atualizar o payable account: {}", payableAccountRequest, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PayableAccountResponse> updatePayableAccountStatus(@PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        this.logger.info("Iniciando a requisição na camada REST para atualizar o status da conta a pagar com o ID: {}", id);
        try {
            PayableAccountResponse payableAccountSaved = this.updatePayableAccountUseCase.updatePayableAccountStatus(id, fields);
            this.logger.info("Requisição na camada REST para atualizar o status da conta a pagar com o ID {} concluída", id);
            return ResponseEntity.status(HttpStatus.OK).body(payableAccountSaved);
        } catch (Exception ex) {
            this.logger.error("Erro ao atualizar o status da conta a pagar com o ID: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayableAccountResponse> getPayableAccountById(@PathVariable UUID id) {
        this.logger.info("Iniciando a requisição na camada REST para obter a conta a pagar com o ID: {}", id);
        try {
            PayableAccountResponse payableAccountFound = this.getPayableAccountUseCase.getPayableAccountById(id);
            this.logger.info("Requisição na camada REST para obter a conta a pagar com o ID {} concluída", id);
            return ResponseEntity.status(HttpStatus.OK).body(payableAccountFound);
        } catch (Exception ex) {
            this.logger.error("Erro ao obter a conta a pagar com o ID: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Page<PayableAccountResponse>> getPayableAccountsFiltered(Pageable pageable,
                                                                                   @RequestParam(required = false) LocalDate dueDate,
                                                                                   @RequestParam(required = false) String description) {
        this.logger.info("Iniciando a requisição na camada REST para obter as contas a pagar. Parâmetros: dueDate={}, description={}", dueDate, description);
        try {
            Page<PayableAccountResponse> payableAccountsPageountFound = this.getPayableAccountUseCase.getPayableAccountsFiltered(pageable, dueDate, description);
            this.logger.info("Requisição na camada REST para obter as contas a pagar concluída");
            return ResponseEntity.status(HttpStatus.OK).body(payableAccountsPageountFound);
        } catch (Exception ex) {
            this.logger.error("Erro ao obter as contas a pagar. Parâmetros: dueDate={}, description={}", dueDate, description, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/total-paid")
    public ResponseEntity<BigDecimal> getPayableAccountsAmountByPeriod(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        this.logger.info("Iniciando a requisição na camada REST para obter o total pago das contas a pagar por período. Parâmetros: startDate={}, endDate={}", startDate, endDate);
        try {
            BigDecimal amount = this.getPayableAccountUseCase.getPayableAccountsAmountByPeriod(startDate, endDate);
            this.logger.info("Requisição na camada REST para obter o total pago das contas a pagar por período concluída");
            return ResponseEntity.status(HttpStatus.OK).body(amount);
        } catch (Exception ex) {
            this.logger.error("Erro ao obter o total pago das contas a pagar por período. Parâmetros: startDate={}, endDate={}", startDate, endDate, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/import")
    public ResponseEntity<String> importCSV(@RequestParam("file") MultipartFile file) {
        this.logger.info("Iniciando a requisição na camada REST para importar contas a pagar a partir de um arquivo CSV.");
        try {
            this.createPayableAccountUseCase.importCSV(file);
            this.logger.info("Importação de contas a pagar a partir de um arquivo CSV concluída com sucesso.");
            return ResponseEntity.ok("Arquivo importado com sucesso");
        } catch (Exception ex) {
            this.logger.error("Erro ao importar contas a pagar a partir de um arquivo CSV.", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao importar o arquivo: " + ex.getMessage());
        }
    }
}
