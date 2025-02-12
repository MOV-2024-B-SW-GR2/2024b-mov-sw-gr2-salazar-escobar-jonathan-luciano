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

        val createUbicacionTable = """
            CREATE TABLE $TABLE_UBICACION (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_LATITUD REAL NOT NULL,
                $COLUMN_LONGITUD REAL NOT NULL,
                $COLUMN_ZOOLOGICO_ID INTEGER NOT NULL,
                FOREIGN KEY($COLUMN_ZOOLOGICO_ID) REFERENCES $TABLE_ZOOLOGICO($COLUMN_ID) ON DELETE CASCADE
            )
        """.trimIndent()

        db.execSQL(createZoologicoTable)
        db.execSQL(createAnimalTable)
        db.execSQL(createUbicacionTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ANIMAL")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ZOOLOGICO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_UBICACION")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "ZoologicoDB"
        const val DATABASE_VERSION = 1

        const val TABLE_ZOOLOGICO = "zoologicos"
        const val TABLE_ANIMAL = "animales"
        const val TABLE_UBICACION = "ubicaciones"

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

        const val COLUMN_LATITUD = "latitud"
        const val COLUMN_LONGITUD = "longitud"
    }
}
