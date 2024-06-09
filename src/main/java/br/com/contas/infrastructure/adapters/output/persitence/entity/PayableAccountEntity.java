package br.com.contas.infrastructure.adapters.output.persitence.entity;

import br.com.contas.domain.model.PayableAccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "accounts_payable")
@Entity
public class PayableAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    @Column(nullable = false)
    private LocalDate dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate paymentDate;

    @Positive
    @Column(nullable = false)
    private BigDecimal amount;

    @NotBlank
    @Column(length = 255, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length = 50, nullable = false)
    private PayableAccountStatus status;

    @PrePersist
    public void setData() {
        this.status = PayableAccountStatus.PENDING;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NotNull LocalDate dueDate) {
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

    public @NotNull PayableAccountStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull PayableAccountStatus status) {
        this.status = status;
    }
}
