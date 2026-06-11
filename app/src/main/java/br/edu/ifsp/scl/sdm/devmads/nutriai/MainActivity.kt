package br.edu.ifsp.scl.sdm.devmads.nutriai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.ifsp.scl.sdm.devmads.nutriai.ui.NutriAppNavigation
import br.edu.ifsp.scl.sdm.devmads.nutriai.ui.theme.NutriAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriAITheme {
                // Agora ele vai chamar a função que está no NavGraph.kt
                NutriAppNavigation()
            }
        }
    }
}
