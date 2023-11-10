package com.cadastro.pessoa.bitzen.service;
import com.cadastro.pessoa.bitzen.entity.Contato;
import com.cadastro.pessoa.bitzen.record.ContatoRecord;
import com.cadastro.pessoa.bitzen.repository.ContatoRepository;
import com.cadastro.pessoa.bitzen.utils.EntityRecordConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    public ContatoRecord getContato(Long id) {
        return contatoRepository.findById(id)
                .map(EntityRecordConverter::contatoEntityToContatoRecord)
                .orElse(null);
    }

    public ContatoRecord saveContato(ContatoRecord contatoRecord) {
        Contato contatoEntity = EntityRecordConverter.contatoRecordToContatoEntity(contatoRecord);
        Contato savedEntity = contatoRepository.save(contatoEntity);
        return EntityRecordConverter.contatoEntityToContatoRecord(savedEntity);
    }

    public ContatoRecord updateContato(Long id, ContatoRecord contatoRecord) {
        return contatoRepository.findById(id)
                .map(entity -> {
                    Contato updatedEntity = EntityRecordConverter.contatoRecordToContatoEntity(contatoRecord);
                    updatedEntity.setId(entity.getId()); // Mantém o mesmo ID
                    return contatoRepository.save(updatedEntity);
                })
                .map(EntityRecordConverter::contatoEntityToContatoRecord)
                .orElse(null);
    }

    public void deleteContato(Long id) {
        contatoRepository.deleteById(id);
    }
}
