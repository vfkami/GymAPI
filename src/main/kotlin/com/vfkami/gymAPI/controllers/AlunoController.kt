package com.vfkami.gymAPI.controllers

import com.vfkami.gymAPI.dtos.AlunoDto
import com.vfkami.gymAPI.model.AlunoModel
import com.vfkami.gymAPI.services.AlunoService
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
class AlunoController (val alunoService: AlunoService, val utils: Utils) {

    @GetMapping
    fun getAllAlunos() = ResponseEntity.status(HttpStatus.OK).body(alunoService.findAll())

    @GetMapping("/{cpf}")
    fun getAlunoByCpf(@PathVariable(value = "cpf") cpf : String) : ResponseEntity<Any> {
        val aluno : Optional<AlunoModel> = alunoService.findByMatricula(cpf)

        if(aluno.isPresent)
            return ResponseEntity.status(HttpStatus.OK).body(aluno.get())

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado para o CPF: $cpf")
    }

    @PostMapping
    fun saveAluno(@RequestBody @Valid alunoDto : AlunoDto) : ResponseEntity<Any>{
        if(alunoService.existsByCpf(alunoDto.cpf))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Aluno já existe para o CPF: ${alunoDto.cpf}")

        if(!utils.validarCpf(alunoDto.cpf))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF: ${alunoDto.cpf} Inválido")

        val matricula : String = utils.gerarMatricula(LocalDateTime.now())
        var alunoModel = AlunoModel()

        BeanUtils.copyProperties(alunoDto, alunoModel)
        alunoModel.matricula = matricula

        val saved = alunoService.save(alunoModel)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

}