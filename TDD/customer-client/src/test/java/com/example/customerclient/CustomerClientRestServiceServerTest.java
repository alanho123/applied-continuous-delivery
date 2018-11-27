package com.example.customerclient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */
@SpringBootTest(classes = CustomerClientApplication.class)
@RunWith(SpringRunner.class)
public class CustomerClientRestServiceServerTest {

    private Resource customers = new ClassPathResource("customers.json");
    private Resource customerById = new ClassPathResource("customer-by-id.json");
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private CustomerClient client;

    @Autowired
    private RestTemplate restTemplate;


    @Before
    public void before() {
        this.mockRestServiceServer = MockRestServiceServer.bindTo(this.restTemplate).build();
    }

    @Test
    public void customersShouldReturnAllCustomers() {
        mockRestServiceServer
                .expect(ExpectedCount.manyTimes(), requestTo("http://localhost:8080/customers"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(customers, MediaType.APPLICATION_JSON_UTF8));
        Collection<Customer> customers = client.getCustomers();
        then(customers.size()).isEqualTo(2);

        this.mockRestServiceServer.verify();
    }

    @Test
    public void customerByIdShouldReturnACustomer() {
        mockRestServiceServer
                .expect(ExpectedCount.manyTimes(), requestTo("http://localhost:8080/customers/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(customerById, MediaType.APPLICATION_JSON_UTF8));
        Customer customer = client.getCustomerById(1L);
        then(customer.getFirstName()).isEqualToIgnoringCase("first");
        then(customer.getLastName()).isEqualToIgnoringCase("last");
        then(customer.getEmail()).isEqualToIgnoringCase("email@email.com");
        then(customer.getId()).isEqualTo(1L);

        this.mockRestServiceServer.verify();
    }
}
