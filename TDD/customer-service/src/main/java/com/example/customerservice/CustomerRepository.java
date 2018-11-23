package com.example.customerservice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
