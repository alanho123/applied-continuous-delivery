package com.example.customerservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */
@RestController
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(path = "/customers/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Customer customerById(@PathVariable Long id) {
        return customerRepository.findOne(id);
    }

    @GetMapping("/customers")
    public Collection<Customer> customers() {
        return customerRepository.findAll();
    }
}
