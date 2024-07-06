package br.com.bancoamericano.mscalculate.common;

import br.com.bancoamericano.mscalculate.dto.CalculateRequestDto;
import br.com.bancoamericano.mscalculate.dto.RuleCreateDto;
import br.com.bancoamericano.mscalculate.entities.Rules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RuleConstants {

    public static final Rules RULE = new Rules(1L,"category", 1);
    public static final Rules INVALID_RULE = new Rules("", null);
    public static final RuleCreateDto UPDATE_ELETRONICOS = new RuleCreateDto("Eletrônicos", 10);
    public static final CalculateRequestDto CALCULATE_REQUEST_DTO = new CalculateRequestDto(2L, BigDecimal.valueOf(100));

    public static final Rules ELETRONICOS = new Rules(2L,"Eletrônicos", 10);
    public static final Rules MOVEIS = new Rules(3L,"Móveis", 8);
    public static final Rules ELETRODOMESTICOS = new Rules(2L,"Eletrodomésticos", 6);

    public static final List<Rules> RULES = new ArrayList<>() {
        {
            add(ELETRONICOS);
            add(MOVEIS);
            add(ELETRODOMESTICOS);
        }
    };
}
