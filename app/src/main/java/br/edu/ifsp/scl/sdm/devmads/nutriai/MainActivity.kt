package br.edu.ifsp.scl.sdm.devmads.nutriai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifsp.scl.sdm.devmads.nutriai.ui.theme.NutriAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriAITheme {
                // Aqui chamaremos nosso NavHost (o roteador do app)
                NutriAppNavigation()
            }
        }
    }
}

@Composable
fun NutriAppNavigation() {
    // Por enquanto, apenas um placeholder para a primeira tela
    Text(text = "Bem-vindo ao NutriAI! Em breve: Login")
}