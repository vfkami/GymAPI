package com.vfkami.gymAPI.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "TB_MENSALIDADE")
class MensalidadeModel (
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : UUID,

    @Column(nullable = false)
    var matricula: String = "",

    @Column(nullable = false)
    var diaVencimento : LocalDateTime = LocalDateTime.MIN,

    @Column(nullable = false)
    var pago : Boolean = false,

    @Column
    var dataPagamento : LocalDateTime?
){}
