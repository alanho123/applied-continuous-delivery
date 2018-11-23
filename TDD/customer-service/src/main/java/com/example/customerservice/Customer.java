package com.example.customerservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity  // for repository test
public class Customer {

    @Id             // for repository test
    @GeneratedValue // for repository test
    private Long id;

    @NotNull
    private String firstName, lastName;

    @NotNull
    @Email
    private String email;
}
