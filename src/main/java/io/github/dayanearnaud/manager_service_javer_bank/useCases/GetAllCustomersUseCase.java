package io.github.dayanearnaud.manager_service_javer_bank.useCases;


import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCustomersUseCase {

    @Autowired
    private final CustomerRepository customerRepository;

    public GetAllCustomersUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerEntity> execute() {
        return customerRepository.findAll();
    }
}
