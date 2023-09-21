package com.vfkami.gymAPI.services

import com.vfkami.gymAPI.dtos.AlunoDto
import com.vfkami.gymAPI.repository.AlunoRepository
import com.vfkami.gymAPI.model.*
import com.vfkami.gymAPI.repository.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*
import javax.swing.text.html.Option

@Service
class AlunoService (val alunoRepository: AlunoRepository, val usuarioRepository: UsuarioRepository) {
    @Transactional
    fun save(alunoModel : AlunoModel) = alunoRepository.save(alunoModel)
    fun existsByCpf(cpf: String) = alunoRepository.existsByCpf(cpf)
    fun existsById(matricula: String) = alunoRepository.existsById(matricula)
    fun findByCpf(cpf : String) = alunoRepository.findAlunoModelByCpf(cpf)
    fun findAll() = alunoRepository.findAll()
    fun alunosCadastradosCount() = alunoRepository.countAlunos()
    fun findUsuarioAluno(matricula : String) : Optional<AlunoDto> {
        val aluno = alunoRepository.findById(matricula)
        val usuario = usuarioRepository.findById(aluno.get().cpf)

        if(!aluno.isPresent or !usuario.isPresent)
            return Optional.empty()

        val usuarioAluno =  AlunoDto()
        usuarioAluno.cpf = aluno.get().cpf
        usuarioAluno.peso = aluno.get().peso
        usuarioAluno.diaPagamento = aluno.get().diaPagamento
        usuarioAluno.matricula = aluno.get().matricula
        usuarioAluno.nome = usuario.get().nome
        usuarioAluno.login = usuario.get().login
        usuarioAluno.idade = usuario.get().idade

        return Optional.of(usuarioAluno)
    }

}