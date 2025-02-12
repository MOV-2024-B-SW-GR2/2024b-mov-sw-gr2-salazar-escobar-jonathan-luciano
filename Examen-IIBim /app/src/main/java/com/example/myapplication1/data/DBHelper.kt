package com.example.myapplication1.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createZoologicoTable = """
            CREATE TABLE $TABLE_ZOOLOGICO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                fecha_inicio TEXT NOT NULL,
                ingresos_anuales REAL NOT NULL,
                esta_abierto INTEGER NOT NULL
            )
        """.trimIndent()

        val createAnimalTable = """
            CREATE TABLE $TABLE_ANIMAL (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                especie TEXT NOT NULL,
                edad INTEGER NOT NULL,
                cumpleanos TEXT NOT NULL,
                peso REAL NOT NULL,
                esta_alimentado INTEGER NOT NULL,
                zoologico_id INTEGER NOT NULL,
                FOREIGN KEY(zoologico_id) REFERENCES $TABLE_ZOOLOGICO(id) ON DELETE CASCADE
            )
        """.trimIndent()

        db.execSQL(createZoologicoTable)
        db.execSQL(createAnimalTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ANIMAL")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ZOOLOGICO")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "ZoologicoDB"
        const val DATABASE_VERSION = 1

        const val TABLE_ZOOLOGICO = "zoologicos"
        const val TABLE_ANIMAL = "animales"

        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_FECHA_INICIO = "fecha_inicio"
        const val COLUMN_INGRESOS_ANUALES = "ingresos_anuales"
        const val COLUMN_ESTA_ABIERTO = "esta_abierto"

        const val COLUMN_ESPECIE = "especie"
        const val COLUMN_EDAD = "edad"
        const val COLUMN_CUMPLEANOS = "cumpleanos"
        const val COLUMN_PESO = "peso"
        const val COLUMN_ESTA_ALIMENTADO = "esta_alimentado"
        const val COLUMN_ZOOLOGICO_ID = "zoologico_id"
    }
}
