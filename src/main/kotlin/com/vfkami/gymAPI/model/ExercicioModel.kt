package com.vfkami.gymAPI.model

import jakarta.persistence.*
import java.time.LocalTime
import java.util.*

@Entity
@Table(name = "TB_EXERCICIO")
class ExercicioModel (
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var nome : String = "",

    @Column(nullable = false)
    var repeticao : Int = 0,

    @Column(nullable = false)
    var tempo : LocalTime = LocalTime.MIN,

    @Column(nullable = false)
    var ativo : Boolean = true
)
