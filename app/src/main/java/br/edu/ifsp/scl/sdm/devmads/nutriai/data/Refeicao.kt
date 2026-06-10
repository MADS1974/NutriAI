package br.edu.ifsp.scl.sdm.devmads.nutriai.data

data class Refeicao(
    val id: Int = 0,
    val descricao: String,
    val calorias: Int,
    val data: String,
    val analiseIA: String
)