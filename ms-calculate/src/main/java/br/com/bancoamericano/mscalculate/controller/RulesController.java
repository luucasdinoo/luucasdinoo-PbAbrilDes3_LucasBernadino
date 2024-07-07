package br.com.bancoamericano.mscalculate.controller;

import br.com.bancoamericano.mscalculate.dto.CalculateRequestDto;
import br.com.bancoamericano.mscalculate.dto.CalculateResponseDto;
import br.com.bancoamericano.mscalculate.dto.RuleCreateDto;
import br.com.bancoamericano.mscalculate.dto.RuleResponseDto;
import br.com.bancoamericano.mscalculate.entities.Rules;
import br.com.bancoamericano.mscalculate.mapper.RuleMapper;
import br.com.bancoamericano.mscalculate.services.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rules")
@RequiredArgsConstructor
@Tag(name = "Rule", description = "Endpoints for managing rules")
public class RulesController {

    private final RuleService ruleService;

    @PostMapping
    @Operation(summary = "Add a new rule", description = "Add a new rule",
            tags = {"Rule"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(
                                    schema = @Schema(implementation = RuleResponseDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<RuleResponseDto> createRule(@RequestBody RuleCreateDto rules) {
        Rules rule = ruleService.createRule(RuleMapper.toRule(rules));
        return ResponseEntity.status(HttpStatus.CREATED).body(RuleMapper.toDto(rule));
    }

    @GetMapping
    @Operation(summary = "Finds rules", description = "Finds rules",
            tags = {"Rule"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RuleResponseDto.class)))
                            }),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<List<RuleResponseDto>> getAllRules(){
        List<Rules> rules = ruleService.getAllRules();
        return ResponseEntity.ok(RuleMapper.toRulesList(rules));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find rule by id", description = "Find rule by id",
            tags = {"Rule"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = RuleResponseDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<RuleResponseDto> getRuleById(@PathVariable Long id) {
        Rules rule = ruleService.getRuleById(id);
        return ResponseEntity.ok(RuleMapper.toDto(rule));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a rule", description = "Deletes a rule",
            tags = {"Rule"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> deleteRuleById(@PathVariable Long id) {
        ruleService.deleteRuleById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updating a rule", description = "Updating a rule",
            tags = {"Rule"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204"),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> updateRule(@PathVariable Long id, @RequestBody RuleCreateDto dto){
       ruleService.updateRule(id,dto);
       return ResponseEntity.noContent().build();
    }

    @PostMapping("/calculate")
    @Operation(summary = "Calculate points", description = "Calculate points",
            tags = {"Rule"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                            schema = @Schema(implementation = CalculateResponseDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<CalculateResponseDto> calculate(@RequestBody CalculateRequestDto dto){
        CalculateResponseDto responseDto = ruleService.calculatePoints(dto);
        return ResponseEntity.ok(responseDto);
    }
}
