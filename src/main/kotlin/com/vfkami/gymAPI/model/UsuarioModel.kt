package com.vfkami.gymAPI.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "TB_USUARIO")
class UsuarioModel (
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

    var dataExclusao: LocalDateTime? = null,

)