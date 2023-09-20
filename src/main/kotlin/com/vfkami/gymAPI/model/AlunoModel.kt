package com.vfkami.gymAPI.model;

import jakarta.persistence.*;
import java.time.LocalDateTime
import java.time.ZoneId

@Entity
@Table(name = "TB_ALUNO")
class AlunoModel (
    @Id
    @Column(nullable = false, unique = true)
    var matricula: String = "",

    @Column(nullable = false)
    var peso: Double = 0.00,

    @Column(nullable = false)
    var diaPagamento : Int = 1,

    @Column(nullable = false, unique = true)
    var cpf : String = ""
) {

}

