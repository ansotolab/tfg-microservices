package com.lca.customer.service;

import com.lca.customer.exception.CustomerAlreadyExists;
import com.lca.customer.exception.CustomerHasNotBeenDeleted;
import com.lca.customer.exception.CustomerNotFound;
import com.lca.customer.model.Customer;
import com.lca.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomer(long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFound("The customer with id: " + id + " does not exist."));
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().iterator().forEachRemaining(customers::add);
        return customers;
    }

    public Customer saveCustomer(Customer customer) {
        Optional<Customer> customerSearch = customerRepository.findByCif(customer.getCif());
        if (!customerSearch.isPresent())
        {
            return customerRepository.save(customer);
        }
        else
        {
            throw new CustomerAlreadyExists("The customer with CIF: " + customer.getCif() + " already exists.");
        }
    }

    public Customer updateCustomer(Customer customer) {
        Optional<Customer> customerSearch = customerRepository.findById(customer.getId());
        if (customerSearch.isPresent())
        {
            return customerRepository.save(customer);
        }
        else
        {
            throw new CustomerNotFound("The customer with id: " + customer.getId() + " does not exist.");
        }
    }

    public Map<String, Object> deleteCustomer(Customer customer) {
        Optional<Customer> customerSearch = customerRepository.findById(customer.getId());
        if (customerSearch.isPresent())
        {
            customerRepository.delete(customer);

            customerSearch = customerRepository.findById(customer.getId());
            if (!customerSearch.isPresent())
            {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("result", "The customer with id: " + customer.getId() + " has been deleted.");
                return result;
            }
            else
            {
                throw new CustomerHasNotBeenDeleted("The customer has not been deleted.");
            }
        }
        else
        {
            throw new CustomerNotFound("The customer with id: " + customer.getId() + " does not exist.");
        }
    }
}
