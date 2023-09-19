package com.vfkami.gymAPI.services

import com.vfkami.gymAPI.model.InstrutorModel
import com.vfkami.gymAPI.repository.InstrutorRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class InstrutorService (val instrutorRepository: InstrutorRepository){
    @Transactional
    fun save(instrutorModel : InstrutorModel) = instrutorRepository.save(instrutorModel)
    @Transactional
    fun delete(instrutorModel: InstrutorModel) = instrutorRepository.delete(instrutorModel)
    fun existsByCfe(cfe: String) = instrutorRepository.existsByCFE(cfe)
    fun existsByCpf(cpf: String) = instrutorRepository.existsByCpf(cpf)
    fun findByCpf(cpf : String) = instrutorRepository.findById(cpf)
    fun findAll() = instrutorRepository.findAll()
}