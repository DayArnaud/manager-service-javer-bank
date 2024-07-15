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
    private CustomerRepository customerRepository;

    public double execute(UUID id) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        double score = customer.getBalance() * 0.1;
        customer.setCredit_score(score);
        customerRepository.save(customer);

        return score;
    }
}
