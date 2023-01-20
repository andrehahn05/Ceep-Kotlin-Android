package br.com.hahn.ceep.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.hahn.ceep.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    suspend fun save(note: Note)

    @Query("SELECT * FROM Note")
    fun findAll() : Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id")
    fun findById(id: Long): Flow<Note>

    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun remove(id: Long)

}