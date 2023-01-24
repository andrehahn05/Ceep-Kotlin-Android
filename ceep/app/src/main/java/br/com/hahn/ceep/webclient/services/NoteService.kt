package br.com.hahn.ceep.webclient.services

import br.com.hahn.ceep.model.Note
import retrofit2.Call
import retrofit2.http.GET

interface NoteService {
    @GET("notas")
    fun findAll():Call<List<Note>>
}