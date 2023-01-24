package br.com.hahn.ceep.webclient.services

import br.com.hahn.ceep.webclient.model.NoteResponse
import retrofit2.http.GET

interface NoteService {
    @GET("notas")
    suspend fun findAll(): List<NoteResponse>
}