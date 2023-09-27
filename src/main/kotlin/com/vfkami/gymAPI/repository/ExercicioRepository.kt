package com.vfkami.gymAPI.repository

import com.vfkami.gymAPI.model.ExercicioModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExercicioRepository : JpaRepository<ExercicioModel, UUID>