package br.com.desafio.payableaccount.domain.repository;

import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;
import br.com.desafio.payableaccount.domain.model.PayableAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PayableAccountRepository extends JpaRepository<PayableAccount, UUID> {

    @Modifying
    @Query("UPDATE PayableAccountEntity pae SET pae.status = ?2 WHERE pae.id = ?1")
    void updateStatusById(UUID id, PayableAccountStatus payableAccountStatus);

    Page<PayableAccount> findByDueDateOrDescriptionContainingIgnoreCase(Pageable pageable, LocalDate dueDate, String description);

    @Query("SELECT SUM(pae.amount) FROM PayableAccountEntity pae WHERE pae.dueDate BETWEEN ?1 AND ?2")
    BigDecimal findByDueDateBetween(LocalDate startDate, LocalDate endDate);

}
