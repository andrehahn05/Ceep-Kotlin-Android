package br.com.hahn.ceep.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.hahn.ceep.database.dao.NoteDao
import br.com.hahn.ceep.model.Note

@Database(
    version = 1,
    entities = [Note::class],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "ceep.db"
            ).build()
        }
    }

}