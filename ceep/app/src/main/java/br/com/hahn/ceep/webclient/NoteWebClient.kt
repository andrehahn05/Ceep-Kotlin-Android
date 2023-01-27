package br.com.hahn.ceep.webclient

import android.util.Log
import br.com.hahn.ceep.model.Note
import br.com.hahn.ceep.webclient.model.NoteRequest
import br.com.hahn.ceep.webclient.model.NoteResponse
import br.com.hahn.ceep.webclient.services.NoteService


private const val TAG = "WebClient"
private val noteService : NoteService = RetrofitInitialize().service

class NoteWebClient {
    suspend fun  findAll() : List<Note>? {
        return try {
            val noteResponse = noteService.findAll()
            return noteResponse.map(NoteResponse::note)
        }
        catch (e : Exception) {
            Log.e(TAG , "findAll" , e)
            null
        }
    }

    suspend fun save(note : Note) : Boolean {
        try {
            val res = noteService.save(
                note.id , NoteRequest(
                    titulo = note.title ,
                    descricao = note.description ,
                    imagem = note.image
                )
            )
            return res.isSuccessful
        }
        catch (e : Exception) {
            Log.e(TAG , "Falha ao tentar salvar" , e)
        }
        return false
    }

    suspend fun remove(id : String): Boolean {
        try {
            noteService.remove(id)
            return true
        }
        catch (e : Exception) {
            Log.e(TAG , "Falha ao tentar remover a nota" , e)
        }
        return false
    }
}
