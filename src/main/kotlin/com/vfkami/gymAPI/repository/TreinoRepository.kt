package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.TreinoModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TreinoRepository : JpaRepository<TreinoModel, UUID> {
    fun findByMatricula(matricula : String) : List<TreinoModel>
    fun existsByMatricula(matricula : String) : Boolean
    fun existsByMatriculaAndAtivo(matricula : String, ativo : Boolean) : Boolean
    fun findByMatriculaAndAtivo(matricula : String, ativo : Boolean) : List<TreinoModel>
}