package br.com.bancoamericano.mscustomer.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "customer")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "bithdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "points")
    private Long points;

    @Column(name = "url_photo")
    private String url_photo;

    public Customer(String cpf, String name, String gender, Date birthdate, String email, Long points, String url_photo) {
        this.cpf = cpf;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.email = email;
        this.points = points;
        this.url_photo = url_photo;
    }
}
