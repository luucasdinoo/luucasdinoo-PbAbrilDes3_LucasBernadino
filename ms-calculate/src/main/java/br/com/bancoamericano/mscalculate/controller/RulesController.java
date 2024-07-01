package br.com.bancoamericano.mscalculate.controller;

import br.com.bancoamericano.mscalculate.dto.CalculateRequestDto;
import br.com.bancoamericano.mscalculate.dto.CalculateResponseDto;
import br.com.bancoamericano.mscalculate.dto.RuleCreateDto;
import br.com.bancoamericano.mscalculate.dto.RuleResponseDto;
import br.com.bancoamericano.mscalculate.entities.Rules;
import br.com.bancoamericano.mscalculate.mapper.RuleMapper;
import br.com.bancoamericano.mscalculate.repositories.RuleRepository;
import br.com.bancoamericano.mscalculate.services.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rules")
@RequiredArgsConstructor
public class RulesController {

    private final RuleService ruleService;

    @PostMapping
    public ResponseEntity<RuleResponseDto> createRule(@RequestBody RuleCreateDto rules) {
        Rules rule = ruleService.createRule(RuleMapper.toRule(rules));
        return ResponseEntity.status(HttpStatus.CREATED).body(RuleMapper.toDto(rule));
    }

    @GetMapping
    public ResponseEntity<List<RuleResponseDto>> getAllRules(){
        List<Rules> rules = ruleService.getAllRules();
        return ResponseEntity.ok(RuleMapper.toRulesList(rules));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleResponseDto> getRuleById(@PathVariable Long id) {
        Rules rule = ruleService.getRuleById(id);
        return ResponseEntity.ok(RuleMapper.toDto(rule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuleById(@PathVariable Long id) {
        ruleService.deleteRuleById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody RuleCreateDto dto){
       ruleService.updateCustomer(id,dto);
       return ResponseEntity.noContent().build();
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculateResponseDto> calculate(@RequestBody CalculateRequestDto dto){
        CalculateResponseDto responseDto = ruleService.calculatePoints(dto);
        return ResponseEntity.ok(responseDto);
    }
}
