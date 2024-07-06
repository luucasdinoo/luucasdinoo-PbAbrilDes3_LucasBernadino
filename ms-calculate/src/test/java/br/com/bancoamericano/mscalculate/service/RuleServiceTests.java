package br.com.bancoamericano.mscalculate.service;

import br.com.bancoamericano.mscalculate.dto.CalculateRequestDto;
import br.com.bancoamericano.mscalculate.dto.CalculateResponseDto;
import br.com.bancoamericano.mscalculate.dto.RuleCreateDto;
import br.com.bancoamericano.mscalculate.entities.Rules;
import br.com.bancoamericano.mscalculate.exception.exceptions.RuleNotFoundException;
import br.com.bancoamericano.mscalculate.repositories.RuleRepository;
import br.com.bancoamericano.mscalculate.services.RuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static br.com.bancoamericano.mscalculate.common.RuleConstants.*;

@ExtendWith(MockitoExtension.class)
public class RuleServiceTests {

    @InjectMocks
    private RuleService service;

    @Mock
    private RuleRepository repository;

    @Test
    void createRule_WithValidData() throws IOException {
        Rules rule = RULE;

        when(repository.save(rule)).thenReturn(rule);

        Rules sut = service.createRule(rule);

        assertNotNull(sut);
        assertNotNull(sut.getId());

        assertEquals(rule, sut);
        assertEquals(rule.getId(), sut.getId());
        assertEquals(rule.getCategory(), sut.getCategory());
        assertEquals(rule.getParity(), sut.getParity());
    }

    @Test
    void createRule_WithInvavalidData() {
        when(repository.save(INVALID_RULE)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> service.createRule(INVALID_RULE)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void getRuleById_WithExistingId(){
        when(repository.findById(2L)).thenReturn(Optional.of(ELETRONICOS));
        Rules sut = service.getRuleById(2L);

        assertNotNull(sut);
        assertNotNull(sut.getId());

        assertEquals(ELETRONICOS.getId(), sut.getId());
        assertEquals(ELETRONICOS.getCategory(), sut.getCategory());
        assertEquals(ELETRONICOS.getParity(), sut.getParity());

    }

    @Test
    void getRuleyById_WithNonExistingId(){
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getRuleById(99L))
                .isInstanceOf(RuleNotFoundException.class)
                .hasMessage(String.format("Rule not found with id: %s", 99L));
    }

    @Test
    void updateRule_WithValidData(){
        Rules existingRule = RULE;
        RuleCreateDto update = UPDATE_ELETRONICOS;

        when(repository.findById(1L)).thenReturn(Optional.of(existingRule));
        when(repository.save(any(Rules.class))).thenReturn(existingRule);

        service.updateRule(1L, update);

        assertNotNull(existingRule);
        assertNotNull(existingRule.getId());

        assertEquals(1L, existingRule.getId());
        assertEquals(UPDATE_ELETRONICOS.getCategory(), existingRule.getCategory());
        assertEquals(UPDATE_ELETRONICOS.getParity(), existingRule.getParity());
    }

    @Test
    void deleteCustomer_WithtExistingId(){
        assertThatCode(() -> service.deleteRuleById(1L)).doesNotThrowAnyException();
    }

    @Test
    void deleteCustomer_WithtUnexistingId() {
        doThrow(new RuntimeException()).when(repository).deleteById(99L);
        assertThatThrownBy(() -> service.deleteRuleById(99L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void getAllRules_ReturnsRules(){
        when(repository.findAll()).thenReturn(RULES);

        List<Rules> sut = service.getAllRules();
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(3);
        assertThat(sut.get(0)).isEqualTo(ELETRONICOS);
    }

    @Test
    void getAllRules_ReturnsNonEmptyList(){
            when(repository.findAll()).thenReturn(Collections.emptyList());
            List<Rules> sut = service.getAllRules();
            assertThat(sut).isEmpty();
    }

    @Test
    void calculatePoints_ReturnsTotalPoints(){
        CalculateRequestDto requestDto = CALCULATE_REQUEST_DTO;
        when(repository.findById(2L)).thenReturn(Optional.of(ELETRONICOS));

        CalculateResponseDto responseDto = service.calculatePoints(requestDto);

        assertNotNull(responseDto);
        assertEquals(1000, responseDto.getTotal());
    }
}
