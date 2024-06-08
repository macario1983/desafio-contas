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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public void updatePayableAccountStatus(@PathVariable UUID id) {

    }

    @GetMapping("/{id}")
    public void getPayableAccount(@PathVariable UUID id) {

    }

    @GetMapping
    public void getPayableAccounts(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(required = false) LocalDate dueDate,
                                   @RequestParam(required = false) String description) {

    }

    @GetMapping("/total-paid")
    public void getPayableAccountsAmountByPeriod(@RequestParam LocalDate startDate,
                                                 @RequestParam LocalDate endDate) {

    }

}
