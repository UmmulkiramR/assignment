package com.example.assignment.controller;

import com.example.assignment.exceptions.CustomerNotFoundException;
import com.example.assignment.model.Customer;
import com.example.assignment.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer/")
@RequiredArgsConstructor
@Tag(name = "Customer API", description = "API for managing customers")
public class CustomerController extends BaseController {

    private final CustomerRepository customerRepository;


    @GetMapping(value="/all")
    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }


    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get a customer by ID",
            description = "Retrieves a specific customer by their unique ID"
    )
    public Customer getCustomer(@PathVariable(name = "id") UUID id){
        Customer customer = customerRepository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("There is no customer with id: %s", id.toString())));
        return customer;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Create a new customer", description = "Creates a new customer record")
    public Customer createCustomer(@RequestBody CustomerRequest request) {
        request.validateFirstName().validateLastName().validateMiddleName().validateEmail().validatePhone();
        Customer customer = Customer.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName()).build();
        return customerRepository.save(customer);

    }

    @PutMapping(value = "/update/{id}")
    @Operation(
            summary = "Update a customer by ID",
            description = "Updates the details of an existing customer using their unique ID"
    )
    public Customer updateCustomer(@RequestBody CustomerRequest request, @PathVariable(name = "id") UUID id) {
        request.validateFirstName().validateLastName().validateMiddleName().validateEmail().validatePhone();
        Customer customer = customerRepository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("There is no customer with id: %s", id.toString())));
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setMiddleName(request.getMiddleName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        return customerRepository.save(customer);
    }

    @DeleteMapping(value = "remove/{id}")
    @Operation(
            summary = "Delete a customer by ID",
            description = "Deletes a customer record by their unique ID"
    )
    public String deleteCustomer(@PathVariable(name = "id") UUID id){
        Customer customer = customerRepository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException(String.format("There is no customer with id: %s", id.toString())));
        customerRepository.delete(customer);
        return String.format("Customer with id: %s deleted", id);


    }
}


// check validations of request
// phone compsite type
// return custome message instead of internal server error
