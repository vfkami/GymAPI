package com.vfkami.gymAPI.services

import com.vfkami.gymAPI.repository.AlunoRepository
import com.vfkami.gymAPI.model.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AlunoService (val alunoRepository: AlunoRepository) {
    @Transactional
    fun save(alunoModel : AlunoModel) = alunoRepository.save(alunoModel)
    @Transactional
    fun delete(alunoModel: AlunoModel) = alunoRepository.delete(alunoModel)
    fun existsByMatricula(matricula: String) = alunoRepository.existsByMatricula(matricula)
    fun existsByCpf(cpf: String) = alunoRepository.existsByCpf(cpf)
    fun findByMatricula(cpf : String) = alunoRepository.findById(cpf)
    fun findAll() = alunoRepository.findAll()
}