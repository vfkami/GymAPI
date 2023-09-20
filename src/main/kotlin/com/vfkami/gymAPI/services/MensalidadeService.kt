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
    fun findById(id : UUID) = mensalidadeRepository.findById(id)
    fun findNaoPagoByMatricula(matricula : String) = Optional.of(mensalidadeRepository.findByMatricula(matricula).filter{ !it.pago })
    fun verifyMensalidadeAlreadyPago(id : UUID) : Boolean = mensalidadeRepository.findById(id).get().pago

    @Transactional
    fun pagarMensalidade(mensalidadeModel: MensalidadeModel) : Optional<MensalidadeModel>{
        mensalidadeModel.pago = true
        mensalidadeModel.dataPagamento = LocalDateTime.now(ZoneId.of("UTC"))
        gerarProximaMensalidade(mensalidadeModel)

        return Optional.of(mensalidadeRepository.save(mensalidadeModel))
    }
    @Transactional
    fun save(matricula : String, diaVencimento : Int, valorMensalidade : Double) : MensalidadeModel {
        val proximoPagamento = utils.gerarProximoDiaPagamento(diaVencimento)

        val mensalidadeModel = MensalidadeModel(
            id = UUID.randomUUID(),
            matricula = matricula,
            diaVencimento = proximoPagamento,
            pago = false,
            dataPagamento = null,
            valorMensalidade =  valorMensalidade
        )

        return mensalidadeRepository.save(mensalidadeModel)
    }
    @Transactional
    fun gerarProximaMensalidade(oldMensalidade: MensalidadeModel) : MensalidadeModel {
        val mensalidadeModel = MensalidadeModel(
            id = UUID.randomUUID(),
            matricula = oldMensalidade.matricula,
            diaVencimento = oldMensalidade.diaVencimento.plusMonths(1),
            pago = false,
            dataPagamento = null,
            valorMensalidade =  oldMensalidade.valorMensalidade
        )
        return mensalidadeRepository.save(mensalidadeModel)
    }

    fun findAllMensalidadeNaoPago() = mensalidadeRepository.findByPagoEquals(false)
}