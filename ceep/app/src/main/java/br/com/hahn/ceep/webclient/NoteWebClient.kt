package br.com.hahn.ceep.webclient

import android.util.Log
import br.com.hahn.ceep.model.Note
import br.com.hahn.ceep.webclient.model.NoteResponse

private const val TAG = "NoteWebClient"

class NoteWebClient {
    suspend fun findAll() : List<Note>? {
        return try {
            val noteResponse = RetrofitInitialize().service.findAll()
            return noteResponse.map(NoteResponse::note)
        }
        catch (e : Exception) {
            Log.e(TAG, "findAll",e)
            null
        }
    }
}
