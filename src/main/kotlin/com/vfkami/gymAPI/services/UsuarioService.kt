package com.vfkami.gymAPI.services

import com.vfkami.gymAPI.model.UsuarioModel
import com.vfkami.gymAPI.repository.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class UsuarioService (val usuarioRepository: UsuarioRepository) {
    @Transactional
    fun save(usuarioModel : UsuarioModel) : UsuarioModel{
        usuarioModel.dataCadastro = LocalDateTime.now()
        return usuarioRepository.save(usuarioModel)
    }
    fun delete(usuarioModel: UsuarioModel) : UsuarioModel{
        usuarioModel.ativo = false
        usuarioModel.dataExclusao = LocalDateTime.now()
        return usuarioRepository.save(usuarioModel)
    }
    fun existsByCpf(cpf: String) = usuarioRepository.existsByCpf(cpf)
    fun findByCpf(cpf : String) = usuarioRepository.findById(cpf)
    fun existsByLogin(login : String) = usuarioRepository.existsByLogin(login)
    fun findAll() = usuarioRepository.findAll()
    
}