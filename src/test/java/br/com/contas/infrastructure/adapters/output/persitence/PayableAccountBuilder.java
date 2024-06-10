package br.com.contas.infrastructure.adapters.output.persitence;

import br.com.contas.domain.model.PayableAccount;
import br.com.contas.domain.model.PayableAccountStatus;
import br.com.contas.infrastructure.adapters.input.rest.data.request.PayableAccountRequest;
import br.com.contas.infrastructure.adapters.input.rest.data.response.PayableAccountResponse;
import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PayableAccountBuilder {

    public static @NotNull PayableAccountEntity buildPayableAccountEntity() {

        Faker faker = new Faker(new Locale("pt-BR"));

        PayableAccountEntity payableAccountEntity = new PayableAccountEntity();

        payableAccountEntity.setDueDate(faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        payableAccountEntity.setPaymentDate(faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        payableAccountEntity.setAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000)));
        payableAccountEntity.setDescription(faker.lorem().sentence());
        payableAccountEntity.setStatus(faker.options().option(PayableAccountStatus.PAID, PayableAccountStatus.OVERDUE, PayableAccountStatus.PENDING));

        return payableAccountEntity;
    }

    public static @NotNull PayableAccount buildPayableAccount() {

        Faker faker = new Faker(new Locale("pt-BR"));

        var id = UUID.randomUUID();
        var dueDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var paymentDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var amount = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000));
        var description = faker.lorem().sentence();
        var status = faker.options().option(PayableAccountStatus.PAID, PayableAccountStatus.OVERDUE, PayableAccountStatus.PENDING);

        return new PayableAccount(id, dueDate, paymentDate, amount, description, status);
    }

    public static @NotNull PayableAccountRequest buildPayableAccountRequest() {

        Faker faker = new Faker(new Locale("pt-BR"));

        var id = UUID.randomUUID();
        var dueDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var paymentDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var amount = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000));
        var description = faker.lorem().sentence();
        var status = faker.options().option(PayableAccountStatus.PAID, PayableAccountStatus.OVERDUE, PayableAccountStatus.PENDING);

        return new PayableAccountRequest(id, dueDate, paymentDate, amount, description, status);
    }

    public static @NotNull PayableAccountResponse buildPayableAccountResponse() {

        Faker faker = new Faker(new Locale("pt-BR"));

        var id = UUID.randomUUID();
        var dueDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var paymentDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var amount = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000));
        var description = faker.lorem().sentence();
        var status = faker.options().option(PayableAccountStatus.PAID, PayableAccountStatus.OVERDUE, PayableAccountStatus.PENDING);

        return new PayableAccountResponse(id, dueDate, paymentDate, amount, description, status);
    }
}
