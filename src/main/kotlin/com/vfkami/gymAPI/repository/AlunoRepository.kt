package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.AlunoModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlunoRepository : JpaRepository<AlunoModel, String> {
    fun existsByCpf (cpf : String) : Boolean
    fun existsByMatricula (matricula : String) : Boolean
}