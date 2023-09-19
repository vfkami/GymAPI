package com.vfkami.gymAPI.dtos

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

class InstrutorDto {
    @NotBlank
    var cpf : String = ""
    @NotBlank
    var nome : String = ""
    @NotBlank
    var idade : Int = 0
    @NotBlank
    var login : String = ""
    @NotBlank
    var dataCadastro : LocalDateTime = LocalDateTime.MIN
    @NotBlank
    var CFE : String = ""
    @NotBlank
    var salario : Double = 0.00

}