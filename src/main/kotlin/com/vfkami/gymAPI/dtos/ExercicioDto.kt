package com.vfkami.gymAPI.dtos

import jakarta.persistence.Column
import jakarta.validation.constraints.NotBlank
import org.springframework.cglib.core.Local
import java.time.LocalTime
import java.util.*
class ExercicioDto {
    @NotBlank
    var nome : String = ""

    @NotBlank
    var repeticao : Int = 0

    @NotBlank
    var tempo : String = ""
}