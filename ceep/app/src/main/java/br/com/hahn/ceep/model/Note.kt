package br.com.hahn.ceep.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title : String ,
    val description : String ,
    val image : String? = null,
    @ColumnInfo(defaultValue = "0")
    val synchronize : Boolean = false,
    @ColumnInfo(defaultValue = "0")
    val disabled : Boolean = false
)
