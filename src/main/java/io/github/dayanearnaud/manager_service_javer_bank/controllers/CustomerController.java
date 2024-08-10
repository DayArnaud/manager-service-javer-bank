package io.github.dayanearnaud.manager_service_javer_bank.controllers;

import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CreateCustomerUseCase createCustomerUseCase;

    @Autowired
    private FindCustomerByIdUseCase findCustomerByIdUseCase;

    @Autowired
    private GetAllCustomersUseCase getAllCustomersUseCase;

    @Autowired
    private UpdateCustomerUseCase updateCustomerUseCase;

    @Autowired
    private DeleteCustomerUseCase deleteCustomerUseCase;

    @Autowired
    private CalculateScoreUseCase calculateScoreUseCase;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, FindCustomerByIdUseCase findCustomerByIdUseCase, GetAllCustomersUseCase getAllCustomersUseCase, UpdateCustomerUseCase updateCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, CalculateScoreUseCase calculateScoreUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.calculateScoreUseCase = calculateScoreUseCase;
    }

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CustomerEntity customerEntity) {
        try {
            var result = createCustomerUseCase.execute(customerEntity);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            var result = findCustomerByIdUseCase.execute(uuid);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll() {
        var result = getAllCustomersUseCase.execute();
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody CustomerEntity customerEntity) {
        try{
            var result = updateCustomerUseCase.execute(id, customerEntity);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        try{
            UUID uuid = UUID.fromString(id);
            deleteCustomerUseCase.execute(uuid);
            return ResponseEntity.ok().body("Customer deleted successfully");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/calculate-score/{id}")
    public ResponseEntity<Object> calculateScore(@PathVariable String id) {
        try{
            UUID uuid = UUID.fromString(id);
            var result = calculateScoreUseCase.execute(uuid);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
