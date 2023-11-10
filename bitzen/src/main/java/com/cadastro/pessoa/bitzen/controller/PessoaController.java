package com.cadastro.pessoa.bitzen.controller;

import com.cadastro.pessoa.bitzen.record.PessoaRecord;
import com.cadastro.pessoa.bitzen.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/{id}")
    public ResponseEntity<PessoaRecord> getPessoa(@PathVariable Long id) {
        PessoaRecord PessoaRecord = pessoaService.getPessoa(id);
        if (PessoaRecord != null) {
            return ResponseEntity.ok(PessoaRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paginada")
    public Page<PessoaRecord> getAllPessoasPaginated(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "nome", required = false) String nome) {
        return pessoaService.getAllPessoasPaginated(page, size, nome);
    }

    @PostMapping
    public ResponseEntity<PessoaRecord> createPessoa(@RequestBody PessoaRecord PessoaRecord) {
        PessoaRecord createdPessoa = pessoaService.savePessoa(PessoaRecord);
        return ResponseEntity.ok(createdPessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaRecord> updatePessoa(@PathVariable Long id, @RequestBody PessoaRecord PessoaRecord) {
        PessoaRecord updatedPessoa = pessoaService.updatePessoa(id, PessoaRecord);
        if (updatedPessoa != null) {
            return ResponseEntity.ok(updatedPessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePessoa(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.ok().build();
    }
}
