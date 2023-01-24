package br.com.hahn.ceep.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title : String ,
    val description : String ,
    val image : String? = null
)
