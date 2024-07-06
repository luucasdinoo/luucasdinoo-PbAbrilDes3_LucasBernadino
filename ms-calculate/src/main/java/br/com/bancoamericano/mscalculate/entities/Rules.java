package br.com.bancoamericano.mscalculate.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rules")
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
public class Rules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String category;

    @Column(nullable = false)
    private Integer parity;

    public Rules(String category, Integer parity) {
        this.category = category;
        this.parity = parity;
    }

}
