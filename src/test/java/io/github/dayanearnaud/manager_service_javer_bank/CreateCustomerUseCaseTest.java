package io.github.dayanearnaud.manager_service_javer_bank;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.CreateCustomerUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreateCustomerUseCaseTest {

    @InjectMocks
    private CreateCustomerUseCase createCustomerUseCase;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should not create customer if already exists")
    public void shouldNotCreateCustomerIfAlreadyExists() {
        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setEmail("test@example.com");

        when(customerRepository.findByEmail(existingCustomer.getEmail())).thenReturn(Optional.of(existingCustomer));

        assertThrows(UserFoundException.class, () -> {
            createCustomerUseCase.execute(existingCustomer);
        });
    }
}
