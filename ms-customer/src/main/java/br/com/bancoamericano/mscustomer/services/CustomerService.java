package br.com.bancoamericano.mscustomer.services;

import br.com.bancoamericano.mscustomer.exception.exceptions.CustomerNotFoundException;
import br.com.bancoamericano.mscustomer.models.entities.Customer;
import br.com.bancoamericano.mscustomer.repositories.CustomerRepository;
import br.com.bancoamericano.mscustomer.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    //private final ModelMapper modelMapper;
    private final S3Util util;



    public Customer createCustomer(Customer customer, File file) throws IOException {
        log.info("Creating one person!");
        customer.setPoints(0L);
        String fileUrl = util.uploadFile(file.getName(), file);
        customer.setUrl_photo(fileUrl);
        return customerRepository.save(customer);
    }


    public Customer getCustomerById(Long id){
        log.info("Finding one customer by id");
        return customerRepository.findById(id).orElseThrow(()->
                new CustomerNotFoundException(String.format("Customer with id %s not found", id)));
    }

    public void deleteCustomerById(Long id){
        log.info("Deleting one customer by id!");
        if (id == null)
            throw new CustomerNotFoundException(String.format("Customer with id %s not found or id is null", id));
        else
            customerRepository.deleteById(id);
    }

//    public void updateCustomer(Long id, CustomerUpdateDto dto){
//        log.info("Updating one customer by id");
//        Customer customerById = customerRepository.findById(id).orElseThrow(()->
//                        new CustomerNotFoundException(String.format("Customer with id %s not found", id)));
//        modelMapper.map(dto, customerById);
//        customerRepository.save(customerById);
//    }
}

