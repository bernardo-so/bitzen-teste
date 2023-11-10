package com.cadastro.pessoa.bitzen.utils;

import com.cadastro.pessoa.bitzen.entity.Contato;
import com.cadastro.pessoa.bitzen.entity.Pessoa;
import com.cadastro.pessoa.bitzen.record.ContatoRecord;
import com.cadastro.pessoa.bitzen.record.PessoaRecord;

import java.util.List;
import java.util.stream.Collectors;

public class EntityRecordConverter {
    public static Pessoa pessoaRecordToPessoaEntity(PessoaRecord record) {
        if (record == null) {
            return null;
        }

        Pessoa entity = new Pessoa(
                record.id(),
                record.nome(),
                record.cpf(),
                record.dataNascimento(),
                null // Contatos serão definidos abaixo
        );

        if (record.contatos() != null) {
            List<Contato> contatos = record.contatos().stream()
                    .map(EntityRecordConverter::contatoRecordToContatoEntity)
                    .collect(Collectors.toList());
            entity.setContatos(contatos);
        }

        return entity;
    }

    public static PessoaRecord pessoaEntityToPessoaRecord(Pessoa entity) {
        if (entity == null) {
            return null;
        }

        List<ContatoRecord> contatos = null;
        if (entity.getContatos() != null) {
            contatos = entity.getContatos().stream()
                    .map(EntityRecordConverter::contatoEntityToContatoRecord)
                    .collect(Collectors.toList());
        }

        return new PessoaRecord(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getDataNascimento(),
                contatos
        );
    }

    // Contato conversions
    public static Contato contatoRecordToContatoEntity(ContatoRecord record) {
        if (record == null) {
            return null;
        }

        return new Contato(
                record.id(),
                record.nome(),
                record.telefone(),
                record.email(),
                null // Pessoa associada será definida em outro contexto
        );
    }

    public static ContatoRecord contatoEntityToContatoRecord(Contato entity) {
        if (entity == null) {
            return null;
        }

        return new ContatoRecord(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getEmail()
        );
    }
}