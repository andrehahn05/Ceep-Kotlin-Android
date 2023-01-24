package br.com.hahn.ceep.webclient

import br.com.hahn.ceep.webclient.services.NoteService
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInitialize {
    val URL = "http://192.168.0.145:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(NoteService::class.java)
}