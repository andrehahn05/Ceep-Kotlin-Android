package br.com.hahn.ceep.webclient.model

data class NoteRequest (
    val titulo : String? ,
    val descricao: String? ,
    val imagem: String? = null
)