package br.com.hahn.ceep.webclient.model

import br.com.hahn.ceep.model.Note

class NoteResponse (
    val id: String? ,
    val titulo : String? ,
    val descricao: String? ,
    val imagem: String?
        ){
    val note : Note get() = Note(
        id = 0 ,
        title = titulo ?:"",
        description = descricao ?: "",
        image = imagem ?: ""
    )
}
