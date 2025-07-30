package dev.lucasteixeira.desafioexploradordefilmes.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDateString(dateString: String): String{
    return try {
        val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        val date = LocalDate.parse(dateString, inputFormatter)

        val outputFormatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR"))
        date.format(outputFormatter)
    }catch (e: Exception){
        dateString
    }
}