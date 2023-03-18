package com.projeto.backend.attornatus.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.backend.attornatus.model.Pessoa;
import com.projeto.backend.attornatus.repository.PessoaRepository;

@RestController
@RequestMapping(value = "/api/pessoas")
public class PessoaController {

    @GetMapping
    public ResponseEntity<Page<Pessoa>> obterRelatorioDePessoas(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequest paging = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findAll(paging));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<Pessoa>> obterPessoaPeloId(@PathVariable("id") int id) {

        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> criarNovaPessoa(@RequestBody Pessoa pessoa) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pessoaRepository.save(pessoa));
        } catch (DataIntegrityViolationException d) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao tentar criar, os dados desta pessoa já existem! " + d.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao tentar enviar a requisição, por favor verifique os dados! " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> atualizarPessoa(@RequestBody Pessoa pessoa) {
        if (pessoaRepository.existsById(pessoa.getIdPessoa())) {
            return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.save(pessoa));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao enviar a requisição, esta pessoa já existe!");
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletarPessoaPeloId(@PathVariable("id") int id) {
        try {
            pessoaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso!");
        } catch (DataIntegrityViolationException d) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Esta pessoa possui um endereco vinculado, por favor exclua o endereço e tente novamente! "
                            + d.getMessage());
        }
    }

    @Autowired
    private PessoaRepository pessoaRepository;
}
