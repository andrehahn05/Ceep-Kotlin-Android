package br.com.hahn.ceep.webclient.services

import br.com.hahn.ceep.webclient.model.NoteRequest
import br.com.hahn.ceep.webclient.model.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteService {
    @GET("notas")
    suspend fun findAll(): List<NoteResponse>

    @PUT("notas/{id}")
    suspend fun save(@Path("id") id: String,
             @Body note: NoteRequest):Response<NoteResponse>
}