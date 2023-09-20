package com.vfkami.gymAPI.services

import com.vfkami.gymAPI.model.MensalidadeModel
import com.vfkami.gymAPI.repository.MensalidadeRepository
import com.vfkami.gymAPI.utils.Utils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Optional
import java.util.UUID

@Service
class MensalidadeService  (val mensalidadeRepository: MensalidadeRepository, val utils: Utils){
    fun findAllByMatricula(matricula : String) = mensalidadeRepository.findByMatricula(matricula)
    fun findNaoPagoByMatricula(matricula : String) = Optional.of(mensalidadeRepository.findByMatricula(matricula).filter{ !it.pago })
    @Transactional
    fun pagarMensalidade(id : UUID) : Optional<MensalidadeModel>{

        val mensalidade = mensalidadeRepository.findById(id)
        if (!mensalidade.isPresent)
            return mensalidade

        val mensalidadeModel = mensalidade.get()
        mensalidadeModel.pago = true
        mensalidadeModel.dataPagamento = LocalDateTime.now(ZoneId.of("UTC"))

        return Optional.of(mensalidadeRepository.save(mensalidadeModel))
    }
    @Transactional
    fun save(matricula : String, diaVencimento : Int) : MensalidadeModel {
        val proxDia = utils.gerarProximoDiaPagamento(diaVencimento)

        val mensalidadeModel = MensalidadeModel(
            id = UUID.randomUUID(),
            matricula = matricula,
            diaVencimento = proxDia,
            pago = false,
            dataPagamento = null
        )

        return mensalidadeRepository.save(mensalidadeModel)
    }
    fun findAllMensalidadeNaoPago() = mensalidadeRepository.findByPagoEquals(false)
}