package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.MensalidadeModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MensalidadeRepository : JpaRepository<MensalidadeModel, UUID> {
    fun findByMatricula(matricula : String) : List<MensalidadeModel>
    fun findByPagoEquals(status : Boolean) : List<MensalidadeModel>
}