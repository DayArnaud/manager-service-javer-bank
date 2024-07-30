package io.github.dayanearnaud.manager_service_javer_bank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String name;

    @Email(message = "The [email] field must be valid.")
    private String email;
    private Long phone;
    private Boolean account_holder;

    @Column(name = "account_number", unique = true)
    private String account_number;

    @NotNull(message = "Balance must not be null.")
    @Min(value = 0, message = "Balance must be greater than or equal to zero.")
    private Double balance;

    private Double credit_score;

    @CreationTimestamp
    private LocalDateTime created_at;
}
