package br.com.contas.infrastructure.adapters.output.persitence;

import br.com.contas.domain.exception.PayableAccountNotFoundException;
import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
import br.com.contas.infrastructure.adapters.output.persitence.mapper.PayableAccountMapper;
import br.com.contas.infrastructure.adapters.output.persitence.repository.PayableAccountRepository;
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
public class PayableAccountPersistenceAdapterTest {

    @Mock
    private PayableAccountMapper mapper;

    @Mock
    private PayableAccountRepository repository;

    @InjectMocks
    private PayableAccountPersistenceAdapter adapter;

    private PayableAccount payableAccountBuild;

    @BeforeEach
    public void setup(){
        payableAccountBuild = PayableAccountBuilder.buildPayableAccount();
    }

    @DisplayName("Testa a criação de uma conta a pagar com dados válidos.")
    @Test
    public void testCreatePayableAccount() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);

        PayableAccountResponse payableAccountExpectedResponse = buildPayableAccountResponse(payableAccount);

        when(mapper.toEntity(any(PayableAccount.class))).thenReturn(payableAccountEntity);
        when(repository.save(any(PayableAccountEntity.class))).thenReturn(payableAccountEntity);
        when(mapper.toResponse(any(PayableAccountEntity.class))).thenReturn(payableAccountExpectedResponse);

        PayableAccountResponse payableAccountActualResponse = adapter.createPayableAccount(payableAccount);

        assertEquals(payableAccountExpectedResponse, payableAccountActualResponse);
    }

    @DisplayName("Testa a criação de uma conta a pagar com data de vencimento nula, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountEntityWithDueDateNull() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        payableAccountEntity.setDueDate(null);

        when(mapper.toEntity(any(PayableAccount.class))).thenReturn(payableAccountEntity);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccount));
    }

    @DisplayName("Testa a criação de uma conta a pagar com valor negativo, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountEntityWithAmountNegative() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        payableAccountEntity.setAmount(BigDecimal.valueOf(-0.01d));

        when(mapper.toEntity(any(PayableAccount.class))).thenReturn(payableAccountEntity);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccount));
    }

    @DisplayName("Testa a criação de uma conta a pagar com valor zero, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountEntityWithAmountZero() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        payableAccountEntity.setAmount(BigDecimal.ZERO);

        when(mapper.toEntity(any(PayableAccount.class))).thenReturn(payableAccountEntity);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccount));
    }

    @DisplayName("Testa a criação de uma conta a pagar com descrição nula, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountEntityWithDescriptionNull() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        payableAccountEntity.setDescription(null);

        when(mapper.toEntity(any(PayableAccount.class))).thenReturn(payableAccountEntity);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccount));
    }

    @DisplayName("Testa a criação de uma conta a pagar com descrição vazia, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountEntityWithDescriptionEmpty() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        payableAccountEntity.setDescription("");

        when(mapper.toEntity(any(PayableAccount.class))).thenReturn(payableAccountEntity);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccount));
    }

    @DisplayName("Testa a criação de uma conta a pagar com descrição contendo apenas espaços, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountEntityWithDescriptionWithEmptySpace() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        payableAccountEntity.setDescription(" ");

        when(mapper.toEntity(any(PayableAccount.class))).thenReturn(payableAccountEntity);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccount));
    }

    @DisplayName("Testa a criação de uma conta a pagar com status nulo, esperando violação de constraint.")
    @Test
    public void testCreatePayableAccountEntityWithStatusNull() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        payableAccountEntity.setStatus(null);

        when(mapper.toEntity(any(PayableAccount.class))).thenReturn(payableAccountEntity);
        assertThrows(ConstraintViolationException.class, () -> adapter.createPayableAccount(payableAccount));
    }

    @DisplayName("Testa a atualização de uma conta a pagar com dados válidos.")
    @Test
    public void testUpdatePayableAccount() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        PayableAccountResponse payableAccountExpectedResponse = buildPayableAccountResponse(payableAccount);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(payableAccountEntity));
        when(repository.save(any(PayableAccountEntity.class))).thenReturn(payableAccountEntity);
        when(mapper.toResponse(any(PayableAccountEntity.class))).thenReturn(payableAccountExpectedResponse);

        PayableAccountResponse payableAccountActualResponse = adapter.updatePayableAccount(payableAccount.id(), payableAccount);

        assertEquals(payableAccountExpectedResponse, payableAccountActualResponse);
    }

    @DisplayName("Testa a busca de uma conta a pagar por ID.")
    @Test
    public void testGetPayableAccountById() {
        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity payableAccountEntity = buildPayableAccountEntity(payableAccount);
        PayableAccountResponse payableAccountExpectedResponse = buildPayableAccountResponse(payableAccount);

        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(payableAccountEntity));
        when(mapper.toResponse(any(PayableAccountEntity.class))).thenReturn(payableAccountExpectedResponse);

        PayableAccountResponse payableAccountActualResponse = adapter.getPayableAccountById(payableAccount.id());

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

    private static PayableAccountEntity buildPayableAccountEntity(PayableAccount payableAccount) {
        PayableAccountEntity payableAccountEntity = new PayableAccountEntity();

        payableAccountEntity.setId(payableAccount.id());
        payableAccountEntity.setDueDate(payableAccount.dueDate());
        payableAccountEntity.setPaymentDate(payableAccount.paymentDate());
        payableAccountEntity.setAmount(payableAccount.amount());
        payableAccountEntity.setDescription(payableAccount.description());
        payableAccountEntity.setStatus(payableAccount.status());

        return payableAccountEntity;
    }

    private static PayableAccountResponse buildPayableAccountResponse(PayableAccount payableAccount) {
        return new PayableAccountResponse(payableAccount.id(),
                payableAccount.dueDate(),
                payableAccount.paymentDate(),
                payableAccount.amount(),
                payableAccount.description(),
                payableAccount.status());
    }
}
