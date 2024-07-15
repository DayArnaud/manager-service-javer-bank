package io.github.dayanearnaud.manager_service_javer_bank.useCases;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCustomerUseCase {

    @Autowired
    private CustomerRepository customerRepository;

    public void execute(UUID id) {
        this.customerRepository.findById(id).ifPresentOrElse(
                customer -> customerRepository.deleteById(id), () -> {
                    throw new UserNotFoundException();
                }
        );
    }
}
