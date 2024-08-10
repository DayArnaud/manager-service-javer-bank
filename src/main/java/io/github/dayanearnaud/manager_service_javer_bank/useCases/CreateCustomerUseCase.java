package io.github.dayanearnaud.manager_service_javer_bank.useCases;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserInfoFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerUseCase {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final CalculateScoreUseCase calculateScoreUseCase;

    public CreateCustomerUseCase(CustomerRepository customerRepository, CalculateScoreUseCase calculateScoreUseCase) {
        this.customerRepository = customerRepository;
        this.calculateScoreUseCase = calculateScoreUseCase;
    }

    public CustomerEntity execute(CustomerEntity customerEntity) {
        customerRepository.findByEmail(customerEntity.getEmail()).ifPresent(customer -> {
            throw new UserInfoFoundException("Email already in use by another customer.");
        });

        customerRepository.findByPhone((customerEntity.getPhone())).ifPresent((customer -> {
            throw new UserInfoFoundException("Phone number already in use by another customer.");
        }));

        double roundedBalance = Math.round(customerEntity.getBalance() * 100.0) / 100.0;
        customerEntity.setBalance(roundedBalance);

        try {
            CustomerEntity savedCustomer = customerRepository.save(customerEntity);

            double calculatedCreditScore = calculateScoreUseCase.execute(savedCustomer.getId());
            savedCustomer.setCredit_score(calculatedCreditScore);

            return customerRepository.save(savedCustomer);
        } catch(DataIntegrityViolationException e) {
            throw new UserInfoFoundException("Account number must be unique.");
        }
    }
}
