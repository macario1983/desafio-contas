package br.com.desafio.payableaccount.domain.service;

import br.com.desafio.payableaccount.api.v1.model.input.PayableAccountInput;
import br.com.desafio.payableaccount.api.v1.model.view.PayableAccountView;
import br.com.desafio.payableaccount.core.mapstruct.PayableAccountMapper;
import br.com.desafio.payableaccount.domain.builder.PayableAccountBuilder;
import br.com.desafio.payableaccount.domain.exception.PayableAccountNotFoundException;
import br.com.desafio.payableaccount.domain.model.PayableAccount;
import br.com.desafio.payableaccount.domain.repository.PayableAccountRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayableAccountServiceTest {

    @Mock
    private PayableAccountMapper mapper;

    @Mock
    private PayableAccountRepository repository;

    @InjectMocks
    private PayableAccountService adapter;

    private PayableAccount payableAccountBuild;

    private static PayableAccount buildPayableAccount(PayableAccountInput payableAccountInput) {

        PayableAccount payableAccount = new PayableAccount();

        payableAccount.setId(payableAccountInput.id());
        payableAccount.setDueDate(payableAccountInput.dueDate());
        payableAccount.setPaymentDate(payableAccountInput.paymentDate());
        payableAccount.setAmount(payableAccountInput.amount());
        payableAccount.setDescription(payableAccountInput.description());
        payableAccount.setStatus(payableAccountInput.status());

        return payableAccount;
    }

    private static PayableAccountView buildPayableAccountView(PayableAccountInput payableAccountInput) {

        return new PayableAccountView(payableAccountInput.id(),
                payableAccountInput.dueDate(),
                payableAccountInput.paymentDate(),
                payableAccountInput.amount(),
                payableAccountInput.description(),
                payableAccountInput.status());
    }

    @BeforeEach
    public void setup() {
        payableAccountBuild = PayableAccountBuilder.buildPayableAccount();
    }

    @DisplayName("Testa a criação de uma conta a pagar com dados válidos.")
    @Test
    public void testCreatePayableAccount() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);

        PayableAccountView payableAccountExpectedResponse = buildPayableAccountView(payableAccountInput);

        when(mapper.toModel(any(PayableAccountInput.class))).thenReturn(payableAccount);
        when(repository.save(any(PayableAccount.class))).thenReturn(payableAccount);
        when(mapper.toResponse(any(PayableAccount.class))).thenReturn(payableAccountExpectedResponse);

        PayableAccountView payableAccountActualResponse = adapter.createPayableAccount(payableAccountInput);

        assertEquals(payableAccountExpectedResponse, payableAccountActualResponse);
    }

    @DisplayName("Testa a criação de uma conta a pagar com data de vencimento nula, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountWithDueDateNull() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        payableAccount.setDueDate(null);

        when(mapper.toModel(any(PayableAccountInput.class))).thenReturn(payableAccount);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccountInput));
    }

    @DisplayName("Testa a criação de uma conta a pagar com valor negativo, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountWithAmountNegative() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        payableAccount.setAmount(BigDecimal.valueOf(-0.01d));

        when(mapper.toModel(any(PayableAccountInput.class))).thenReturn(payableAccount);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccountInput));
    }

    @DisplayName("Testa a criação de uma conta a pagar com valor zero, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountWithAmountZero() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        payableAccount.setAmount(BigDecimal.ZERO);

        when(mapper.toModel(any(PayableAccountInput.class))).thenReturn(payableAccount);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccountInput));
    }

    @DisplayName("Testa a criação de uma conta a pagar com descrição nula, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountWithDescriptionNull() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        payableAccount.setDescription(null);

        when(mapper.toModel(any(PayableAccountInput.class))).thenReturn(payableAccount);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccountInput));
    }

    @DisplayName("Testa a criação de uma conta a pagar com descrição vazia, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountWithDescriptionEmpty() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        payableAccount.setDescription("");

        when(mapper.toModel(any(PayableAccountInput.class))).thenReturn(payableAccount);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccountInput));
    }

    @DisplayName("Testa a criação de uma conta a pagar com descrição contendo apenas espaços, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountWithDescriptionWithEmptySpace() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        payableAccount.setDescription(" ");

        when(mapper.toModel(any(PayableAccountInput.class))).thenReturn(payableAccount);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccountInput));
    }

    @DisplayName("Testa a criação de uma conta a pagar com status nulo, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountWithStatusNull() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        payableAccount.setStatus(null);

        when(mapper.toModel(any(PayableAccountInput.class))).thenReturn(payableAccount);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccountInput));
    }

    @DisplayName("Testa a atualização de uma conta a pagar com dados válidos.")
    @Test
    public void testUpdatePayableAccount() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        PayableAccountView payableAccountExpectedResponse = buildPayableAccountView(payableAccountInput);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(payableAccount));
        when(repository.save(any(PayableAccount.class))).thenReturn(payableAccount);
        when(mapper.toResponse(any(PayableAccount.class))).thenReturn(payableAccountExpectedResponse);

        PayableAccountView payableAccountActualResponse = adapter.updatePayableAccount(payableAccountInput.id(), payableAccountInput);

        assertEquals(payableAccountExpectedResponse, payableAccountActualResponse);
    }

    @DisplayName("Testa a busca de uma conta a pagar por ID.")
    @Test
    public void testGetPayableAccountById() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = buildPayableAccount(payableAccountInput);
        PayableAccountView payableAccountExpectedResponse = buildPayableAccountView(payableAccountInput);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(payableAccount));
        when(mapper.toResponse(any(PayableAccount.class))).thenReturn(payableAccountExpectedResponse);

        PayableAccountView payableAccountActualResponse = adapter.getPayableAccountById(payableAccountInput.id());

        assertEquals(payableAccountExpectedResponse, payableAccountActualResponse);
    }

    @DisplayName("Testa a busca de uma conta a pagar por ID não encontrado, esperando uma exceção.")
    @Test
    public void testGetPayableAccountByIdNotFound() {
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(PayableAccountNotFoundException.class, () -> adapter.getPayableAccountById(UUID.randomUUID()));
    }

    @DisplayName("Testa a obtenção do valor total das contas a pagar em um período específico.")
    @Test
    public void testGetPayableAccountsAmountByPeriod() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        BigDecimal expectedAmount = BigDecimal.valueOf(1000);

        when(repository.findByDueDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedAmount);
        BigDecimal actualAmount = adapter.getPayableAccountsAmountByPeriod(startDate, endDate);

        assertEquals(expectedAmount, actualAmount);
    }

}