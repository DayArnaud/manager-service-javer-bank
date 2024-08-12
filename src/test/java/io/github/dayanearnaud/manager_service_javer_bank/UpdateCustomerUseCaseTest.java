package io.github.dayanearnaud.manager_service_javer_bank;

import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserInfoFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.exceptions.UserNotFoundException;
import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.CalculateScoreUseCase;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.UpdateCustomerUseCase;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCustomerUseCaseTest {

    @InjectMocks
    private UpdateCustomerUseCase updateCustomerUseCase;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CalculateScoreUseCase calculateScoreUseCase;

    @Test
    @DisplayName("Should not allow update of customer with duplicated phone number")
    public void shouldNotAllowUpdateOfCustomerWithDuplicatedPhoneNumber() {
        CustomerEntity customerFirst = new CustomerEntity();
        customerFirst.setId(UUID.randomUUID());
        customerFirst.setName("Some User");
        customerFirst.setEmail("some@email.com");
        customerFirst.setPhone(11999999999L);
        customerFirst.setAccount_holder(true);
        customerFirst.setBalance(5555.0);

        CustomerEntity customerSecond = new CustomerEntity();
        customerSecond.setId(UUID.randomUUID());
        customerSecond.setName("Updated User");
        customerSecond.setEmail("updated@email.com");
        customerSecond.setPhone(11999999999L);
        customerSecond.setAccount_holder(true);
        customerSecond.setBalance(4444.0);

        when(customerRepository.findById(customerSecond.getId())).thenReturn(Optional.of(customerSecond));

        when(customerRepository.findByPhone(customerSecond.getPhone())).thenReturn(Optional.of(customerFirst));

        assertThrows(UserInfoFoundException.class, () -> {
            updateCustomerUseCase.execute(customerSecond.getId(), customerSecond);
        });
    }

    @Test
    @DisplayName("Should not allow update of customer with duplicated email")
    public void shouldNotAllowUpdateOfCustomerWithDuplicatedEmail() {
        CustomerEntity customerFirst = new CustomerEntity();
        customerFirst.setId(UUID.randomUUID());
        customerFirst.setName("Some User");
        customerFirst.setEmail("duplicate@email.com");
        customerFirst.setPhone(11999999999L);
        customerFirst.setAccount_holder(true);
        customerFirst.setBalance(5555.0);

        CustomerEntity customerSecond = new CustomerEntity();
        customerSecond.setId(UUID.randomUUID());
        customerSecond.setName("Updated User");
        customerSecond.setEmail("duplicate@email.com");
        customerSecond.setPhone(11999999998L);
        customerSecond.setAccount_holder(true);
        customerSecond.setBalance(4444.0);

        when(customerRepository.findById(customerSecond.getId())).thenReturn(Optional.of(customerSecond));

        when(customerRepository.findByEmail(customerSecond.getEmail())).thenReturn(Optional.of(customerFirst));

        assertThrows(UserInfoFoundException.class, () -> {
            updateCustomerUseCase.execute(customerSecond.getId(), customerSecond);
        });
    }

    @Test
    @DisplayName("Should successfully update customer with valid fields")
    public void shouldSuccessfullyUpdateCustomerWithValidFields() {
        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setId(UUID.randomUUID());
        existingCustomer.setName("Original User");
        existingCustomer.setEmail("original@email.com");
        existingCustomer.setPhone(11999999999L);
        existingCustomer.setAccount_holder(true);
        existingCustomer.setBalance(5555.0);

        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer .setId(existingCustomer.getId());
        updatedCustomer .setName("Updated User");
        updatedCustomer .setEmail("updated@email.com");
        updatedCustomer .setPhone(11888888888L);
        updatedCustomer .setAccount_holder(true);
        updatedCustomer .setBalance(4444.0);

        when(customerRepository.findById(updatedCustomer.getId())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(CustomerEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        double expectedCreditScore = 444.4;
        when(calculateScoreUseCase.execute(existingCustomer.getId())).thenReturn(expectedCreditScore);
        CustomerEntity result = updateCustomerUseCase.execute(updatedCustomer.getId(), updatedCustomer);

        assertEquals("Updated User", result.getName());
        assertEquals("updated@email.com", result.getEmail());
        assertEquals(11888888888L, result.getPhone());
        assertEquals(true, result.getAccount_holder());
        assertEquals(4444.0, result.getBalance());
        assertEquals(444.4, result.getCredit_score());

        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("Shpuld not update customer if it does not exist")
    public void shouldNotUpdateCustomerIfItDoesNotExist() {
        UUID inexistingCustomerId = UUID.randomUUID();

        CustomerEntity inexistingCustomer = new CustomerEntity();
        inexistingCustomer.setId(inexistingCustomerId);
        inexistingCustomer.setName("Inexisting Customer");
        inexistingCustomer.setEmail("inexisting@email.com");
        inexistingCustomer.setPhone(11555555555L);
        inexistingCustomer.setAccount_holder(true);
        inexistingCustomer.setBalance(8000.0);

        when(customerRepository.findById(inexistingCustomerId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            updateCustomerUseCase.execute(inexistingCustomerId, inexistingCustomer);
        });

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("Should correctly update credit score when balance is updated")
    public void shouldCorrectlyUpdateCreditScoreWhenBalanceIsUpdated() {
        UUID existingCustomerId = UUID.randomUUID();

        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setId(existingCustomerId);
        existingCustomer.setName("Existing User");
        existingCustomer.setEmail("existing@email.com");
        existingCustomer.setPhone(11999999999L);
        existingCustomer.setAccount_holder(true);
        existingCustomer.setBalance(5555.0);
        existingCustomer.setCredit_score(555.5);

        CustomerEntity updatedCustomer = new CustomerEntity();
        updatedCustomer.setId(existingCustomer.getId());
        updatedCustomer.setName("Existing User");
        updatedCustomer.setEmail("existing@email.com");
        updatedCustomer.setPhone(11999999999L);
        updatedCustomer.setAccount_holder(true);
        updatedCustomer.setBalance(4444.0);

        double newCreditScore = 444.4;

        when(customerRepository.findById(existingCustomerId)).thenReturn(Optional.of(existingCustomer));
        when(calculateScoreUseCase.execute(existingCustomerId)).thenReturn(newCreditScore);
        when(customerRepository.save(any(CustomerEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CustomerEntity result = updateCustomerUseCase.execute(existingCustomerId, updatedCustomer);

        assertEquals(newCreditScore, result.getCredit_score());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }
}
