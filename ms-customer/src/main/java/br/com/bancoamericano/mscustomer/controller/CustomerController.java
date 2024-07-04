package br.com.bancoamericano.mscustomer.controller;

import br.com.bancoamericano.mscustomer.mapper.CustomerMapper;
import br.com.bancoamericano.mscustomer.models.dto.CustomerCreateDto;
import br.com.bancoamericano.mscustomer.models.dto.CustomerResponseDto;
import br.com.bancoamericano.mscustomer.models.dto.CustomerUpdateDto;
import br.com.bancoamericano.mscustomer.services.CustomerService;
import br.com.bancoamericano.mscustomer.util.FileUtil;
import br.com.bancoamericano.mscustomer.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final S3Util util;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerCreateDto dto) throws IOException {
        try {
            File photo = FileUtil.convertToFile(dto.getBase64Photo(), dto.getEmail());
            var customer = customerService.createCustomer(CustomerMapper.toCustomer(dto), photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.toDto(customer));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateDto dto){
        customerService.updateCustomer(id,dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/points/{id}/{points}")
    public ResponseEntity<Void> updatePoints(@PathVariable("id") Long id, @PathVariable("points") Long points){
        customerService.updatePoints(id, points);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
