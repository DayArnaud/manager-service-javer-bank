package io.github.dayanearnaud.manager_service_javer_bank.model;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.UUID;

@Data
public class CustomerEntity {

    private UUID id;
    private String name;

    @Email(message = "The [email] field must be valid.")
    private String email;
    private Long phone;
    private Boolean account_holder;
    private String account_number;
    private Double balance;
    private Double credit_score;
}
