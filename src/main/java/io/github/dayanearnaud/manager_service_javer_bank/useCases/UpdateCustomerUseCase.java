package io.github.dayanearnaud.manager_service_javer_bank.useCases;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCustomerUseCase {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerEntity execute(UUID id, CustomerEntity customerEntity) {
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setName(customerEntity.getName());
            existingCustomer.setEmail(customerEntity.getEmail());
            existingCustomer.setPhone(customerEntity.getPhone());
            existingCustomer.setAccount_holder(customerEntity.getAccount_holder());
            existingCustomer.setBalance(customerEntity.getBalance());
            return customerRepository.save(existingCustomer);
        }).orElseThrow(() -> new UserNotFoundException());
    }
}
