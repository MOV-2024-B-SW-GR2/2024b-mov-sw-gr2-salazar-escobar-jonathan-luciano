package com.example.myapplication1.repository

import android.content.ContentValues
import com.example.myapplication1.data.DBHelper
import com.example.myapplication1.models.Animal

class AnimalRepository(private val dbHelper: DBHelper) {

    // Agregar un animal
    fun addAnimal(animal: Animal): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_NOMBRE, animal.name)
            put(DBHelper.COLUMN_ESPECIE, animal.specie)
            put(DBHelper.COLUMN_EDAD, animal.age)
            put(DBHelper.COLUMN_CUMPLEANOS, animal.birthDay)
            put(DBHelper.COLUMN_PESO, animal.weight)
            put(DBHelper.COLUMN_ZOOLOGICO_ID, animal.zoologicoId)
            put(DBHelper.COLUMN_ESTA_ALIMENTADO, if (animal.isFeeded) 1 else 0)
        }
        return db.insert(DBHelper.TABLE_ANIMAL, null, values)
    }

    // Obtener animales por zoológico
    fun getAnimalesPorZoologico(zoologicoId: Int): List<Animal> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_ANIMAL,
            null,
            "${DBHelper.COLUMN_ZOOLOGICO_ID} = ?",
            arrayOf(zoologicoId.toString()),
            null,
            null,
            null
        )
        val animales = mutableListOf<Animal>()
        while (cursor.moveToNext()) {
            val animal = Animal(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NOMBRE)),
                specie = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ESPECIE)),
                age = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EDAD)),
                birthDay = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CUMPLEANOS)),
                weight = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PESO)),
                zoologicoId = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ZOOLOGICO_ID)),
                isFeeded = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ESTA_ALIMENTADO)) == 1
            )
            animales.add(animal)
        }
        cursor.close()
        return animales
    }

    // Actualizar un animal
    fun updateAnimal(animal: Animal): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_NOMBRE, animal.name)
            put(DBHelper.COLUMN_ESPECIE, animal.specie)
            put(DBHelper.COLUMN_EDAD, animal.age)
            put(DBHelper.COLUMN_CUMPLEANOS, animal.birthDay)
            put(DBHelper.COLUMN_PESO, animal.weight)
            put(DBHelper.COLUMN_ZOOLOGICO_ID, animal.zoologicoId)
            put(DBHelper.COLUMN_ESTA_ALIMENTADO, if (animal.isFeeded) 1 else 0)
        }
        return db.update(DBHelper.TABLE_ANIMAL, values, "${DBHelper.COLUMN_ID} = ?", arrayOf(animal.id.toString()))
    }

    // Eliminar un animal
    fun deleteAnimal(animalId: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(DBHelper.TABLE_ANIMAL, "${DBHelper.COLUMN_ID} = ?", arrayOf(animalId.toString()))
    }
}
