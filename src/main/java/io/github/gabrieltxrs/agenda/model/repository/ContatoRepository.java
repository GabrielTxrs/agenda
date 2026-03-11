package io.github.gabrieltxrs.agenda.model.repository;

import io.github.gabrieltxrs.agenda.model.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Integer> {
}
