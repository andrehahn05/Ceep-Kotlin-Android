package br.com.hahn.ceep.repository

import br.com.hahn.ceep.database.dao.NoteDao
import br.com.hahn.ceep.model.Note
import br.com.hahn.ceep.webclient.NoteWebClient
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val dao : NoteDao ,
    private val webClient : NoteWebClient ,
) {

    fun findAll(): Flow<List<Note>> {
        return dao.findAll()
    }

    suspend fun refresh() {
        webClient.findAll()?.let { notes ->
            dao.add(notes)
        }
    }

}