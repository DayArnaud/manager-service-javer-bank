package io.github.dayanearnaud.manager_service_javer_bank.useCases;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CalculateScoreUseCase {

    @Autowired
    private final CustomerRepository customerRepository;

    public CalculateScoreUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public double execute(UUID id) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        double roundedScore = Math.round(customer.getBalance() * 0.1 * 100.0) / 100.0;
        customer.setCredit_score(roundedScore);
        customerRepository.save(customer);

        return roundedScore;
    }
}
