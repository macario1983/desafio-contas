package br.com.contas.infrastructure.adapters.config;

import br.com.contas.domain.service.PayableAccountService;
import br.com.contas.infrastructure.adapters.output.persitence.PayableAccountPersistenceAdapter;
import br.com.contas.infrastructure.adapters.output.persitence.mapper.PayableAccountMapper;
import br.com.contas.infrastructure.adapters.output.persitence.repository.PayableAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    PayableAccountPersistenceAdapter payableAccountPersistenceAdapter(PayableAccountMapper payableAccountMapper, PayableAccountRepository payableAccountRepository) {
        return new PayableAccountPersistenceAdapter(payableAccountMapper, payableAccountRepository);
    }

    @Bean
    PayableAccountService payableAccountService(PayableAccountPersistenceAdapter payableAccountPersistenceAdapter) {
        return new PayableAccountService(payableAccountPersistenceAdapter);
    }

}
