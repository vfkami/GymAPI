package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.AlunoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.swing.text.html.Option

@Repository
interface AlunoRepository : JpaRepository<AlunoModel, String> {
    fun existsByMatricula(matricula : String) : Boolean
    fun existsByCpf(cpf : String) : Boolean
    @Query("SELECT COUNT(a) FROM AlunoModel a")
    fun countAlunos() : Long
    fun findAlunoModelByCpf(cpf: String) : Optional<AlunoModel>

}