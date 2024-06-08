package br.com.contas.infrastructure.adapters.output.persitence.entity;

import br.com.contas.domain.model.PayableAccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class PayableAccountEntity {

    @Id
    private UUID id;

    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate paymentDate;

    @Positive
    @Column(nullable = false)
    private BigDecimal amount;

    @NotBlank
    @Column(length = 255, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotBlank
    @Column(length = 50, nullable = false)
    private PayableAccountStatus status;

    @PrePersist
    public void setData() {
        this.id = UUID.randomUUID();
        this.status = PayableAccountStatus.PENDING;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @FutureOrPresent LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@FutureOrPresent LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public @Positive BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@Positive BigDecimal amount) {
        this.amount = amount;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public @NotBlank PayableAccountStatus getStatus() {
        return status;
    }

    public void setStatus(@NotBlank PayableAccountStatus status) {
        this.status = status;
    }
}
