package io.github.dayanearnaud.manager_service_javer_bank.useCases;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerUseCase {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerEntity execute(CustomerEntity customerEntity) {
        this.customerRepository.findByEmail(customerEntity.getEmail()).ifPresent(user -> {
            throw new UserFoundException();
        });
        return this.customerRepository.save(customerEntity);
    }
}
