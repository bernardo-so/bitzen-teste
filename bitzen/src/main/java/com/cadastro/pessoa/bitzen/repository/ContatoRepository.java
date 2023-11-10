package com.cadastro.pessoa.bitzen.repository;

import com.cadastro.pessoa.bitzen.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
}