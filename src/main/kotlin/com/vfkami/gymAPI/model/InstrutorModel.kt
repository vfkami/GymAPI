package com.vfkami.gymAPI.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "TB_INSTRUTOR")
class InstrutorModel (
    @Id
    @Column(nullable = false, unique = true)
    var CFE: String = "", // <- Identificação de Instrutor. Semelhante à matrícula

    @Column(nullable = false)
    var salario : Double = 0.00,

    @Column(nullable = false, unique = true, )
    var cpf : String = ""

)