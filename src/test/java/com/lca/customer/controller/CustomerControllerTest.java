package com.lca.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lca.customer.exception.CustomerAlreadyExists;
import com.lca.customer.exception.CustomerHasNotBeenDeleted;
import com.lca.customer.exception.CustomerNotFound;
import com.lca.customer.model.Customer;
import com.lca.customer.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
//@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    private List<Customer> customerList;

    @Before
    public void setUp() {
        this.customerList = new ArrayList<>();
        this.customerList.add(new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre."));
        this.customerList.add(new Customer(2L,"B0123456789", "La cooperativa Coop.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre."));
        this.customerList.add(new Customer(3L,"C0123456789", "La sociedad S.A.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre."));
        this.customerList.add(new Customer(4L,"D0123456789", "La PYME Autónomo.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre."));
        this.customerList.add(new Customer(5L,"E0123456789", "La organización O.N.G.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre."));
    }

    /*
        GET CASES
     */
    @Test
    public void shouldGetAllCustomers() {
        given(customerService.getAll()).willReturn(customerList);

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get("/customers").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(customerList.size())));
        });
    }

    @Test
    public void shouldGetEmptyCustomerList() {
        given(customerService.getAll()).willReturn(new ArrayList<Customer>());

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get("/customers").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()", is(0)));
        });
    }

    @Test
    public void shouldGetOneCustomer() {
        given(customerService.getCustomer(1L)).willReturn(this.customerList.get(0));

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get("/customers/1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.cif", is("A0123456789")))
                    .andExpect(jsonPath("$.name", is("La empresa S.L.")));
        });
    }

    @Test
    public void shouldThrowErrorWhenGetCustomerThatNotExists() {
        given(customerService.getCustomer(1L)).willThrow(CustomerNotFound.class);

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get("/customers/1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        });
    }

    /*
        SAVE CASES
     */
    @Test
    public void shouldSaveCustomerSuccesfully() {
        Customer customer = new Customer(10L,"Z0123456789", "La tienda S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerService.saveCustomer(customer)).willReturn(customer);

        assertDoesNotThrow(() -> {

            this.mockMvc.perform(post("/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json(objectMapper.writeValueAsString(customer)));
        });
    }

    @Test
    public void shouldThrowErrorWhenSaveCustomerWithExistingCIF() {
        Customer customer = new Customer(10L,"Z0123456789", "La tienda S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerService.saveCustomer(customer)).willThrow(CustomerAlreadyExists.class);

        assertDoesNotThrow(() -> {

            this.mockMvc.perform(post("/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)))
                    .andExpect(status().isBadRequest());
        });
    }

    /*
        UPDATE CASES
     */
    @Test
    public void shouldUpdateCustomerSuccesfully() {
        Customer customer = new Customer(10L,"Z0123456789", "La tienda S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerService.updateCustomer(customer)).willReturn(customer);

        assertDoesNotThrow(() -> {

            this.mockMvc.perform(put("/customers/10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json(objectMapper.writeValueAsString(customer)));
        });
    }

    @Test
    public void shouldThrowErrorWhenUpdateCustomerThatNotExists() {
        Customer customer = new Customer(10L,"Z0123456789", "La tienda S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerService.updateCustomer(customer)).willThrow(CustomerNotFound.class);

        assertDoesNotThrow(() -> {

            this.mockMvc.perform(put("/customers/10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)))
                    .andExpect(status().isNotFound());
        });
    }

    @Test
    public void shouldThrowErrorWhenUpdateCustomerWithExistingID() {
        Customer customer = new Customer(10L,"Z0123456789", "La tienda S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerService.updateCustomer(customer)).willThrow(CustomerAlreadyExists.class);

        assertDoesNotThrow(() -> {

            this.mockMvc.perform(put("/customers/10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)))
                    .andExpect(status().isBadRequest());
        });
    }

    /*
        DELETE CASES
     */
    @Test
    public void shouldDeleteCustomerSuccessfully() {
        Customer customer = new Customer(10L,"Z0123456789", "La tienda S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "The customer with id: " + customer.getId() + " has been deleted.");

        given(customerService.deleteCustomer(10L)).willReturn(result);

        assertDoesNotThrow(() -> {

            this.mockMvc.perform(delete("/customers/10"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(result)));
        });
    }

    @Test
    public void shouldThrowErrorWhenDeleteCustomerThatNotExists() {
        Customer customer = new Customer(10L,"Z0123456789", "La tienda S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "The customer with id: " + customer.getId() + " has been deleted.");

        given(customerService.deleteCustomer(10L)).willReturn(result);

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(delete("/customers/10"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(result)));
        });
    }

    @Test
    public void shouldThrowErrorWhenNotDeleteCustomer() {

        given(customerService.deleteCustomer(10L)).willThrow(CustomerHasNotBeenDeleted.class);

        assertDoesNotThrow(() -> {
            this.mockMvc.perform(delete("/customers/10"))
                    .andExpect(status().isInternalServerError());
        });
    }
}
