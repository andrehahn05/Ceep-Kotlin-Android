package br.com.hahn.ceep.repository

import br.com.hahn.ceep.database.dao.NoteDao
import br.com.hahn.ceep.model.Note
import br.com.hahn.ceep.webclient.NoteWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NoteRepository(
    private val dao : NoteDao ,
    private val webClient : NoteWebClient ,
) {

    fun findAll(): Flow<List<Note>> {
        return dao.findAll()
    }

    private suspend fun refresh() {
        webClient.findAll()?.let { notes ->
            val noteSynced = notes.map { note ->
                note.copy(synchronize = true)
            }
            dao.add(noteSynced)
        }
    }

    fun findById(id : String) : Flow<Note> {
        return dao.findById(id)
    }

    suspend fun remove(id : String) {
        dao.remove(id)
        webClient.remove(id)
    }

    suspend fun save(note : Note) {
        dao.save(note)
       if(webClient.save(note)){
          val synchronizedNote = note.copy(synchronize = true)
           dao.save(synchronizedNote)
       }
    }

    suspend fun synchronize() {
        val notSynchronized = dao.findNotSynchronized().first()
        notSynchronized.forEach { synced ->
            save(synced)
        }
        refresh()
    }
}