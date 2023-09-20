package com.vfkami.gymAPI.controllers

import com.vfkami.gymAPI.dtos.InstrutorDto
import com.vfkami.gymAPI.model.InstrutorModel
import com.vfkami.gymAPI.model.UsuarioModel
import com.vfkami.gymAPI.services.InstrutorService
import com.vfkami.gymAPI.services.UsuarioService
import com.vfkami.gymAPI.utils.Utils
import jakarta.validation.Valid
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/instrutor")
class InstrutorController (val instrutorService: InstrutorService, val usuarioService: UsuarioService, val utils: Utils){
    @GetMapping
    fun getAllInstrutores() = ResponseEntity.status(HttpStatus.OK).body(instrutorService.findAll())

    @GetMapping("/{cpf}")
    fun getAlunoByCpf(@PathVariable(value = "cpf") cpf : String) : ResponseEntity<Any> {
        val instrutor : Optional<InstrutorModel> = instrutorService.findByCpf(cpf)

        if(instrutor.isPresent)
            return ResponseEntity.status(HttpStatus.OK).body(instrutor.get())

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instrutor não encontrado para o CPF: $cpf")
    }

    @PostMapping
    fun saveAluno(@RequestBody @Valid instrutorDto: InstrutorDto) : ResponseEntity<Any> {
        if(!utils.validarCpf(instrutorDto.cpf))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF: ${instrutorDto.cpf} Inválido")

        if(usuarioService.existsByCpf(instrutorDto.cpf))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe para o CPF: ${instrutorDto.cpf}")

        if(instrutorService.existsByCpf(instrutorDto.cpf))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Instrutor já existe para o CPF: ${instrutorDto.cpf}")

        if(instrutorService.existsByCfe(instrutorDto.CFE))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Instrutor já existe com o CFE Nº: ${instrutorDto.CFE}")

        if(usuarioService.existsByLogin(instrutorDto.login))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe com o login: ${instrutorDto.login}")

        val instrutorModel = InstrutorModel()
        BeanUtils.copyProperties(instrutorDto, instrutorModel)

        val usuarioModel = UsuarioModel()
        BeanUtils.copyProperties(instrutorDto, usuarioModel)

        instrutorService.save(instrutorModel)
        usuarioService.save(usuarioModel)

        return ResponseEntity.status(HttpStatus.CREATED).body("Instrutor matriculado com sucesso!")
    }

}