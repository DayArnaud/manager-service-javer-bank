package io.github.dayanearnaud.manager_service_javer_bank;

import io.github.dayanearnaud.manager_service_javer_bank.model.CustomerEntity;
import io.github.dayanearnaud.manager_service_javer_bank.repository.CustomerRepository;
import io.github.dayanearnaud.manager_service_javer_bank.useCases.GetAllCustomersUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllCustomersUseCaseTest {

    @InjectMocks
    private GetAllCustomersUseCase getAllCustomersUseCase;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should return empty list when no customers exist")
    public void shouldReturnEmptyListWhenNoCustomersExist() {

        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerEntity> result = getAllCustomersUseCase.execute();

        assertTrue(result.isEmpty());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return list with one customer when one customer exists")
    public void shouldReturnListWithOneCustomerWhenOneCustomerExists() {

        CustomerEntity customer = new CustomerEntity();

        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));

        List<CustomerEntity> result = getAllCustomersUseCase.execute();

        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return list with multiple customers when multiple customers exist")
    public void shouldReturnListWithMultipleCustomersWhenMultipleCustomersExist(){

        CustomerEntity customerFirst = new CustomerEntity();
        CustomerEntity customerSecond = new CustomerEntity();

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customerFirst, customerSecond));

        List<CustomerEntity> result = getAllCustomersUseCase.execute();

        assertEquals(2, result.size());
        assertEquals(customerFirst, result.get(0));
        assertEquals(customerSecond, result.get(1));

        verify(customerRepository, times(1)).findAll();
    }
}
