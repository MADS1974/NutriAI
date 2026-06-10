package br.edu.ifsp.scl.sdm.devmads.nutriai.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import br.edu.ifsp.scl.sdm.devmads.nutriai.BuildConfig
import kotlinx.coroutines.launch

class NutriViewModel : ViewModel() {

    // Estados da UI
    var analiseResultado by mutableStateOf("")
        private set

    var carregando by mutableStateOf(false)
        private set

    // No NutriViewModel.kt
    private val generativeModel = GenerativeModel(
        // Atualizado para os modelos atuais de 2026
        modelName = "gemini-3.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    fun analisarRefeicao(descricao: String) {
        viewModelScope.launch {
            carregando = true
            analiseResultado = "" // Limpa resultado anterior
            try {
                val prompt = """
                    Você é um nutricionista especializado. 
                    Analise a seguinte refeição: "$descricao".
                    1. Estime o total de calorias.
                    2. Estime proteínas, carboidratos e gorduras.
                    3. Dê uma dica rápida para melhorar essa refeição.
                    Seja direto, amigável e use português do Brasil.
                """.trimIndent()

                val response = generativeModel.generateContent(prompt)
                analiseResultado = response.text ?: "O Gemini não conseguiu gerar uma resposta."
            } catch (e: Exception) {
                analiseResultado = "Erro de conexão: ${e.localizedMessage}"
            } finally {
                carregando = false
            }
        }
    }
}