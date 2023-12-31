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

        // Verifica se o aluno não possui treinos ativos. Se possuir, atualizar antigos para false!
        treinoService.desativaAllTreinosAtivosByMatricula(cadastroTreinoDto.matricula)

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

    @PutMapping("/desativa/{id}")
    fun desativaTreino(@PathVariable(value = "id") id: String): ResponseEntity<Any> {
        if (!treinoService.existsById(UUID.fromString(id)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocorreu um erro: O treino não foi encontrado!")

        return ResponseEntity.status(HttpStatus.OK).body(
            treinoService.desativaTreino(
                treinoService.findById(UUID.fromString(id)).get()
            )
        )
    }
    @PutMapping("/ativa/{id}")
    fun ativaTreino(@PathVariable(value = "id") id: String): ResponseEntity<Any> {
        if (!treinoService.existsById(UUID.fromString(id)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocorreu um erro: O treino não foi encontrado!")

        val treino = treinoService.findById(UUID.fromString(id)).get()
        if(treino.qtdExecutado == treino.qtdTotal)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                .body("Não foi possível ativar o treino. O treino foi completado pelo usuário!")

        return ResponseEntity.status(HttpStatus.OK).body(treinoService.ativaTreino(treino))
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

    @GetMapping("/todosMatricula/{matricula}")
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

    @GetMapping("/matricula/{matricula}")
    fun getTreinoAtualByMatricula(@PathVariable(value = "matricula") matricula: String): ResponseEntity<Any> {
        val treinos = treinoService.findByMatriculaAndAtivo(matricula, true)

        if (treinos.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não foram encontrados treinos ativos para a matrícula ${matricula}!")

        val treino = treinos.first()
        val listaExercicios = exercicioTreinoService.getListaExerciciosByIdTreino(treino.id)

        if (listaExercicios.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ocorreu um erro: Um ou mais exercícios não foram encontrados!")

        return ResponseEntity.status(HttpStatus.OK)
            .body(TreinoDto(treino = treinoService.atualizaTreino(treino), exercicios = listaExercicios))
    }



}