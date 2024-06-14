package br.com.desafio.payableaccount.domain.repository;

import br.com.desafio.payableaccount.domain.model.PayableAccount;
import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;
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
    @Query("UPDATE PayableAccount pa SET pa.status = ?2 WHERE pa.id = ?1")
    void updateStatusById(UUID id, PayableAccountStatus payableAccountStatus);

    Page<PayableAccount> findByDueDateOrDescriptionContainingIgnoreCase(Pageable pageable, LocalDate dueDate, String description);

    @Query("SELECT SUM(pa.amount) FROM PayableAccount pa WHERE pa.dueDate BETWEEN ?1 AND ?2")
    BigDecimal findByDueDateBetween(LocalDate startDate, LocalDate endDate);

}
