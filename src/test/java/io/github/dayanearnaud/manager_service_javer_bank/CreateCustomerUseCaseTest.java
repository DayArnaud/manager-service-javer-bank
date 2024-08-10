package io.github.dayanearnaud.manager_service_javer_bank;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserInfoFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.CalculateScoreUseCase;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.CreateCustomerUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateCustomerUseCaseTest {

    @InjectMocks
    private CreateCustomerUseCase createCustomerUseCase;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CalculateScoreUseCase calculateScoreUseCase;

    @Test
    @DisplayName("Should not create customer if already exists")
    public void shouldNotCreateCustomerIfAlreadyExists() {
        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setEmail("test@example.com");

        when(customerRepository.findByEmail(existingCustomer.getEmail())).thenReturn(Optional.of(existingCustomer));

        assertThrows(UserInfoFoundException.class, () -> {
            createCustomerUseCase.execute(existingCustomer);
        });
    }

    @Test
    @DisplayName("Should not allow creation of customer with duplicated phone number")
    public void shouldNotAllowCreationOfCustomerWithDuplicatedPhoneNumber() {
        CustomerEntity customerFirst = new CustomerEntity();
        customerFirst.setEmail("new@email.com");
        customerFirst.setPhone(11999999999L);
        customerFirst.setAccount_number("12345-2");
        customerFirst.setBalance(5555.0);
        when(customerRepository.findByPhone(customerFirst.getPhone())).thenReturn(Optional.of(customerFirst));

        CustomerEntity customerSecond = new CustomerEntity();
        customerSecond.setEmail("other@email.com");
        customerSecond.setPhone(11999999999L);
        customerSecond.setAccount_number("12345-3");
        customerSecond.setBalance(4444.0);

        assertThrows(UserInfoFoundException.class, () -> {
            createCustomerUseCase.execute(customerSecond);
        });
    }

    @Test
    @DisplayName("Should correctly calculate and set credit score for customer")
    public void shouldCorrectlyCalculateAndSetCreditScoreForCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setPhone(11111111111L);
        customer.setAccount_number("12345-2");
        customer.setBalance(8000.0);
        customer.setEmail("new@email.com");

        UUID customerId = UUID.randomUUID();
        when(calculateScoreUseCase.execute(customerId)).thenReturn(800.0);

        when(customerRepository.save(any(CustomerEntity.class))).thenAnswer(invocation -> {
           CustomerEntity savedCustomer = invocation.getArgument(0);
           savedCustomer.setId(customerId);
           return savedCustomer;
        });
        CustomerEntity createdCustomer = createCustomerUseCase.execute(customer);

        assertEquals(800.0, createdCustomer.getCredit_score());

        verify(customerRepository, times(2)).save(createdCustomer);
    }

    @Test
    @DisplayName("Should create customer with all valid fields")
    public void shouldCreateCustomerWithAllValidFields() {

        CustomerEntity customer = new CustomerEntity();
        customer.setName("New User");
        customer.setPhone(11111111111L);
        customer.setAccount_holder(true);
        customer.setAccount_number("12345-2");
        customer.setBalance(8000.0);
        customer.setEmail("new@email.com");

        UUID customerId = UUID.randomUUID();
        when(calculateScoreUseCase.execute(any(UUID.class))).thenReturn(800.0);

        when(customerRepository.save(any(CustomerEntity.class))).thenAnswer(invocation -> {
            CustomerEntity savedCustomer = invocation.getArgument(0);
            savedCustomer.setId(customerId);
            return savedCustomer;
        });

        CustomerEntity createdCustomer = createCustomerUseCase.execute(customer);

        assertEquals("New User", createdCustomer.getName());
        assertEquals(11111111111L, createdCustomer.getPhone());
        assertEquals(true, createdCustomer.getAccount_holder());
        assertEquals("12345-2", createdCustomer.getAccount_number());
        assertEquals(8000.0, createdCustomer.getBalance());
        assertEquals("new@email.com", createdCustomer.getEmail());
        assertEquals(800.0, createdCustomer.getCredit_score());

        verify(customerRepository, times(2)).save(any(CustomerEntity.class));
    }
}
