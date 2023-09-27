package com.vfkami.gymAPI.dtos

import jakarta.validation.constraints.NotBlank

class CadastroTreinoDto {
    @NotBlank
    var exerciciosId : List<String> = emptyList()

    @NotBlank
    var matricula : String = ""
}
