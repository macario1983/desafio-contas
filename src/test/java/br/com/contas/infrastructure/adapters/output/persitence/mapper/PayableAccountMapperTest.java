package br.com.contas.infrastructure.adapters.output.persitence.mapper;

import br.com.contas.domain.model.PayableAccount;
import br.com.contas.infrastructure.adapters.input.rest.data.request.PayableAccountRequest;
import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import br.com.contas.infrastructure.adapters.output.persitence.PayableAccountBuilder;
import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayableAccountMapperTest {

    @Autowired
    private PayableAccountMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PayableAccountMapper.class);
    }

    @Test
    void testToResponse() {

        PayableAccountEntity entity = PayableAccountBuilder.buildPayableAccountEntity();
        PayableAccountResponse response = mapper.toResponse(entity);

        assertNotNull(response);
        assertEquals(entity.getId(), response.id());
        assertEquals(entity.getDueDate(), response.dueDate());
        assertEquals(entity.getPaymentDate(), response.paymentDate());
        assertEquals(entity.getAmount(), response.amount());
        assertEquals(entity.getDescription(), response.description());
    }

    @Test
    void testToModelFromRequest() {

        PayableAccountRequest request = PayableAccountBuilder.buildPayableAccountRequest();
        PayableAccount model = mapper.toModel(request);

        assertNotNull(model);
        assertEquals(request.dueDate(), model.dueDate());
        assertEquals(request.paymentDate(), model.paymentDate());
        assertEquals(request.amount(), model.amount());
        assertEquals(request.description(), model.description());
    }

    @Test
    void testToModelFromEntity() {

        PayableAccountEntity entity = PayableAccountBuilder.buildPayableAccountEntity();
        PayableAccount model = mapper.toModel(entity);

        assertNotNull(model);
        assertEquals(entity.getDueDate(), model.dueDate());
        assertEquals(entity.getPaymentDate(), model.paymentDate());
        assertEquals(entity.getAmount(), model.amount());
        assertEquals(entity.getDescription(), model.description());
    }

    @Test
    void testToEntity() {

        PayableAccount model = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity entity = mapper.toEntity(model);

        assertNotNull(entity);
        assertEquals(model.dueDate(), entity.getDueDate());
        assertEquals(model.paymentDate(), entity.getPaymentDate());
        assertEquals(model.amount(), entity.getAmount());
        assertEquals(model.description(), entity.getDescription());
    }

    @Test
    void testToEntityExceptyId() {

        PayableAccount model = PayableAccountBuilder.buildPayableAccount();
        PayableAccountEntity entity = new PayableAccountEntity();
        mapper.toEntityExceptyId(model, entity);

        assertNotNull(entity);
        assertEquals(model.dueDate(), entity.getDueDate());
        assertEquals(model.paymentDate(), entity.getPaymentDate());
        assertEquals(model.amount(), entity.getAmount());
        assertEquals(model.description(), entity.getDescription());
    }
}
