package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.lucasteixeira.desafioexploradordefilmes.ui.theme.DesafioExploradorDeFilmesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DesafioExploradorDeFilmesTheme {
            }
        }
    }
}