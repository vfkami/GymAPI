package com.vfkami.gymAPI.model;

import jakarta.persistence.*;
import java.time.LocalDateTime

@Entity
@Table(name = "TB_ALUNO")
public class Aluno (
    cpf: String,
    nome: String,
    idade: Int,
    login: String,
    dataCadastro: LocalDateTime,

    @Column(nullable = false)
    var matricula: String = "",

    @Column(nullable = false)
    var peso: Int = 0,

    @OneToOne
    @JoinColumn(name = "cpf")
    var pessoa : Pessoa

) : Pessoa(cpf, nome, idade, login, dataCadastro)

