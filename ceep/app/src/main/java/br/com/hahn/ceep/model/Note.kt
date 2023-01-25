package br.com.hahn.ceep.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title : String ,
    val description : String ,
    val image : String? = null
)
