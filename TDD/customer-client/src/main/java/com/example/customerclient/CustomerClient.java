package com.example.customerclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */
public class CustomerClient {

    private final RestTemplate restTemplate;
    private final String uri;

    public CustomerClient(RestTemplate restTemplate, String uri) {
        this.restTemplate = restTemplate;
        this.uri = uri;
    }


    public Collection<Customer> getCustomers() {

        ParameterizedTypeReference<Collection<Customer>> ptr = new ParameterizedTypeReference<Collection<Customer>>() {
        };

        return restTemplate.exchange(uri +"/customers", HttpMethod.GET, null, ptr).getBody();
    }

    public Customer getCustomerById(Long id) {
        return restTemplate.exchange(uri +"/customers/{id}", HttpMethod.GET, null, Customer.class, id).getBody();
    }
}
