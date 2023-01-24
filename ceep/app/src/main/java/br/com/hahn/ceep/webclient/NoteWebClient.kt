package br.com.hahn.ceep.webclient

import android.util.Log
import br.com.hahn.ceep.model.Note
import br.com.hahn.ceep.webclient.model.NoteResponse
import retrofit2.Call
import retrofit2.Response

class NoteWebClient {
    suspend fun findAll(): List<Note> {
        val noteResponse = RetrofitInitialize().service.findAll()
        return noteResponse.map(NoteResponse::note)

        }
    }
