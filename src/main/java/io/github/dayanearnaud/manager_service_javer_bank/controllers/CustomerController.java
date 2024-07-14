package io.github.dayanearnaud.manager_service_javer_bank.controllers;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.CreateCustomerUseCase;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.FindCustomerByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CustomerEntity customerEntity) {
        try {
            var result = this.createCustomerUseCase.execute(customerEntity);
            return ResponseEntity.ok().body(result);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            var result = this.findCustomerByIdUseCase.execute(uuid);
            return ResponseEntity.ok(result);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID format.");
        } catch(UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
