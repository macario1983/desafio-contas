package br.com.desafio.payableaccount.core.mapstruct;

import br.com.desafio.payableaccount.api.v1.model.input.PayableAccountInput;
import br.com.desafio.payableaccount.api.v1.model.view.PayableAccountView;
import br.com.desafio.payableaccount.domain.builder.PayableAccountBuilder;
import br.com.desafio.payableaccount.domain.model.PayableAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PayableAccountMapperTest {

    @Spy
    private PayableAccountMapper mapper = Mappers.getMapper(PayableAccountMapper.class);

    @DisplayName("Testa a conversão de uma entidade PayableAccount para PayableAccountView.")
    @Test
    void testToResponse() {

        PayableAccount payableAccount = PayableAccountBuilder.buildPayableAccount();
        PayableAccountView response = mapper.toResponse(payableAccount);

        assertNotNull(response);
        assertEquals(payableAccount.getId(), response.id());
        assertEquals(payableAccount.getDueDate(), response.dueDate());
        assertEquals(payableAccount.getPaymentDate(), response.paymentDate());
        assertEquals(payableAccount.getAmount(), response.amount());
        assertEquals(payableAccount.getDescription(), response.description());
    }

    @DisplayName("Testa a conversão de um PayableAccountInput para um modelo PayableAccount.")
    @Test
    void testToModelFromRequest() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount model = mapper.toModel(payableAccountInput);

        assertNotNull(model);
        assertEquals(payableAccountInput.dueDate(), model.getDueDate());
        assertEquals(payableAccountInput.paymentDate(), model.getPaymentDate());
        assertEquals(payableAccountInput.amount(), model.getAmount());
        assertEquals(payableAccountInput.description(), model.getDescription());
    }

    @DisplayName("Testa a conversão de uma entidade PayableAccount para um modelo PayableAccount.")
    @Test
    void testToModelFromEntity() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount model = mapper.toModel(payableAccountInput);

        assertNotNull(model);
        assertEquals(payableAccountInput.dueDate(), model.getDueDate());
        assertEquals(payableAccountInput.paymentDate(), model.getPaymentDate());
        assertEquals(payableAccountInput.amount(), model.getAmount());
        assertEquals(payableAccountInput.description(), model.getDescription());
    }

    @DisplayName("Testa a conversão de um modelo PayableAccount para uma entidade PayableAccount.")
    @Test
    void testToEntity() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = mapper.toModel(payableAccountInput);

        assertNotNull(payableAccount);
        assertEquals(payableAccountInput.dueDate(), payableAccount.getDueDate());
        assertEquals(payableAccountInput.paymentDate(), payableAccount.getPaymentDate());
        assertEquals(payableAccountInput.amount(), payableAccount.getAmount());
        assertEquals(payableAccountInput.description(), payableAccount.getDescription());
    }

    @DisplayName("Testa a atualização de uma entidade PayableAccount existente com dados de um modelo PayableAccount, exceto o ID.")
    @Test
    void testToEntityExceptyId() {

        PayableAccountInput payableAccountInput = PayableAccountBuilder.buildPayableAccountInput();
        PayableAccount payableAccount = mapper.toModel(payableAccountInput);
        mapper.toModelExceptyId(payableAccountInput, payableAccount);

        assertNotNull(payableAccount);
        assertEquals(payableAccountInput.dueDate(), payableAccount.getDueDate());
        assertEquals(payableAccountInput.paymentDate(), payableAccount.getPaymentDate());
        assertEquals(payableAccountInput.amount(), payableAccount.getAmount());
        assertEquals(payableAccountInput.description(), payableAccount.getDescription());
    }

}