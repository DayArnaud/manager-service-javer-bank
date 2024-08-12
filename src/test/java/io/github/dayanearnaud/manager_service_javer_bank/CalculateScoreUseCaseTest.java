package io.github.dayanearnaud.manager_service_javer_bank;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.CalculateScoreUseCase;
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
public class CalculateScoreUseCaseTest {

    @InjectMocks
    private CalculateScoreUseCase calculateScoreUseCase;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should calculate credit score correctly")
    public void shouldCalculateCreditScoreCorrectly() {

        UUID customerId = UUID.randomUUID();

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setBalance(5000.0);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        double expectedScore = Math.round(customer.getBalance() * 0.1 * 100.0) / 100.0;
        double result = calculateScoreUseCase.execute(customerId);

        assertEquals(expectedScore, result);
        verify(customerRepository, times(1)).save(customer);
        assertEquals(expectedScore, customer.getCredit_score());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when customer does not exist")
    public void shouldThrowUserNotFoundExceptionWhenCustomerDoesNotExist() {

        UUID customerId = UUID.randomUUID();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> calculateScoreUseCase.execute(customerId));
    }
}
