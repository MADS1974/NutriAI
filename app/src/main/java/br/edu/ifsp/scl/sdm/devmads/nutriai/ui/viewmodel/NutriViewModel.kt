package br.edu.ifsp.scl.sdm.devmads.nutriai.ui.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content // IMPORTANTE: Adicionado para suporte a imagem
import br.edu.ifsp.scl.sdm.devmads.nutriai.BuildConfig
import kotlinx.coroutines.launch

class NutriViewModel : ViewModel() {

    // 1. Variável para o texto que o usuário digita
    var textoRefeicao by mutableStateOf("")

    // 2. Estados da UI
    var analiseResultado by mutableStateOf("")
        private set

    var carregando by mutableStateOf(false)
        private set

    var imagemSelecionada by mutableStateOf<Bitmap?>(null)

    private val generativeModel = GenerativeModel(
        modelName = "gemini-3.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    // 3. Função para limpar os campos
    fun limparCampos() {
        textoRefeicao = ""
        analiseResultado = ""
        imagemSelecionada = null
    }

    fun analisarRefeicao() {
        val descricao = textoRefeicao
        val bitmapOriginal = imagemSelecionada

        // Agora permite avançar se tiver texto OU imagem
        if (descricao.isBlank() && bitmapOriginal == null) return

        viewModelScope.launch {
            carregando = true
            analiseResultado = ""
            try {
                // Criamos um "content" que aceita múltiplos tipos (Texto e Imagem)
                val inputContent = content {
                    // Se o usuário selecionou uma imagem, redimensionamos e adicionamos ao envio
                    bitmapOriginal?.let {
                        // Redimensiona para no máximo 640px de largura para ser rápido e não dar erro de limite
                        val larguraAlvo = 640
                        val alturaAlvo = (it.height * (larguraAlvo.toDouble() / it.width)).toInt()
                        val scaledBitmap = Bitmap.createScaledBitmap(it, larguraAlvo, alturaAlvo, false)

                        image(scaledBitmap)
                    }

                    // Adicionamos o texto do prompt
                    text("""
                        Você é um nutricionista especializado. 
                        Analise a refeição da imagem e/ou a descrição fornecida: "$descricao".
                        1. Identifique os alimentos presentes.
                        2. Estime o total de calorias.
                        3. Estime proteínas, carboidratos e gorduras.
                        4. Dê uma dica rápida para melhorar essa refeição.
                        Seja direto, amigável e use português do Brasil.
                    """.trimIndent())
                }

                // Chamamos a geração passando o inputContent (multimodal)
                val response = generativeModel.generateContent(inputContent)
                analiseResultado = response.text ?: "O NutrIA não conseguiu gerar uma resposta."

            } catch (e: Exception) {
                analiseResultado = "Erro de conexão: ${e.localizedMessage}"
            } finally {
                carregando = false
            }
        }
    }
}