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
    fun existsByMatricula(matricula : String) = treinoRepository.existsByMatricula(matricula)
    fun existsByMatriculaAndAtivo(matricula : String, ativo : Boolean) = treinoRepository.existsByMatriculaAndAtivo(matricula, ativo)
    fun findByMatriculaAndAtivo(matricula : String, ativo : Boolean) = treinoRepository.findByMatriculaAndAtivo(matricula, ativo)
    @Transactional
    fun desativaTreino(treinoModel : TreinoModel) : TreinoModel {
        treinoModel.ativo = false
        return treinoRepository.save(treinoModel)
    }
    @Transactional
    fun desativaAllTreinosAtivosByMatricula(matricula : String){
        findByMatriculaAndAtivo(matricula, true).forEach { treino -> desativaTreino(treino) }
    }

    @Transactional
    fun ativaTreino(treinoModel : TreinoModel) : TreinoModel {
        desativaAllTreinosAtivosByMatricula(treinoModel.matricula)
        treinoModel.ativo = true
        return treinoRepository.save(treinoModel)
    }

    @Transactional
    fun atualizaTreino(treinoModel: TreinoModel) : TreinoModel {
        treinoModel.qtdExecutado += 1

        if (treinoModel.qtdExecutado == treinoModel.qtdTotal)
            treinoModel.ativo = false

        return treinoRepository.save(treinoModel)
    }

}