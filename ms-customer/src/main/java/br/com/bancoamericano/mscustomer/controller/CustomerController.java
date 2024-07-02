package br.com.bancoamericano.mscustomer.controller;

import br.com.bancoamericano.mscustomer.mapper.CustomerMapper;
import br.com.bancoamericano.mscustomer.models.dto.CustomerCreateDto;
import br.com.bancoamericano.mscustomer.models.dto.CustomerResponseDto;
import br.com.bancoamericano.mscustomer.models.dto.CustomerUpdateDto;
import br.com.bancoamericano.mscustomer.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerCreateDto dto/*,
                                                              @RequestParam("file") MultipartFile file*/) throws IOException {
        var customer = customerService.createCustomer(CustomerMapper.toCustomer(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.toDto(customer));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable Long id) {
        var customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(CustomerMapper.toDto(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") Long id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateDto dto){
//        customerService.updateCustomer(id,dto);
//        return ResponseEntity.noContent().build();
//    }
}
