package br.com.bancoamericano.mscustomer.services;

import br.com.bancoamericano.mscustomer.exception.exceptions.CustomerNotFoundException;
import br.com.bancoamericano.mscustomer.exception.exceptions.InvalidTypeFileException;
import br.com.bancoamericano.mscustomer.models.dto.CustomerUpdateDto;
import br.com.bancoamericano.mscustomer.models.entities.Customer;
import br.com.bancoamericano.mscustomer.repositories.CustomerRepository;
import br.com.bancoamericano.mscustomer.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    //private final ModelMapper modelMapper;
    private final S3Util util;

    @Value("${aws.s3.bucket}")
    private String AWS_BUCKET;

    public Customer createCustomer(Customer customer/*, MultipartFile file*/) throws IOException {
        log.info("Creating one person!");
        //String extensionFile = this.getFileExtension(file);
/*        if (!extensionFile.equals("png") && !extensionFile.equals("jpeg") && !extensionFile.equals("jpg"))
            throw new InvalidTypeFileException("Invalid type file");*/

/*        String filename = file.getOriginalFilename();
        String fileUrl = "https://" + AWS_BUCKET + ".s3.amazonaws.com/" + filename;
        util.uploadFile(filename, file.getInputStream());
        customer.setUrl_photo(fileUrl);*/
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

//    public void updateCustomer(Long id, CustomerUpdateDto dto){
//        log.info("Updating one customer by id");
//        Customer customerById = customerRepository.findById(id).orElseThrow(()->
//                        new CustomerNotFoundException(String.format("Customer with id %s not found", id)));
//        modelMapper.map(dto, customerById);
//        customerRepository.save(customerById);
//    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf(".");
        return index == -1 || index == fileName.length() -1 ? "" : fileName.substring(index + 1);
    }

}

