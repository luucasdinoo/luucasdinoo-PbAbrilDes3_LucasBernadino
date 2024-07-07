package br.com.bancoamericano.mscustomer.controller;

import br.com.bancoamericano.mscustomer.mapper.CustomerMapper;
import br.com.bancoamericano.mscustomer.models.dto.CustomerCreateDto;
import br.com.bancoamericano.mscustomer.models.dto.CustomerResponseDto;
import br.com.bancoamericano.mscustomer.models.dto.CustomerUpdateDto;
import br.com.bancoamericano.mscustomer.services.CustomerService;
import br.com.bancoamericano.mscustomer.util.FileUtil;
import br.com.bancoamericano.mscustomer.util.S3Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Endpoints for managing customers")
public class CustomerController {

    private final CustomerService customerService;
    private final S3Util util;

    @PostMapping
    @Operation(summary = "Add a new customer", description = "Add a new customer",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(
                                    schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerCreateDto dto) throws IOException {
        try {
            File photo = FileUtil.convertToFile(dto.getBase64Photo(), dto.getEmail());
            var customer = customerService.createCustomer(CustomerMapper.toCustomer(dto), photo);
            return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.toDto(customer));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Find customer by id", description = "Find customer by id",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable Long id) {
        var customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(CustomerMapper.toDto(customer));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a customer", description = "Deletes a customer",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") Long id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updating a customer", description = "Updating a customer",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204"),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateDto dto){
        customerService.updateCustomer(id,dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/points/{id}/{points}")
    @Operation(summary = "Add points", description = "Add points",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204"),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> updatePoints(@PathVariable("id") Long id, @PathVariable("points") Long points){
        customerService.updatePoints(id, points);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
