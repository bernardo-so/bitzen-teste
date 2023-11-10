package com.cadastro.pessoa.bitzen.repository;

import com.cadastro.pessoa.bitzen.entity.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    // Método para buscar pessoas por nome com paginação
    Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
}
