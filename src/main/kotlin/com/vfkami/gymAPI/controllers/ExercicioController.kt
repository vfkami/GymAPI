package com.vfkami.gymAPI.controllers

import com.vfkami.gymAPI.dtos.AlunoDto
import com.vfkami.gymAPI.dtos.ExercicioDto
import com.vfkami.gymAPI.model.ExercicioModel
import com.vfkami.gymAPI.services.ExercicioService
import com.vfkami.gymAPI.utils.Utils
import jakarta.validation.Valid
import org.springframework.beans.BeanUtils
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@RestController
@RequestMapping("/exercicio")
class ExercicioController (val exercicioService: ExercicioService, val utils: Utils){
    @GetMapping
    fun getAllExercicios() :  ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body(exercicioService.getAllExercicios())
    }
    @GetMapping("/{id}")
    fun getExercicio(@PathVariable(value = "id") id : String) : ResponseEntity<Any>{
        val exercicio = exercicioService.findById(UUID.fromString(id))

        if(exercicio.isEmpty)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercicio não encontrado!")

        return ResponseEntity.status(HttpStatus.OK).body(exercicio)
    }
    @PostMapping
    fun saveExercicio(@RequestBody @Valid exercicioDto: ExercicioDto) : ResponseEntity<Any>{
        val exercicioModel = ExercicioModel()
        BeanUtils.copyProperties(exercicioDto, exercicioModel)

        exercicioModel.ativo = true
        exercicioModel.id = UUID.randomUUID()

        if(!utils.validaFormatoHora(exercicioDto.tempo))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Tempo no formato incorreto! Use 00:00:00")

        exercicioModel.tempo = LocalTime.parse(exercicioDto.tempo, DateTimeFormatter.ofPattern("HH:mm:ss"))
        return ResponseEntity.status(HttpStatus.OK).body(exercicioService.saveExercicio(exercicioModel))
    }
    @PutMapping("/{id}")
    fun updateExercicio(@PathVariable(value = "id") id : String) : ResponseEntity<Any>{
        val exercicio = exercicioService.findById(UUID.fromString(id))

        if(exercicio.isEmpty)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exercicio não encontrado!")

        return ResponseEntity.status(HttpStatus.OK).body(exercicioService.updateExercicio(exercicio.get()))
    }
}