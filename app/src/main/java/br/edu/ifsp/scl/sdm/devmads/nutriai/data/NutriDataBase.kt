package br.edu.ifsp.scl.sdm.devmads.nutriai.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Refeicao::class], version = 1, exportSchema = false)
abstract class NutriDatabase : RoomDatabase() {
    abstract fun refeicaoDao(): RefeicaoDao

    companion object {
        @Volatile
        private var INSTANCE: NutriDatabase? = null

        fun getDatabase(context: Context): NutriDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NutriDatabase::class.java,
                    "nutri_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}