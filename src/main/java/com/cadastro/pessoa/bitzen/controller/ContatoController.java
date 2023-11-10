package com.cadastro.pessoa.bitzen.controller;
import com.cadastro.pessoa.bitzen.record.ContatoRecord;
import com.cadastro.pessoa.bitzen.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @GetMapping("/{id}")
    public ResponseEntity<ContatoRecord> getContato(@PathVariable Long id) {
        ContatoRecord ContatoRecord = contatoService.getContato(id);
        if (ContatoRecord != null) {
            return ResponseEntity.ok(ContatoRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ContatoRecord> createContato(@RequestBody ContatoRecord ContatoRecord) {
        ContatoRecord createdContato = contatoService.saveContato(ContatoRecord);
        return ResponseEntity.ok(createdContato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoRecord> updateContato(@PathVariable Long id, @RequestBody ContatoRecord ContatoRecord) {
        ContatoRecord updatedContato = contatoService.updateContato(id, ContatoRecord);
        if (updatedContato != null) {
            return ResponseEntity.ok(updatedContato);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContato(@PathVariable Long id) {
        contatoService.deleteContato(id);
        return ResponseEntity.ok().build();
    }
}
