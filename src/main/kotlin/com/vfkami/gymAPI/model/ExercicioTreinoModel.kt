package com.vfkami.gymAPI.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "TB_EXERCICIO_TREINO")
class ExercicioTreinoModel (
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : UUID,

    @Column(nullable = false)
    var ativo : Boolean = true,

    @Column(nullable = false)
    var idTreino: UUID,

    @Column(nullable = false)
    var idExercicio: UUID
)
