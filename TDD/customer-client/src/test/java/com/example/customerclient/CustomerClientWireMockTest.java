package com.example.customerclient;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import wiremock.org.apache.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */
@SpringBootTest(classes = CustomerClientApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 8080)
public class CustomerClientWireMockTest {
    private Resource customers = new ClassPathResource("customers.json");
    private Resource customerById = new ClassPathResource("customer-by-id.json");

    @Autowired
    private CustomerClient client;

    @Test
    public void customersShouldReturnAllCustomers() {
        WireMock.stubFor(WireMock.get("/customers")
                .willReturn(
                        WireMock.aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .withStatus(HttpStatus.OK.value())
                                .withBody(asJson(customers))
                ));


        Collection<Customer> customers = client.getCustomers();
        then(customers.size()).isEqualTo(2);
    }

    private String asJson(Resource resource) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            Stream<String> lines = br.lines();
            return lines.collect(Collectors.joining());
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }

    @Test
    public void customerByIdShouldReturnACustomer() {

        WireMock.stubFor(WireMock.get("/customers/1")
                .willReturn(
                        WireMock.aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .withStatus(HttpStatus.OK.value())
                                .withBody(asJson(customerById))
                ));
        Customer customer = client.getCustomerById(1L);
        then(customer.getFirstName()).isEqualToIgnoringCase("first");
        then(customer.getLastName()).isEqualToIgnoringCase("last");
        then(customer.getEmail()).isEqualToIgnoringCase("email@email.com");
        then(customer.getId()).isEqualTo(1L);
    }
}
