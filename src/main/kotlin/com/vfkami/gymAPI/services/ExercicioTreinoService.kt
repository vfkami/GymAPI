package com.vfkami.gymAPI.services

import com.vfkami.gymAPI.model.ExercicioModel
import com.vfkami.gymAPI.model.ExercicioTreinoModel
import com.vfkami.gymAPI.repository.ExercicioTreinoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExercicioTreinoService (val exercicioTreinoRepository: ExercicioTreinoRepository, val exercicioService: ExercicioService){
    fun save(exercicioTreinoModel: ExercicioTreinoModel) = exercicioTreinoRepository.save(exercicioTreinoModel)
    fun getExercicioTreinoByIdTreino(id : UUID) = exercicioTreinoRepository.findByIdTreino(id)
    fun getListaExerciciosByIdTreino(id : UUID) : MutableList<ExercicioModel>{
        val listaExercicioTreino = getExercicioTreinoByIdTreino(id)
        val listaExercicios : MutableList<ExercicioModel> = mutableListOf()

        for(it in listaExercicioTreino) {
            val exercicio = exercicioService.findById(it.idExercicio)
            if (exercicio.isPresent)
                listaExercicios.add(exercicio.get())
            else
                return mutableListOf()
        }

        return listaExercicios
    }

}