package com.cadastro.pessoa.bitzen.record;

import java.time.LocalDate;
import java.util.List;

public record PessoaRecord(Long id, String nome, String cpf, LocalDate dataNascimento, List<ContatoRecord> contatos) {}

