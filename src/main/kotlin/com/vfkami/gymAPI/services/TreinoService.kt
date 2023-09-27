package com.vfkami.gymAPI.services

import com.vfkami.gymAPI.model.ExercicioModel
import com.vfkami.gymAPI.model.ExercicioTreinoModel
import com.vfkami.gymAPI.model.TreinoModel
import com.vfkami.gymAPI.repository.ExercicioTreinoRepository
import com.vfkami.gymAPI.repository.TreinoRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class TreinoService (val treinoRepository: TreinoRepository, val exercicioTreinoRepository: ExercicioTreinoRepository){
    @Transactional
    fun save(treinoModel: TreinoModel) = treinoRepository.save(treinoModel)
    fun existsById(id : UUID) = treinoRepository.existsById(id)
    fun findById(id : UUID) = treinoRepository.findById(id)
    fun findByMatricula(matricula : String) = treinoRepository.findByMatricula(matricula)
    @Transactional
    fun updateTreino(id : UUID) : TreinoModel {
        var treinoModel = treinoRepository.findById(id)
        treinoModel.get().ativo = !treinoModel.get().ativo

        return treinoRepository.save(treinoModel.get())
    }

}