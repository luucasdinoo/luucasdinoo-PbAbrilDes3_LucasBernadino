package br.com.bancoamericano.mspayment.model.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto implements Serializable {

    private UUID id;
    private Long customerId;
    private BigDecimal totalPayment;
    private LocalDateTime createdDate;
}
