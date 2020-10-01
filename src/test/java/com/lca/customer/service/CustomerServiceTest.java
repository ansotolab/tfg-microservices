package com.lca.customer.service;

import com.lca.customer.exception.CustomerAlreadyExists;
import com.lca.customer.exception.CustomerHasNotBeenDeleted;
import com.lca.customer.exception.CustomerNotFound;
import com.lca.customer.model.Customer;
import com.lca.customer.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    /*
        SAVE CASES
     */
    @Test
    public void shouldSaveCustomerSuccessfully() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findByCif(customer.getCif())).willReturn(Optional.empty());
        given(customerRepository.save(customer)).willAnswer(invocation -> invocation.getArgument(0));

        Customer savedCustomer = customerService.saveCustomer(customer);

        assertThat(savedCustomer).isNotNull();

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    public void shouldThrowErrorWhenSaveCustomerWithExistingCIF() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findByCif(customer.getCif())).willReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyExists.class, () -> {
            customerService.saveCustomer(customer);
        });

        verify(customerRepository, never()).save(any(Customer.class));
    }

    /*
        UPDATE CASES
     */
    @Test
    public void shouldUpdateCustomerSuccessfully() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(customerRepository.findByCifAndIdNot(customer.getCif(), customer.getId())).willReturn(Optional.empty());
        given(customerRepository.save(customer)).willAnswer(invocation -> invocation.getArgument(0));

        Customer savedCustomer = customerService.updateCustomer(customer);

        assertThat(savedCustomer).isNotNull();

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    public void shouldThrowErrorWhenUpdateCustomerWithNotExistingID() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findById(customer.getId())).willReturn(Optional.empty());

        assertThrows(CustomerNotFound.class, () -> {
            customerService.updateCustomer(customer);
        });

        verify(customerRepository, never()).save(any(Customer.class));
        verify(customerRepository, never()).findByCifAndIdNot(anyString(), anyLong());
    }

    @Test
    public void shouldThrowErrorWhenUpdateCustomerWithExistingCIF() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));
        given(customerRepository.findByCifAndIdNot(customer.getCif(), customer.getId())).willReturn(Optional.of(customer));

        assertThrows(CustomerAlreadyExists.class, () -> {
            customerService.updateCustomer(customer);
        });

        verify(customerRepository, never()).save(any(Customer.class));
    }

    /*
        DELETE CASES
     */
    @Test
    public void shouldDeleteCustomerSuccessfully() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findById(1L))
                .willReturn(Optional.of(customer))
                .willReturn(Optional.empty());

        Map<String, Object> expextedResponse = customerService.deleteCustomer(customer.getId());
        Map<String, Object> realResponse = new HashMap<String, Object>();
        realResponse.put("result", "The customer with id: " + customer.getId() + " has been deleted.");

        assertTrue(expextedResponse.equals(realResponse));
    }

    @Test
    public void shouldThrowErrorWhenDeleteCustomerWithNotExistingID() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(CustomerNotFound.class, () -> {
            customerService.deleteCustomer(customer.getId());
        });

        verify(customerRepository, never()).delete(any(Customer.class));
    }

    @Test
    public void shouldThrowErrorWhenNotDeleteCustomer() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findById(1L))
                .willReturn(Optional.of(customer))
                .willReturn(Optional.of(customer));

        assertThrows(CustomerHasNotBeenDeleted.class, () -> {
            customerService.deleteCustomer(customer.getId());
        });
    }

    /*
        GET CASES
     */
    @Test
    public void shouldGetCustomerSuccessfully() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findById(1L)).willReturn(Optional.of(customer));

        Customer gettedCustomer = customerService.getCustomer(customer.getId());

        assertThat(gettedCustomer).isNotNull();
    }

    @Test
    public void shouldThrowErrorWhenGetCustomerWithNotExistingID() {
        Customer customer = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");

        given(customerRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(CustomerNotFound.class, () -> {
            customerService.getCustomer(customer.getId());
        });
    }

    @Test
    public void shouldGetAllCustomerSuccessfully() {
        Customer customer1 = new Customer(1L,"A0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");
        Customer customer2 = new Customer(2L,"B0123456789", "La empresa S.L.", "Polígono Santa Luisa, Calle Constitución española, Nº 11, Planta 1º, Puerta A", "Torrejón de Ardoz", "01234", "España", "email@email.com", "987654321", "Pedro Colera, Luis Fernández", "El cliente suele pedir el 9 de marzo siempre.");
        List<Customer> expetedCustomerList = Arrays.asList(customer1, customer2);

        given(customerRepository.findAll()).willReturn(expetedCustomerList);

        List<Customer> realCustomerList = customerService.getAll();

        assertEquals(realCustomerList, expetedCustomerList);
    }
}
