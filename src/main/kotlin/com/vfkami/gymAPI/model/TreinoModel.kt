package com.vfkami.gymAPI.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "TB_TREINO")
class TreinoModel(
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var matricula : String = "",

    @Column(nullable = false)
    var ativo : Boolean = true,

    @Column(nullable = false)
    var qtdExecutado : Int = 0,

    @Column(nullable = false)
    var qtdTotal : Int = 0

)