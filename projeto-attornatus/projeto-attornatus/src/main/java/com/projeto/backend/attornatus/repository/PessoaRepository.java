package com.projeto.backend.attornatus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.backend.attornatus.model.Pessoa;


public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    Page<Pessoa> findAll(Pageable pageable);
}
