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
        if (id != null)
            ruleRepository.deleteById(id);
        else
            throw new RuleNotFoundException(String.format("Rule with id %s not found or id is null", id));
    }

       public void updateRule(Long id, RuleCreateDto dto){
        Rules ruleById = ruleRepository.findById(id).orElseThrow(()->
                        new RuleNotFoundException(String.format("Customer with id %s not found", id)));
        modelMapper.map(dto, ruleById);
        ruleRepository.save(ruleById);
    }

    public CalculateResponseDto calculatePoints(CalculateRequestDto dto){
        Rules rule = getRuleById(dto.getCategoryId());
        BigDecimal total = dto.getValue().multiply(BigDecimal.valueOf(rule.getParity()));
        Long totalPoints = total.longValue();
        return new CalculateResponseDto(totalPoints);
    }
}
