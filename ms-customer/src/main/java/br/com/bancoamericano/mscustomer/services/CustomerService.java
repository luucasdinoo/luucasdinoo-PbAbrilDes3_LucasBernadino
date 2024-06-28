package br.com.bancoamericano.mscustomer.services;

import br.com.bancoamericano.mscustomer.exception.exceptions.CustomerNotFoundException;
import br.com.bancoamericano.mscustomer.models.entities.Customer;
import br.com.bancoamericano.mscustomer.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        log.info("Creating one person!");
        customer.setPoints(0L);
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id){
        log.info("Finding one customer by id");
        return customerRepository.findById(id).orElseThrow(()->
                new CustomerNotFoundException(String.format("Customer with id %s not found", id)));
    }

    public void deleteCustomerById(Long id){
        log.info("Deleting one customer by id!");
        if (id != null)
            customerRepository.deleteById(id);
        else
            throw new CustomerNotFoundException(String.format("Customer with id %s not found or id is null", id));
    }
}

