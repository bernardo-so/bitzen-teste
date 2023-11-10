package com.cadastro.pessoa.bitzen.service;
import com.cadastro.pessoa.bitzen.entity.Pessoa;
import com.cadastro.pessoa.bitzen.record.ContatoRecord;
import com.cadastro.pessoa.bitzen.record.PessoaRecord;
import com.cadastro.pessoa.bitzen.repository.PessoaRepository;
import com.cadastro.pessoa.bitzen.utils.CpfValidator;
import com.cadastro.pessoa.bitzen.utils.EmailValidator;
import com.cadastro.pessoa.bitzen.utils.EntityRecordConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.cadastro.pessoa.bitzen.utils.EmailValidator.isEmailValido;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaRecord getPessoa(Long id) {
        return pessoaRepository.findById(id)
                .map(EntityRecordConverter::pessoaEntityToPessoaRecord)
                .orElse(null);
    }

    public Page<PessoaRecord> getAllPessoasPaginated(Integer page, Integer size, String nome) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Pessoa> pessoaPage;

        if (nome != null && !nome.isEmpty()) {
            pessoaPage = pessoaRepository.findByNomeContaining(nome, pageRequest);
        } else {
            pessoaPage = pessoaRepository.findAll(pageRequest);
        }

        return pessoaPage.map(EntityRecordConverter::pessoaEntityToPessoaRecord);
    }

    public PessoaRecord savePessoa(PessoaRecord pessoaRecord) {

        this.validaDados(pessoaRecord);

        Pessoa pessoaEntity = EntityRecordConverter.pessoaRecordToPessoaEntity(pessoaRecord);
        Pessoa savedEntity = pessoaRepository.save(pessoaEntity);
        return EntityRecordConverter.pessoaEntityToPessoaRecord(savedEntity);
    }

    public PessoaRecord updatePessoa(Long id, PessoaRecord pessoaRecord) {
        this.validaDados(pessoaRecord);

        return pessoaRepository.findById(id)
                .map(entity -> {
                    Pessoa updatedEntity = EntityRecordConverter.pessoaRecordToPessoaEntity(pessoaRecord);
                    updatedEntity.setId(entity.getId()); // Mantém o mesmo ID
                    return pessoaRepository.save(updatedEntity);
                })
                .map(EntityRecordConverter::pessoaEntityToPessoaRecord)
                .orElse(null);
    }

    public void deletePessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

    public void validaDados (PessoaRecord pessoaRecord) {
        if(pessoaRecord != null) {
            List<String> emailsInvalidos = pessoaRecord.contatos().stream()
                    .map(ContatoRecord::email)
                    .filter(email -> !isEmailValido(email))
                    .collect(Collectors.toList());

            if (!emailsInvalidos.isEmpty()) {
                throw new IllegalArgumentException("Sintaxe de e-mail(s) inválida(s): " + String.join(", ", emailsInvalidos));
            }

            if (pessoaRecord.dataNascimento().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("A data de nascimento não pode ser uma data futura.");
            }

            if (!CpfValidator.isCPFValido(pessoaRecord.cpf())) {
                throw new IllegalArgumentException("CPF inválido.");
            }
        } else {
            throw new IllegalArgumentException("Pessoa não pode ser null !");
        }

    }
}
