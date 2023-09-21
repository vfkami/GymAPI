package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.AlunoModel
import com.vfkami.gymAPI.model.InstrutorModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface InstrutorRepository : JpaRepository<InstrutorModel, String> {
    fun existsByCFE(cfe : String) : Boolean
    fun existsByCpf(cpf : String) : Boolean
    fun findInstrutorModelByCpf(cpf : String) : Optional<InstrutorModel>

}