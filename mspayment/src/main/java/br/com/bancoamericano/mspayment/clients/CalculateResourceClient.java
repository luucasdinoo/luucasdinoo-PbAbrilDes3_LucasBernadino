package br.com.bancoamericano.mspayment.clients;

import br.com.bancoamericano.mspayment.model.dto.CalculateRuleRequest;
import br.com.bancoamericano.mspayment.model.dto.CalculateRuleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "mscalculate" ,url = "http://localhost:8081", path = "/v1/rules")
public interface CalculateResourceClient {

    @PostMapping("/calculate")
    ResponseEntity<CalculateRuleResponse> calculate(@RequestBody CalculateRuleRequest calculateRuleRequest);
}
