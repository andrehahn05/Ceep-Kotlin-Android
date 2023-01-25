package br.com.hahn.ceep.webclient

import android.util.Log
import br.com.hahn.ceep.model.Note
import br.com.hahn.ceep.webclient.model.NoteResponse
import br.com.hahn.ceep.webclient.services.NoteService

private const val TAG = "NoteWebClient"
private  val noteService: NoteService = RetrofitInitialize().service

class NoteWebClient {
    suspend fun findAll() : List<Note>? {
        return try {
            val noteResponse = noteService.findAll()
            return noteResponse.map(NoteResponse::note)
        }
        catch (e : Exception) {
            Log.e(TAG, "findAll",e)
            null
        }
    }
}
