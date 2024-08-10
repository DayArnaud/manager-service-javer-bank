package io.github.dayanearnaud.manager_service_javer_bank.repository;

import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByEmail(String email);
    Optional<CustomerEntity> findByPhone(Long phone);
}
