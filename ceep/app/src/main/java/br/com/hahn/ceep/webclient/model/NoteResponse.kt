package br.com.hahn.ceep.webclient.model

import br.com.hahn.ceep.model.Note
import java.util.*

class NoteResponse (
    val id: String? ,
    val titulo : String? ,
    val descricao: String? ,
    val imagem: String?
        ){
    val note : Note get() = Note(
        id = id?: UUID.randomUUID().toString(),
        title = titulo ?:"",
        description = descricao ?: "",
        image = imagem ?: ""
    )
}
