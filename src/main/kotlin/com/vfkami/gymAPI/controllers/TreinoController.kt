package com.vfkami.gymAPI.controllers

import com.vfkami.gymAPI.dtos.CadastroTreinoDto
import com.vfkami.gymAPI.dtos.TreinoDto
import com.vfkami.gymAPI.model.ExercicioModel
import com.vfkami.gymAPI.model.ExercicioTreinoModel
import com.vfkami.gymAPI.model.TreinoModel
import com.vfkami.gymAPI.services.AlunoService
import com.vfkami.gymAPI.services.ExercicioService
import com.vfkami.gymAPI.services.ExercicioTreinoService
import com.vfkami.gymAPI.services.TreinoService
import jakarta.validation.Valid
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/treino")
class TreinoController (
    val exercicioService: ExercicioService,
    val treinoService: TreinoService,
    val alunoService: AlunoService,
    val exercicioTreinoService: ExercicioTreinoService) {
    @PostMapping
    fun saveTreino(@RequestBody @Valid cadastroTreinoDto: CadastroTreinoDto): ResponseEntity<Any> {
        val listaExercicios: MutableList<ExercicioModel> = mutableListOf()

        for (exercicioId in cadastroTreinoDto.exerciciosId) {
            val exercicio = exercicioService.findById(UUID.fromString(exercicioId))

            if (exercicio.isEmpty)
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ocorreu um erro: Um dos exercícios não foi encontrado!")

            listaExercicios.add(exercicio.get())
        }

        if (!alunoService.existsById(cadastroTreinoDto.matricula))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ocorreu um erro: Aluno de matrícula ${cadastroTreinoDto.matricula} não foi encontrado!")

        val novoTreino = TreinoModel(
            matricula = cadastroTreinoDto.matricula,
            ativo = true
        )

        val savedTreino = treinoService.save(novoTreino)

        //Ao salvar um treino é necessário salvar um relacionamento Exercicio-Treino
        for (exercicio in listaExercicios)
            exercicioTreinoService.save(
                ExercicioTreinoModel(
                    id = UUID.randomUUID(),
                    idTreino = savedTreino.id,
                    idExercicio = exercicio.id
                )
            )

        return ResponseEntity.status(HttpStatus.OK).body(savedTreino)
    }

    @PutMapping("/{id}")
    fun atualizaTreino(@PathVariable(value = "id") id: String): ResponseEntity<Any> {
        if (!treinoService.existsById(UUID.fromString(id)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocorreu um erro: O treino não foi encontrado!")

        return ResponseEntity.status(HttpStatus.OK).body(treinoService.updateTreino(UUID.fromString(id)))
    }

    @GetMapping("/{id}")
    fun getTreino(@PathVariable(value = "id") id: String): ResponseEntity<Any> {
        val treinoModel = treinoService.findById(UUID.fromString(id))

        if (treinoModel.isEmpty)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocorreu um erro: O treino não foi encontrado!")

        val listaExercicios = exercicioTreinoService.getListaExerciciosByIdTreino(UUID.fromString(id))

        if (listaExercicios.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ocorreu um erro: Um ou mais exercícios não foram encontrados!")

        return ResponseEntity.status(HttpStatus.OK).body(
            TreinoDto(
                treino = treinoModel.get(),
                exercicios = listaExercicios
            )
        )
    }

    @GetMapping("/matricula/{matricula}")
    fun getTreinosByMatricula(@PathVariable(value = "matricula") matricula: String): ResponseEntity<Any> {
        val treinos = treinoService.findByMatricula(matricula)

        if (treinos.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não foram encontrados treinos para a matrícula ${matricula}!")

        val listaTreinoDto: MutableList<TreinoDto> = mutableListOf()

        for (it in treinos) {
            val listaExercicios = exercicioTreinoService.getListaExerciciosByIdTreino(it.id)

            if (listaExercicios.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ocorreu um erro: Um ou mais exercícios não foram encontrados!")

            listaTreinoDto.add(
                TreinoDto(
                    treino = it,
                    exercicios = listaExercicios
                )
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(listaTreinoDto)
    }
}