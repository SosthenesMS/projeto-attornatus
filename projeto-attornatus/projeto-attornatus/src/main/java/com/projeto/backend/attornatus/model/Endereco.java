package com.projeto.backend.attornatus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEndereco;

    @Column(name = "logradouro", nullable = false, length = 45)
    private String logradouro;

    @Column(name = "cep", nullable = false, length = 45)
    private long cep;

    @Column(name = "numero", nullable = false, length = 45)
    private int numero;

    @Column(name = "cidade", nullable = false, length = 45)
    private String cidade;

    @Column(name = "enderecoPrincipal", nullable = false)
    private Boolean enderecoPrincipal;

    @OneToOne
    @JoinColumn(name = "idPessoa")
    @JsonIgnoreProperties("hibernateLazyInitializer")
    private Pessoa pessoa;
}
