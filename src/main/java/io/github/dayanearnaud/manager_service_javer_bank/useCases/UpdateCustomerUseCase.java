package io.github.dayanearnaud.manager_service_javer_bank.useCases;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserInfoFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCustomerUseCase {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final CalculateScoreUseCase calculateScoreUseCase;

    public UpdateCustomerUseCase(CustomerRepository customerRepository, CalculateScoreUseCase calculateScoreUseCase) {
        this.customerRepository = customerRepository;
        this.calculateScoreUseCase = calculateScoreUseCase;
    }

    public CustomerEntity execute(UUID id, CustomerEntity customerEntity) {
        return customerRepository.findById(id).map(existingCustomer -> {
            customerRepository.findByPhone(customerEntity.getPhone()).ifPresent(customer -> {
                if(!customer.getId().equals(id)) {
                    throw new UserInfoFoundException("Phone number already in use by another customer.");
                }
            });
            customerRepository.findByEmail(customerEntity.getEmail()).ifPresent(customer -> {
                if(!customer.getId().equals(id)) {
                    throw new UserInfoFoundException("Email already in use by another customer.");
                }
            });
            existingCustomer.setName(customerEntity.getName());
            existingCustomer.setEmail(customerEntity.getEmail());
            existingCustomer.setPhone(customerEntity.getPhone());
            existingCustomer.setAccount_holder(customerEntity.getAccount_holder());
            existingCustomer.setBalance(customerEntity.getBalance());

            double newCreditScore = calculateScoreUseCase.execute(existingCustomer.getId());
            existingCustomer.setCredit_score(newCreditScore);

            return customerRepository.save(existingCustomer);
        }).orElseThrow(() -> new UserNotFoundException());
    }
}
