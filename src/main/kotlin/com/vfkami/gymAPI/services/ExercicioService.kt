package com.vfkami.gymAPI.services

import com.vfkami.gymAPI.model.ExercicioModel
import com.vfkami.gymAPI.model.TreinoModel
import com.vfkami.gymAPI.repository.ExercicioRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExercicioService (val exercicioRepository: ExercicioRepository){
    fun findById(id : UUID) = exercicioRepository.findById(id)
    @Transactional
    fun saveExercicio(exercicioModel: ExercicioModel) = exercicioRepository.save(exercicioModel)
    fun getAllExercicios() : List<ExercicioModel> = exercicioRepository.findAll()
    @Transactional
    fun updateExercicio(exercicioModel: ExercicioModel) : ExercicioModel {
        exercicioModel.ativo = !exercicioModel.ativo
        return exercicioRepository.save(exercicioModel)
    }

}