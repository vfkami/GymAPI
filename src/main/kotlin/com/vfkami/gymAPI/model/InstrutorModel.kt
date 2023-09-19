package com.vfkami.gymAPI.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "TB_INSTRUTOR")
class InstrutorModel (
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
    var CFE: String = "", // <- Identificação de Instrutor. Semelhante à matrícula

    @Column(nullable = false)
    var salario : Float = 0.00F
)