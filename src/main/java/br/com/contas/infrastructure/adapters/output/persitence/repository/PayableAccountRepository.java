package br.com.contas.infrastructure.adapters.output.persitence.repository;

import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PayableAccountRepository extends JpaRepository<PayableAccountEntity, UUID> {
}
