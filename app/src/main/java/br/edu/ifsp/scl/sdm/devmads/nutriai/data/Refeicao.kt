package br.edu.ifsp.scl.sdm.devmads.nutriai.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refeicoes") // Diz ao Room que isso é uma tabela
data class Refeicao(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val descricao: String,
    val analiseIA: String,
    val data: Long, // Mudamos para Long para salvar o timestamp (facilita cálculos)
    val imagemUri: String? = null // Adicionamos este para salvar o caminho da foto
)