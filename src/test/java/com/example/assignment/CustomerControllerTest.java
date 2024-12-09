package com.example.assignment;

import com.example.assignment.controller.CustomerController;
import com.example.assignment.exceptions.CustomerNotFoundException;
import com.example.assignment.model.Customer;
import com.example.assignment.repository.CustomerRepository;
import com.example.assignment.controller.CustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerController customerController;

    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();
    }

    @Test
    void getAllCustomers_ReturnsCustomerList() {
        List<Customer> customers = Collections.singletonList(sampleCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerController.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleCustomer.getFirstName(), result.get(0).getFirstName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomer_ValidId_ReturnsCustomer() {
        when(customerRepository.findCustomerById(sampleCustomer.getId())).thenReturn(Optional.of(sampleCustomer));

        Customer result = customerController.getCustomer(sampleCustomer.getId());

        assertNotNull(result);
        assertEquals(sampleCustomer.getId(), result.getId());
        verify(customerRepository, times(1)).findCustomerById(sampleCustomer.getId());
    }

    @Test
    void getCustomer_InvalidId_ThrowsCustomerNotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(customerRepository.findCustomerById(invalidId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerController.getCustomer(invalidId));
        verify(customerRepository, times(1)).findCustomerById(invalidId);
    }

    @Test
    void createCustomer_ValidRequest_ReturnsSavedCustomer() {
        CustomerRequest request = new CustomerRequest("John", "Doe", null, "john.doe@example.com", "1234567890");
        when(customerRepository.save(any(Customer.class))).thenReturn(sampleCustomer);

        Customer result = customerController.createCustomer(request);

        assertNotNull(result);
        assertEquals(sampleCustomer.getEmail(), result.getEmail());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer_ValidRequest_UpdatesCustomer() {
        CustomerRequest request = new CustomerRequest("Jane", "Smith", null, "jane.smith@example.com", "9876543210");
        when(customerRepository.findCustomerById(sampleCustomer.getId())).thenReturn(Optional.of(sampleCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(sampleCustomer);

        Customer result = customerController.updateCustomer(request, sampleCustomer.getId());

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("jane.smith@example.com", result.getEmail());
        verify(customerRepository, times(1)).findCustomerById(sampleCustomer.getId());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void deleteCustomer_ValidId_DeletesCustomer() {
        when(customerRepository.findCustomerById(sampleCustomer.getId())).thenReturn(Optional.of(sampleCustomer));
        doNothing().when(customerRepository).delete(sampleCustomer);

        String result = customerController.deleteCustomer(sampleCustomer.getId());

        assertNotNull(result);
        assertTrue(result.contains(sampleCustomer.getId().toString()));
        verify(customerRepository, times(1)).findCustomerById(sampleCustomer.getId());
        verify(customerRepository, times(1)).delete(sampleCustomer);
    }

    @Test
    void deleteCustomer_InvalidId_ThrowsCustomerNotFoundException() {
        UUID invalidId = UUID.randomUUID();
        when(customerRepository.findCustomerById(invalidId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerController.deleteCustomer(invalidId));
        verify(customerRepository, times(1)).findCustomerById(invalidId);
    }
}
