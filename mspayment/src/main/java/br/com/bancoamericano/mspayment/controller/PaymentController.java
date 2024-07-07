package br.com.bancoamericano.mspayment.controller;

import br.com.bancoamericano.mspayment.clients.CustomerResourceClient;
import br.com.bancoamericano.mspayment.exception.exceptions.ReceivePaymentException;
import br.com.bancoamericano.mspayment.mapper.PaymentMapper;
import br.com.bancoamericano.mspayment.model.dto.CustomerData;
import br.com.bancoamericano.mspayment.model.dto.PaymentRequestDto;
import br.com.bancoamericano.mspayment.model.dto.PaymentResponseDto;
import br.com.bancoamericano.mspayment.model.entities.Payment;
import br.com.bancoamericano.mspayment.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Endpoints for managing payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final CustomerResourceClient customerResourceClient;

    @PostMapping
    @Operation(summary = "Receive payment", description = "Receive payment",
            tags = {"Payment"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(
                                    schema = @Schema(implementation = PaymentResponseDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<PaymentResponseDto> receivePayment(@RequestBody PaymentRequestDto payment) {
        try {
            PaymentResponseDto responseDto = paymentService.receivePayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }
        catch (ReceivePaymentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("{paymentId}")
    @Operation(summary = "Find payment by id", description = "Find payment by id",
            tags = {"Payment"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = PaymentResponseDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable UUID paymentId){
        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(PaymentMapper.toDto(payment));
    }

    @GetMapping("user/{userId}")
    @Operation(summary = "Find customer by userid", description = "Find customer by userid",
            tags = {"Payment"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = CustomerData.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<CustomerData> getUserById(@PathVariable Long userId){
        ResponseEntity<CustomerData> customer = customerResourceClient.getCustomer(userId);
        return ResponseEntity.ok(customer.getBody());
    }
}
