package com.vfkami.gymAPI.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "TB_PESSOA")
@Inheritance(strategy = InheritanceType.JOINED)
open class Pessoa (
    @Id
    open var cpf: String,

    @Column(nullable = false, length = 50)
    open var nome: String,

    @Column(nullable = false)
    open var idade: Int,

    @Column(nullable = false, unique = true)
    open var login: String,

    @Column(nullable = false)
    open var dataCadastro: LocalDateTime,

    @Column(nullable = false)
    open var ativo: Boolean  = true
)