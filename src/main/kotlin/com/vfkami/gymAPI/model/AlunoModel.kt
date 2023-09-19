package com.vfkami.gymAPI.model;

import jakarta.persistence.*;
import java.time.LocalDateTime
import java.time.ZoneId

@Entity
@Table(name = "TB_ALUNO")
class AlunoModel (
    @Id
    var cpf: String = "",

    @Column(nullable = false, length = 50)
    var nome: String = "",

    @Column(nullable = false)
    var idade: Int = 0,

    @Column(nullable = false, unique = true)
    var login: String = "",

    @Column(nullable = false)
    var dataCadastro: LocalDateTime = LocalDateTime.MIN,

    @Column(nullable = false)
    var ativo: Boolean  = true,

    @Column(nullable = false, unique = true)
    var matricula: String = "",

    @Column(nullable = false)
    var peso: Double = 0.00,

    @Column(nullable = false)
    var diaPagamento : Int = 1
) {
    constructor() : this("", "", 0, "", LocalDateTime.now(ZoneId.of("UTC")), true, "", 0.00, 1)
}

