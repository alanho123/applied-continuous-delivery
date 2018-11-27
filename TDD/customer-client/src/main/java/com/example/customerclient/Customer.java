package com.example.customerclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String firstName, lastName, email;
}
