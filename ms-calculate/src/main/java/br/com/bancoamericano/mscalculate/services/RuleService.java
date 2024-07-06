package br.com.bancoamericano.mscalculate.services;

import br.com.bancoamericano.mscalculate.dto.CalculateRequestDto;
import br.com.bancoamericano.mscalculate.dto.CalculateResponseDto;
import br.com.bancoamericano.mscalculate.dto.RuleCreateDto;
import br.com.bancoamericano.mscalculate.entities.Rules;
import br.com.bancoamericano.mscalculate.exception.exceptions.RuleNotFoundException;
import br.com.bancoamericano.mscalculate.repositories.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;
    private final ModelMapper modelMapper;

    public Rules createRule(Rules rule) {
        return ruleRepository.save(rule);
    }

    public Rules getRuleById(Long id){
        return ruleRepository.findById(id).orElseThrow(()->
                new RuleNotFoundException("Rule not found with id: " + id));
    }

    public List<Rules> getAllRules(){
        return ruleRepository.findAll();
    }

    public void deleteRuleById(Long id){
            ruleRepository.deleteById(id);
    }

       public void updateRule(Long id, RuleCreateDto dto){
        Rules rule = getRuleById(id);
        rule.setCategory(dto.getCategory());
        rule.setParity(dto.getParity());
        ruleRepository.save(rule);
    }

    public CalculateResponseDto calculatePoints(CalculateRequestDto dto){
        Rules rule = getRuleById(dto.getCategoryId());
        BigDecimal total = dto.getValue().multiply(BigDecimal.valueOf(rule.getParity()));
        Long totalPoints = total.longValue();
        return new CalculateResponseDto(totalPoints);
    }
}
