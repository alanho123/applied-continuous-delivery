package com.example.customerservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

/**
 * @author <a href="mailto:alanho123@gmail.com">Jason Ho</a>
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc       //only web testing
@Slf4j
public class CustomerRestControllerTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void customerByIdShouldReturnAJsonCustomer() throws Exception {

        Mockito.when(this.customerRepository.findOne(1L))
                .thenReturn(new Customer(1L, "first", "last", "email@email.com"));


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("@.id").value(1L))
                .andReturn();

        log.info("customer - {}", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void customersShouldReturnAllCustomers() throws Exception {
        Mockito.when(this.customerRepository.findAll()).thenReturn(Arrays.asList(
                new Customer(1L, "first", "last", "email@email.com"),
                new Customer(2L, "first", "last", "email@email.com")
        ));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].firstName").value("first"))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[1].id").value(2L))
                .andReturn();
        log.info("customers - {}", mvcResult.getResponse().getContentAsString());

    }
}
