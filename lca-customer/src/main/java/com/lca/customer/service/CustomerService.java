package com.lca.customer.service;

import com.lca.customer.exception.CustomerAlreadyExists;
import com.lca.customer.exception.CustomerHasNotBeenDeleted;
import com.lca.customer.exception.CustomerNotFound;
import com.lca.customer.model.Customer;
import com.lca.customer.model.ProductDetail;
import com.lca.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductDetailService productDetailService;

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
            Customer customerSaved = customerRepository.save(customer);
            customerSaved.setDetail(
                    productDetailService.saveProductDetail(new ProductDetail("<h1>Texto de prueba</h1>", customer))
            );
            return customerRepository.save(customerSaved);
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
            Optional<Customer> customerSearchCIF = customerRepository.findByCifAndIdNot(customer.getCif(), customer.getId());
            if (!customerSearchCIF.isPresent())
            {
                return customerRepository.save(customer);
            }
            else
            {
                throw new CustomerAlreadyExists("The customer with CIF: " + customer.getCif() + " already exists.");
            }
        }
        else
        {
            throw new CustomerNotFound("The customer with id: " + customer.getId() + " does not exist.");
        }
    }

    public Map<String, Object> deleteCustomer(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent())
        {
            customerRepository.delete(customer.get());

            customer = customerRepository.findById(id);
            if (!customer.isPresent())
            {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("result", "The customer with id: " + id + " has been deleted.");
                return result;
            }
            else
            {
                throw new CustomerHasNotBeenDeleted("The customer has not been deleted.");
            }
        }
        else
        {
            throw new CustomerNotFound("The customer with id: " + id + " does not exist.");
        }
    }
}
