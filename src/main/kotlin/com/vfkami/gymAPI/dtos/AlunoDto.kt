package com.vfkami.gymAPI.dtos

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

class AlunoDto {
    @NotBlank
    var cpf : String = ""
    @NotBlank
    var nome : String = ""
    @NotBlank
    var idade : Int = 0
    @NotBlank
    var login : String = ""
    @NotBlank
    var peso : Double = 0.00
    @NotBlank
    var diaPagamento : Int = 0
    @NotBlank
    var valorMensalidade : Double = 0.00
    var matricula : String = ""
}