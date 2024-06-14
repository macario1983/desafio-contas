package br.com.desafio.payableaccount.api.v1.controller;

import br.com.desafio.payableaccount.api.v1.model.input.PayableAccountInput;
import br.com.desafio.payableaccount.api.v1.model.view.PayableAccountView;
import br.com.desafio.payableaccount.domain.service.PayableAccountService;
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

    private final PayableAccountService service;

    public PayableAccountController(PayableAccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PayableAccountView> createPayableAccount(@RequestBody @Valid PayableAccountInput payableAccountInput) {
        this.logger.info("Iniciando a requisição na camada REST para inserir o payable account: {}", payableAccountInput);
        PayableAccountView payableAccountSaved = this.service.createPayableAccount(payableAccountInput);
        this.logger.info("Terminando a requisição na camada REST para inserir o payable account: {}", payableAccountInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(payableAccountSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayableAccountView> updatePayableAccount(@PathVariable UUID id, @RequestBody @Valid PayableAccountInput payableAccountInput) {
        this.logger.info("Iniciando a requisição na camada REST para atualizar o payable account: {}", payableAccountInput);
        PayableAccountView payableAccountUpdated = this.service.updatePayableAccount(id, payableAccountInput);
        this.logger.info("Terminando a requisição na camada REST para atualizar o payable account: {}", payableAccountInput);
        return ResponseEntity.status(HttpStatus.OK).body(payableAccountUpdated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PayableAccountView> updatePayableAccountStatus(@PathVariable UUID id, @RequestBody Map<String, Object> fields) {
        this.logger.info("Iniciando a requisição na camada REST para atualizar o status da conta a pagar com o ID: {}", id);
        PayableAccountView payableAccountSaved = this.service.updatePayableAccountStatus(id, fields);
        this.logger.info("Requisição na camada REST para atualizar o status da conta a pagar com o ID {} concluída", id);
        return ResponseEntity.status(HttpStatus.OK).body(payableAccountSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayableAccountView> getPayableAccountById(@PathVariable UUID id) {
        this.logger.info("Iniciando a requisição na camada REST para obter a conta a pagar com o ID: {}", id);
        PayableAccountView payableAccountFound = this.service.getPayableAccountById(id);
        this.logger.info("Requisição na camada REST para obter a conta a pagar com o ID {} concluída", id);
        return ResponseEntity.status(HttpStatus.OK).body(payableAccountFound);
    }

    @GetMapping
    public ResponseEntity<Page<PayableAccountView>> getPayableAccountsFiltered(Pageable pageable, @RequestParam(required = false) LocalDate dueDate, @RequestParam(required = false) String description) {
        this.logger.info("Iniciando a requisição na camada REST para obter as contas a pagar. Parâmetros: dueDate={}, description={}", dueDate, description);
        Page<PayableAccountView> payableAccountsPageountFound = this.service.getPayableAccountsFiltered(pageable, dueDate, description);
        this.logger.info("Requisição na camada REST para obter as contas a pagar concluída");
        return ResponseEntity.status(HttpStatus.OK).body(payableAccountsPageountFound);
    }

    @GetMapping("/total-paid")
    public ResponseEntity<BigDecimal> getPayableAccountsAmountByPeriod(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        this.logger.info("Iniciando a requisição na camada REST para obter o total pago das contas a pagar por período. Parâmetros: startDate={}, endDate={}", startDate, endDate);
        BigDecimal amount = this.service.getPayableAccountsAmountByPeriod(startDate, endDate);
        this.logger.info("Requisição na camada REST para obter o total pago das contas a pagar por período concluída");
        return ResponseEntity.status(HttpStatus.OK).body(amount);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importCSV(@RequestParam("file") MultipartFile file) {
        this.logger.info("Iniciando a requisição na camada REST para importar contas a pagar a partir de um arquivo CSV.");
        this.service.importCSV(file);
        this.logger.info("Importação de contas a pagar a partir de um arquivo CSV concluída com sucesso.");
        return ResponseEntity.ok("Arquivo importado com sucesso");
    }
}
