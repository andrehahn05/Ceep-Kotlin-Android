package br.com.hahn.ceep.webclient

import br.com.hahn.ceep.webclient.services.NoteService
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInitialize {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://172.17.0.1:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(NoteService::class.java)
}