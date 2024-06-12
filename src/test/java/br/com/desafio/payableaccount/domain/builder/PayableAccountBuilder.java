package br.com.desafio.payableaccount.domain.builder;

import br.com.desafio.payableaccount.api.v1.model.input.PayableAccountInput;
import br.com.desafio.payableaccount.api.v1.model.view.PayableAccountView;
import br.com.desafio.payableaccount.domain.model.PayableAccount;
import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PayableAccountBuilder {

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

    public static PayableAccountInput buildPayableAccountInput() {

        Faker faker = new Faker(new Locale("pt-BR"));

        var id = UUID.randomUUID();
        var dueDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var paymentDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var amount = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000));
        var description = faker.lorem().sentence();
        var status = faker.options().option(PayableAccountStatus.PAID, PayableAccountStatus.OVERDUE, PayableAccountStatus.PENDING);

        return new PayableAccountInput(id, dueDate, paymentDate, amount, description, status);
    }

    public static PayableAccountView buildPayableAccountView() {

        Faker faker = new Faker(new Locale("pt-BR"));

        var id = UUID.randomUUID();
        var dueDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var paymentDate = faker.date().future(30, TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        var amount = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000));
        var description = faker.lorem().sentence();
        var status = faker.options().option(PayableAccountStatus.PAID, PayableAccountStatus.OVERDUE, PayableAccountStatus.PENDING);

        return new PayableAccountView(id, dueDate, paymentDate, amount, description, status);
    }
}
