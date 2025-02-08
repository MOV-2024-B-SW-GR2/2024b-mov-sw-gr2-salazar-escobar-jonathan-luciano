package com.example.myapplication1.repository

import android.content.ContentValues
import com.example.myapplication1.data.DBHelper
import com.example.myapplication1.models.Zoologico
import com.example.myapplication1.models.Animal

class ZoologicoRepository(private val dbHelper: DBHelper) {

    // Agregar un zoológico
    fun addZoologico(zoologico: Zoologico): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_NOMBRE, zoologico.name)
            put(DBHelper.COLUMN_FECHA_INICIO, zoologico.startDate)
            put(DBHelper.COLUMN_INGRESOS_ANUALES, zoologico.anualIncome)
            put(DBHelper.COLUMN_ESTA_ABIERTO, if (zoologico.isOpen) 1 else 0)
        }

        return db.insert(DBHelper.TABLE_ZOOLOGICO, null, values)
    }

    // Editar un zoológico
    fun updateZoologico(zoologico: Zoologico): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_NOMBRE, zoologico.name)
            put(DBHelper.COLUMN_FECHA_INICIO, zoologico.startDate)
            put(DBHelper.COLUMN_INGRESOS_ANUALES, zoologico.anualIncome)
            put(DBHelper.COLUMN_ESTA_ABIERTO, if (zoologico.isOpen) 1 else 0)
        }
        return db.update(DBHelper.TABLE_ZOOLOGICO, values, "${DBHelper.COLUMN_ID} = ?", arrayOf(zoologico.id.toString()))
    }

    // Eliminar un zoológico
    fun deleteZoologico(zoologicoId: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(DBHelper.TABLE_ZOOLOGICO, "${DBHelper.COLUMN_ID} = ?", arrayOf(zoologicoId.toString()))
    }

    // Obtener todos los zoológicos
    fun getAllZoologicos(): List<Zoologico> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DBHelper.TABLE_ZOOLOGICO, null, null, null, null, null, null)
        val zoologicos = mutableListOf<Zoologico>()
        while (cursor.moveToNext()) {
            val zoologico = Zoologico(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NOMBRE)),
                startDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FECHA_INICIO)),
                anualIncome = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INGRESOS_ANUALES)),
                isOpen = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ESTA_ABIERTO)) == 1
            )
            zoologicos.add(zoologico)
        }
        cursor.close()
        return zoologicos
    }

    // Obtener un zoológico por ID
    fun getZoologicoById(id: Int): Zoologico? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_ZOOLOGICO,
            null,
            "${DBHelper.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var zoologico: Zoologico? = null
        if (cursor.moveToFirst()) {
            zoologico = Zoologico(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NOMBRE)),
                startDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FECHA_INICIO)),
                isOpen = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ESTA_ABIERTO)) == 1,
                anualIncome = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_INGRESOS_ANUALES))
            )
        }
        cursor.close()
        return zoologico
    }
}
