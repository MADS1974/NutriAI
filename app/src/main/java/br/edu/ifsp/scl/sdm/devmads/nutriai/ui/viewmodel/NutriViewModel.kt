package br.edu.ifsp.scl.sdm.devmads.nutriai.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import br.edu.ifsp.scl.sdm.devmads.nutriai.BuildConfig
import br.edu.ifsp.scl.sdm.devmads.nutriai.data.NutriDatabase
import br.edu.ifsp.scl.sdm.devmads.nutriai.data.Refeicao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NutriViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = NutriDatabase.getDatabase(application).refeicaoDao()

    val todasRefeicoes = dao.listarTodas().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var textoRefeicao by mutableStateOf("")
    var analiseResultado by mutableStateOf("")
        private set
    var carregando by mutableStateOf(false)
        private set
    var imagemSelecionada by mutableStateOf<Bitmap?>(null)

    private val generativeModel = GenerativeModel(
        modelName = "gemini-3.1-flash-lite",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    fun limparCampos() {
        textoRefeicao = ""
        analiseResultado = ""
        imagemSelecionada = null
    }

    // --- NOVA FUNÇÃO PARA LIMPAR O HISTÓRICO ---
    fun limparHistorico() {
        viewModelScope.launch {
            dao.deletarTudo()
        }
    }

    fun analisarRefeicao() {
        val descricao = textoRefeicao
        val bitmapOriginal = imagemSelecionada

        if (descricao.isBlank() && bitmapOriginal == null) return

        viewModelScope.launch {
            carregando = true
            analiseResultado = ""
            try {
                val inputContent = content {
                    bitmapOriginal?.let {
                        val larguraAlvo = 640
                        val alturaAlvo = (it.height * (larguraAlvo.toDouble() / it.width)).toInt()
                        val scaledBitmap = Bitmap.createScaledBitmap(it, larguraAlvo, alturaAlvo, false)
                        image(scaledBitmap)
                    }

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

                val response = generativeModel.generateContent(inputContent)
                val resultadoIA = response.text ?: "O NutrIA não conseguiu gerar uma resposta."
                analiseResultado = resultadoIA

                val novaRefeicao = Refeicao(
                    descricao = if (descricao.isNotBlank()) descricao else "Análise por foto",
                    analiseIA = resultadoIA,
                    data = System.currentTimeMillis()
                )
                dao.inserir(novaRefeicao)

            } catch (e: Exception) {
                analiseResultado = "Erro de conexão: ${e.localizedMessage}"
            } finally {
                carregando = false
            }
        }
    }
}