package br.com.hahn.ceep.migrations

import android.annotation.SuppressLint
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

val MIGRATION_1_2 = object : Migration(1, 2) {

    @SuppressLint("Range")
    override fun migrate(database: SupportSQLiteDatabase) {
        val tabelaNova = "Note"
        val tabelaAtual = "Note"

        //criar tabela nova com todos os campos esperados
        database.execSQL(
            """CREATE TABLE IF NOT EXISTS $tabelaNova (
        `id` TEXT PRIMARY KEY NOT NULL, 
        `title` TEXT NOT NULL, 
        `description` TEXT NOT NULL, 
        `image` TEXT)"""
        )

        //copiar dados da tabela atual para a tabela nova
        database.execSQL(
            """INSERT INTO $tabelaNova (id, title, description, image) 
        SELECT id, title, description, image FROM $tabelaAtual
    """
        )

        //gerar ids diferentes e novos
        val cursor = database.query("SELECT * FROM $tabelaNova")
        while (cursor.moveToNext()) {
            val id = cursor.getString(
                cursor.getColumnIndex("id")
            )
            database.execSQL(
                """
        UPDATE $tabelaNova 
            SET id = '${UUID.randomUUID()}' 
            WHERE id = $id"""
            )
        }

        //apagar tabela atual
        database.execSQL("DROP TABLE $tabelaAtual")

        //renomear tabela nova com o nome da tabela atual
        database.execSQL("ALTER TABLE $tabelaNova RENAME TO $tabelaAtual")
    }

}

val  MIGRATION_2_3 = object : Migration(2,3){
    override fun migrate(database : SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Note ADD synchronize INTERGER NOT NULL DEFAULT 0")
    }
}