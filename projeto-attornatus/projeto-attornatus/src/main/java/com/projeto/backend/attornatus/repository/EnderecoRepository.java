package com.projeto.backend.attornatus.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projeto.backend.attornatus.model.Endereco;


public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    Page<Endereco> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM endereco WHERE idPessoa = :idPessoa", nativeQuery = true)
    List<Endereco> findByPessoaId(int idPessoa);

    @Query(value = "SELECT * FROM endereco WHERE enderecoPrincipal = :enderecoPrincipal", nativeQuery = true)
    List<Endereco> findByEnderecoPrincipal(Boolean enderecoPrincipal);

    @Query(value = "SELECT * FROM endereco WHERE enderecoPrincipal = true AND idPessoa = :idPessoa", nativeQuery = true)
    List<Endereco> findByEnderecoPrincipal(int idPessoa);
}
