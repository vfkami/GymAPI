package com.vfkami.gymAPI.dtos

import com.vfkami.gymAPI.model.ExercicioModel
import com.vfkami.gymAPI.model.TreinoModel
import jakarta.validation.constraints.NotBlank

class TreinoDto (

    @NotBlank
    var exercicios : MutableList<ExercicioModel>,

    @NotBlank
    var treino : TreinoModel
)

