package br.com.bancoamericano.mscustomer.repositories;

import br.com.bancoamericano.mscustomer.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {}
