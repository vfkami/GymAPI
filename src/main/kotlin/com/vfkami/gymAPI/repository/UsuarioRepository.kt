package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.InstrutorModel
import com.vfkami.gymAPI.model.UsuarioModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<UsuarioModel, String> {
    fun existsByCpf(cpf : String) : Boolean

    fun existsByLogin(login : String) : Boolean

}