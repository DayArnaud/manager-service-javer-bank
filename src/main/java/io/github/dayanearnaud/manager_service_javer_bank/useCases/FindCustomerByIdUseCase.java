package io.github.dayanearnaud.manager_service_javer_bank.useCases;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindCustomerByIdUseCase {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerEntity execute(UUID id) {
        return this.customerRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
