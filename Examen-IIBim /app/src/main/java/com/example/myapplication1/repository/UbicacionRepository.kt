package com.example.myapplication1.repository

import android.content.ContentValues
import com.example.myapplication1.data.DBHelper
import com.example.myapplication1.models.Ubicacion

class UbicacionRepository(private val dbHelper: DBHelper) {

    // Agregar una ubicación
    fun addUbicacion(ubicacion: Ubicacion): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_LATITUD, ubicacion.latitud)
            put(DBHelper.COLUMN_LONGITUD, ubicacion.longitud)
            put(DBHelper.COLUMN_ZOOLOGICO_ID, ubicacion.zoologicoId)
        }
        return db.insert(DBHelper.TABLE_UBICACION, null, values)
    }

    // Obtener ubicaciones por zoológico
    fun getUbicacionesPorZoologico(zoologicoId: Int): List<Ubicacion> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_UBICACION,
            null,
            "${DBHelper.COLUMN_ZOOLOGICO_ID} = ?",
            arrayOf(zoologicoId.toString()),
            null,
            null,
            null
        )
        val ubicaciones = mutableListOf<Ubicacion>()
        while (cursor.moveToNext()) {
            val ubicacion = Ubicacion(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID)),
                latitud = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LATITUD)),
                longitud = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LONGITUD)),
                zoologicoId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ZOOLOGICO_ID))
            )
            ubicaciones.add(ubicacion)
        }
        cursor.close()
        return ubicaciones
    }

    // Obtener todas las ubicaciones de la base de datos
    fun getAllUbicaciones(): List<Ubicacion> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DBHelper.TABLE_UBICACION, null, null, null, null, null, null)
        val ubicaciones = mutableListOf<Ubicacion>()
        while (cursor.moveToNext()) {
            val ubicacion = Ubicacion(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID)),
                latitud = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LATITUD)),
                longitud = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LONGITUD)),
                zoologicoId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ZOOLOGICO_ID))
            )
            ubicaciones.add(ubicacion)
        }
        cursor.close()
        return ubicaciones
    }

    // Eliminar una ubicación
    fun deleteUbicacion(ubicacionId: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(DBHelper.TABLE_UBICACION, "${DBHelper.COLUMN_ID} = ?", arrayOf(ubicacionId.toString()))
    }
}
