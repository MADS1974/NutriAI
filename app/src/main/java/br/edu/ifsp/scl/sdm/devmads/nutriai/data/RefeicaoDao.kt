package br.edu.ifsp.scl.sdm.devmads.nutriai.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RefeicaoDao {
    @Insert
    suspend fun inserir(refeicao: Refeicao)

    @Query("SELECT * FROM refeicoes ORDER BY data DESC")
    fun listarTodas(): Flow<List<Refeicao>>

    @Query("DELETE FROM refeicoes WHERE id = :id")
    suspend fun deletar(id: Int)

    @Query("DELETE FROM refeicoes")
    suspend fun deletarTudo()
}