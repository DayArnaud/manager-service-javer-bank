package io.github.dayanearnaud.manager_service_javer_bank;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.DeleteCustomerUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCustomerUseCaseTest {

    @InjectMocks
    private DeleteCustomerUseCase deleteCustomerUseCase;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should delete customer successfully when customer exists")
    public void shouldDeleteCustomerSuccessfullyWhenCustomerExists() {

        UUID customerId = UUID.randomUUID();
        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        deleteCustomerUseCase.execute(customerId);

        verify(customerRepository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when customer does not exist")
    public void shouldThrowUserNotFoundExceptionWhenCustomerDoesNotExist() {

        UUID customerId = UUID.randomUUID();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> deleteCustomerUseCase.execute(customerId));

        verify(customerRepository, times(0)).deleteById(any());
    }
}
