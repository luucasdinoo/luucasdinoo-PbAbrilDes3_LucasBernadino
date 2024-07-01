package br.com.bancoamericano.mscalculate.repositories;

import br.com.bancoamericano.mscalculate.entities.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rules, Long> {
}
