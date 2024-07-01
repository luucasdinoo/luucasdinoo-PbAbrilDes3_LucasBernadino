package br.com.bancoamericano.mscalculate.mapper;

import br.com.bancoamericano.mscalculate.dto.RuleCreateDto;
import br.com.bancoamericano.mscalculate.dto.RuleResponseDto;
import br.com.bancoamericano.mscalculate.entities.Rules;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RuleMapper {

    public static RuleResponseDto toDto(Rules rule) {
        return new ModelMapper().map(rule, RuleResponseDto.class);
    }

    public static Rules toRule(RuleCreateDto dto){
        return new ModelMapper().map(dto, Rules.class);
    }

    public static List<RuleResponseDto> toRulesList(List<Rules> list){
        return list.stream().map(rule -> toDto(rule)).collect(Collectors.toList());
    }
}
