package br.com.bancoamericano.mspayment.clients;

import br.com.bancoamericano.mspayment.model.dto.CustomerData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "mscustomer", url = "http://localhost:8080", path = "/v1/customers")
public interface CustomerResourceClient {

    @GetMapping("/{id}")
    ResponseEntity<CustomerData> getCustomer(@PathVariable("id") Long id);
}
