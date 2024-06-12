package br.com.desafio.payableaccount.api.payload;

import br.com.desafio.payableaccount.domain.model.PayableAccount;
import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PayableAccountPayload {

    public static String toJson(PayableAccount payableAccount) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(payableAccount);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public static PayableAccount buildPayableAccount() {

        Faker faker = new Faker(new Locale("pt-BR"));

        PayableAccount payableAccount = new PayableAccount();

        payableAccount.setDueDate(faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        payableAccount.setPaymentDate(faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        payableAccount.setAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000)));
        payableAccount.setDescription(faker.lorem().sentence());
        payableAccount.setStatus(faker.options().option(PayableAccountStatus.PAID, PayableAccountStatus.OVERDUE, PayableAccountStatus.PENDING));

        return payableAccount;
    }
}
