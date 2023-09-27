package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.ExercicioTreinoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExercicioTreinoRepository : JpaRepository<ExercicioTreinoModel, UUID>{
    fun findByIdTreino(idTreino : UUID) : MutableList<ExercicioTreinoModel>
}