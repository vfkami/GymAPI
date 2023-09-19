package com.vfkami.gymAPI.utils

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class Utils {
    companion object {
        var qtdRegistrado : Int = 1

        fun calculateFirstDigit(partialCpf : String): Int{
            val peso = arrayListOf(10, 9, 8, 7, 6, 5, 4, 3, 2)
            var soma = 0

            for(i in partialCpf.indices)
                soma += partialCpf[i].toString().toInt() * peso[i]

            val remainder = soma % 11
            return if (remainder < 2) 0 else 11 - remainder
        }

        fun calculateSecondDigit(partialCpf : String): Int{
            val peso = arrayListOf(11, 10, 9, 8, 7, 6, 5, 4, 3, 2)
            var soma = 0

            for(i in partialCpf.indices)
                soma += partialCpf[i].toString().toInt() * peso[i]

            val remainder = soma % 11
            return if (remainder < 2) 0 else 11 - remainder
        }
    }

    fun validarCpf(cpf : String) : Boolean {
        val cleanedCpf = cpf.replace("[^\\d]".toRegex(), "")
        if(cleanedCpf.length != 11) return false

        val firstDigit = calculateFirstDigit(cleanedCpf.substring(0, 9))
        val secondDigit = calculateSecondDigit(cleanedCpf.substring(0, 10))

        return cleanedCpf.endsWith("$firstDigit$secondDigit")
    }

    fun gerarMatricula(dataCadastro : LocalDateTime) : String {
        val initial : String = dataCadastro.format(DateTimeFormatter.ofPattern("yyyyMM"))
        val cadastro : String = "0".repeat(5 - qtdRegistrado.toString().length) + qtdRegistrado

        qtdRegistrado += 1

        return initial + cadastro
    }



}