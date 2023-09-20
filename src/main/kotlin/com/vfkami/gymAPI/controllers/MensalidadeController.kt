package com.vfkami.gymAPI.controllers

import com.vfkami.gymAPI.services.MensalidadeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.UUID

@RestController
@RequestMapping("mensalidade")
class MensalidadeController (val mensalidadeService: MensalidadeService){
    @GetMapping("/{matricula}")
    fun getAllMensalidadesByMatricula(@PathVariable(value = "matricula") matricula : String) = mensalidadeService.findAllByMatricula(matricula)

    @GetMapping("/naoPago/{matricula}")
    fun getNaoPagoByMatricula(@PathVariable(value = "matricula") matricula: String) : ResponseEntity<Any>{
        val mensalidades = mensalidadeService.findNaoPagoByMatricula(matricula)

        if(mensalidades.isPresent)
            return ResponseEntity.status(HttpStatus.OK).body(mensalidades.get())

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno sem mensalidades a pagar!")
    }
    @PutMapping("/pagar/{id}")
    fun pagarMensalidadeById(@PathVariable(value = "id") id : UUID) : ResponseEntity<Any>{
        val mensalidadeModel = mensalidadeService.findById(id)
        if (!mensalidadeModel.isPresent)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ocorreu um erro: Mensalidade não encontrada!")

        if(mensalidadeService.verifyMensalidadeAlreadyPago(mensalidadeModel.get().id))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ocorreu um erro: Mensalidade já está paga!")

        val mensalidade = mensalidadeService.pagarMensalidade(mensalidadeModel.get())

        if (mensalidade.isPresent)
            return ResponseEntity.status(HttpStatus.OK).body(mensalidade)

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ocorreu um erro ao registrar o pagamento da mensalidade")
    }

    @GetMapping("/naoPago")
    fun getAllMensalidadeNaoPago() : ResponseEntity<Any>{
        val mensalidades = mensalidadeService.findAllMensalidadeNaoPago()

        if (mensalidades.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foram encontradas mensalidades atrasadas")

        return ResponseEntity.status(HttpStatus.OK).body(mensalidades)
    }

    @GetMapping("/atrasado")
    fun getAllMensalidadeVencida() : ResponseEntity<Any>{
        val mensalidade = mensalidadeService.findAllMensalidadeNaoPago()

        if (mensalidade.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foram encontradas mensalidades atrasadas")

        return ResponseEntity.status(HttpStatus.OK).body(mensalidade.filter{it.diaVencimento < LocalDateTime.now()})
    }
}