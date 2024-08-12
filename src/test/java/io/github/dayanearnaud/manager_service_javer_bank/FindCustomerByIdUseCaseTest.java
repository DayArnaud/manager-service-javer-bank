package io.github.dayanearnaud.manager_service_javer_bank;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.FindCustomerByIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindCustomerByIdUseCaseTest {

    @InjectMocks
    private FindCustomerByIdUseCase findCustomerByIdUseCase;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should return customer when found by id")
    public void shouldReturnCustomerWhenFoundById() {

        UUID customerId = UUID.randomUUID();
        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setId(customerId);
        existingCustomer.setName("Existing Customer");
        existingCustomer.setEmail("existing@email.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        CustomerEntity result = findCustomerByIdUseCase.execute(customerId);

        assertEquals(existingCustomer, result);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when customer is not found")
    public void shouldThrowUserNotFoundExceptionWhenCustomerIsNotFound() {

        UUID customerId = UUID.randomUUID();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            findCustomerByIdUseCase.execute(customerId);
        });

        verify(customerRepository, times(1)).findById(customerId);
    }
}
