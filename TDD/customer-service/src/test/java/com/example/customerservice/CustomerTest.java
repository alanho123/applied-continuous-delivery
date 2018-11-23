package com.example.customerservice;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */
public class CustomerTest {

    private Validator validator;
    @Before
    public void before() throws Throwable {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        this.validator = localValidatorFactoryBean.getValidator();
    }

    @Test
    public void newInstanceWithValidValuesShouldReturnARecord() {
        Customer customer = new Customer(1L, "first", "last", "email@email.com");

        // JUnit
        assertEquals(customer.getId(), (Long)1L);
        assertEquals(customer.getFirstName(), "first");
        assertEquals(customer.getLastName(), "last");
        assertEquals(customer.getEmail(), "email@email.com");

        assertThat(customer.getFirstName(), is("first"));
        assertThat(customer.getLastName(), is("last"));
        assertThat(customer.getEmail(), is("email@email.com"));

        // AssertJ
        then(customer.getFirstName()).isEqualToIgnoringCase("first");
        then(customer.getLastName()).isEqualToIgnoringCase("last");
        then(customer.getEmail()).isEqualToIgnoringCase("email@email.com");
        then(customer.getId()).isNotNull();
    }

    @Test
    public void newInstanceWithInvalidConstraintsShouldProduceConstraintViolations() {
        // Validation
        Customer customer = new Customer(null, null, null, null);
        Set<ConstraintViolation<Customer>> validate = validator.validate(customer);
        then(validate.size()).isEqualTo(3);
    }
}
