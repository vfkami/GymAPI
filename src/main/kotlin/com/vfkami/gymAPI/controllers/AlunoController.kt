package com.vfkami.gymAPI.controllers

import com.vfkami.gymAPI.dtos.AlunoDto
import com.vfkami.gymAPI.model.*
import com.vfkami.gymAPI.services.*
import com.vfkami.gymAPI.utils.Utils
import jakarta.validation.Valid
import org.springframework.beans.BeanUtils
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RestController
@RequestMapping("/aluno")
class AlunoController (val alunoService: AlunoService,
                       val usuarioService: UsuarioService,
                       val mensalidadeService: MensalidadeService,
                       val treinoService: TreinoService,
                       val utils: Utils) {
    @GetMapping
    fun getAllAlunos() = ResponseEntity.status(HttpStatus.OK).body(alunoService.findAll())

    @GetMapping("/{cpf}")
    fun getAlunoByCpf(@PathVariable(value = "cpf") cpf : String) : ResponseEntity<Any> {
        val aluno = alunoService.findByCpf(cpf)

        if(aluno.isPresent)
            return ResponseEntity.status(HttpStatus.OK).body(aluno.get())

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado para o CPF: $cpf")
    }

    @PostMapping
    fun saveAluno(@RequestBody @Valid alunoDto : AlunoDto) : ResponseEntity<Any>{
        val novaMatricula = utils.gerarMatricula(LocalDateTime.now(), alunoService.alunosCadastradosCount() + 1)

        if(!utils.validarCpf(alunoDto.cpf))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF: ${alunoDto.cpf} Inválido")

        if(alunoService.existsByCpf(alunoDto.cpf))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Aluno já existe para o CPF: ${alunoDto.cpf}")

        if(usuarioService.existsByCpf(alunoDto.cpf))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe para o CPF: ${alunoDto.cpf}")

        if(usuarioService.existsByLogin(alunoDto.login))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe com o login: ${alunoDto.login}")

        if(alunoService.existsById(novaMatricula))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao gerar número de matrícula")

        val alunoModel = AlunoModel()
        BeanUtils.copyProperties(alunoDto, alunoModel)

        val usuarioModel = UsuarioModel()
        BeanUtils.copyProperties(alunoDto, usuarioModel)

        alunoModel.matricula = novaMatricula

        alunoService.save(alunoModel)
        usuarioService.save(usuarioModel)
        mensalidadeService.save(alunoModel.matricula, alunoDto.diaPagamento, alunoDto.valorMensalidade)

        return ResponseEntity.status(HttpStatus.CREATED).body("Aluno matriculado com sucesso!")
    }

    @GetMapping("/semtreino")
    fun getAlunosSemTreino(): ResponseEntity<Any>{
        val alunos = alunoService.findAll()

        var alunosSemTreino = alunos.filter { aluno ->
            !treinoService.existsByMatricula(aluno.matricula)
        }.toMutableList()

        if(alunosSemTreino.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum aluno sem treino encontrado!")

        return ResponseEntity.status(HttpStatus.OK).body(alunosSemTreino)
    }
}