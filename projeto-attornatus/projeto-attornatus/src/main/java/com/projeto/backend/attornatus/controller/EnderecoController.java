package com.projeto.backend.attornatus.controller;

import java.util.List;
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

import com.projeto.backend.attornatus.model.Endereco;
import com.projeto.backend.attornatus.repository.EnderecoRepository;

@RestController
@RequestMapping(value = "/api/enderecos")
public class EnderecoController {

    @GetMapping
    public ResponseEntity<Page<Endereco>> obterListaDeEnderecos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageRequest paging = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findAll(paging));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Endereco>> obterEnderecoPeloId(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> criarNovoEndereco(@RequestBody Endereco endereco) {
        try {
            return ResponseEntity.ok().body(enderecoRepository.save(endereco));
        } catch (DataIntegrityViolationException d) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao criar endereco! " + d.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar endereco! " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> atualizarNovoEndereco(@RequestBody Endereco endereco) {
        try {
            return ResponseEntity.ok().body(enderecoRepository.save(endereco));
        } catch (DataIntegrityViolationException d) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao criar endereco! " + d.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar endereco! " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletarEnderecoPeloId(@PathVariable("id") int id) {
        enderecoRepository.deleteById(id);
        return ResponseEntity.ok().body("Endereço deletado com sucesso!");
    }

    @GetMapping(value = "/pessoas/{idPessoa}")
    public ResponseEntity<List<Endereco>> listarTodosOsEnderecosDaPessoa(@PathVariable("idPessoa") int idPessoa) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findByPessoaId(idPessoa));
    }

    @GetMapping(value = "/pessoas/enderecoPrincipal/{idPessoa}")
    public ResponseEntity<List<Endereco>> listarApenasOsEnderecosPrincipaisDaPessoa(@PathVariable("idPessoa") int idPessoa) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(enderecoRepository.findByEnderecoPrincipal(idPessoa));
    }

    @GetMapping(value = "/principais/{enderecoPrincipal}")
    public ResponseEntity<List<Endereco>> listarTodosOsEnderecosPrincipais(
            @PathVariable("enderecoPrincipal") Boolean enderecoPrincipal) {
        return ResponseEntity.status(HttpStatus.OK).body(enderecoRepository.findByEnderecoPrincipal(enderecoPrincipal));
    }

    @Autowired
    private EnderecoRepository enderecoRepository;

}
