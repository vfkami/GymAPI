package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.AlunoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AlunoRepository : JpaRepository<AlunoModel, String> {
    fun existsByMatricula(matricula : String) : Boolean
    fun existsByCpf(cpf : String) : Boolean
    @Query("SELECT COUNT(a) FROM AlunoModel a")
    fun countAlunos() : Long
    fun findByCpf(cpf: String)

}