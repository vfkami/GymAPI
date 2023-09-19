package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.InstrutorModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstrutorRepository : JpaRepository<InstrutorModel, String> {
    fun existsByCFE(cfe : String) : Boolean
    fun existsByCpf(cpf : String) : Boolean

}