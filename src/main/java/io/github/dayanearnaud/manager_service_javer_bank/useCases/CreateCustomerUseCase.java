package io.github.dayanearnaud.manager_service_javer_bank.useCases;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.DuplicateAccountNumberException;
import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerUseCase {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerEntity execute(CustomerEntity customerEntity) {
        this.customerRepository.findByEmail(customerEntity.getEmail()).ifPresent(user -> {
            throw new UserFoundException();
        });

        double roundedBalance = Math.round(customerEntity.getBalance() * 100.0) / 100.0;
        customerEntity.setBalance(roundedBalance);

        double calculatedCreditScore = customerEntity.getBalance() * 0.1;
        customerEntity.setCredit_score((double) Math.round(calculatedCreditScore));

        try {
            return this.customerRepository.save(customerEntity);
        } catch(DataIntegrityViolationException e) {
            throw new DuplicateAccountNumberException("Account number must be unique.");
        }
    }
}
