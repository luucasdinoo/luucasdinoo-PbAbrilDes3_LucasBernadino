package br.com.bancoamericano.mspayment.clients;

import br.com.bancoamericano.mspayment.model.dto.CustomerData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "mscustomer", url = "http://localhost:8080", path = "/v1/customers")
public interface CustomerResourceClient {

    @GetMapping("/{id}")
    ResponseEntity<CustomerData> getCustomer(@PathVariable("id") Long id);

    @PostMapping("/points/{id}/{points}")
    ResponseEntity<Void> updatePoints(@PathVariable("id") Long id, @PathVariable("points") Long points);

}
