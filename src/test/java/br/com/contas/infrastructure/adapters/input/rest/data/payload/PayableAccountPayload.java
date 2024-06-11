package br.com.contas.infrastructure.adapters.input.rest.data.payload;

import br.com.contas.domain.model.PayableAccountStatus;
import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PayableAccountPayload {

    public static String toJson(PayableAccountEntity payableAccountEntity) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(payableAccountEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public static PayableAccountEntity buildPayableAccountEntity() {

        Faker faker = new Faker(new Locale("pt-BR"));

        PayableAccountEntity payableAccountEntity = new PayableAccountEntity();

        payableAccountEntity.setDueDate(faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        payableAccountEntity.setPaymentDate(faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        payableAccountEntity.setAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000)));
        payableAccountEntity.setDescription(faker.lorem().sentence());
        payableAccountEntity.setStatus(faker.options().option(PayableAccountStatus.PAID, PayableAccountStatus.OVERDUE, PayableAccountStatus.PENDING));

        return payableAccountEntity;
    }

}
