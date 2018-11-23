package com.example.customerservice;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */

@SpringBootTest(classes = CustomerServiceApplication.class)
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void saveShouldMapCorrectly() throws Exception {
        Customer customer = new Customer(null, "first", "last", "email@email.com");
        Customer saved = testEntityManager.persistAndFlush(customer);

        then(saved.getId()).isNotNull();
        then(saved.getFirstName()).isNotBlank();
        then(saved.getFirstName()).isEqualToIgnoringCase("first");
        then(saved.getLastName()).isNotBlank();
        then(saved.getLastName()).isEqualToIgnoringCase("last");
        then(saved.getEmail()).isNotBlank();
        then(saved.getEmail()).isEqualToIgnoringCase("email@email.com");
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void repositorySaveShouldMapCorrectly() {
        Customer customer = new Customer(null, "first", "last", "email@email.com");
        Customer saved = customerRepository.save(customer);

        then(saved.getId()).isNotNull();
        then(saved.getFirstName()).isNotBlank();
        then(saved.getFirstName()).isEqualToIgnoringCase("first");
        then(saved.getLastName()).isNotBlank();
        then(saved.getLastName()).isEqualToIgnoringCase("last");
        then(saved.getEmail()).isNotBlank();
        then(saved.getEmail()).isEqualToIgnoringCase("email@email.com");
    }

    @Test
    public void newInstanceWithInvalidParametersShouldResultInConstraintViolations() throws Exception {
        this.expectedException.expect(ConstraintViolationException.class);
        Customer customer = new Customer(null, null, null, null);
        this.customerRepository.save(customer);
    }
}
