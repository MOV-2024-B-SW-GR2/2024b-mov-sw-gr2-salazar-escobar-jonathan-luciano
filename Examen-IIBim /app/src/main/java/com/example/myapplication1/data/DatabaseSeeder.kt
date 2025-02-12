package com.example.myapplication1.data

import android.content.Context
import com.example.myapplication1.models.Animal
import com.example.myapplication1.models.Zoologico
import com.example.myapplication1.repository.AnimalRepository
import com.example.myapplication1.repository.ZoologicoRepository

class DatabaseSeeder(context: Context) {
    private val dbHelper = DBHelper(context)
    private val zoologicoRepository = ZoologicoRepository(dbHelper)
    private val animalRepository = AnimalRepository(dbHelper)

    fun seedData() {
        if (zoologicoRepository.getAllZoologicos().isEmpty()) {
            // Insertar zoológicos
            zoologicoRepository.addZoologico(Zoologico(0, "Safari Park", "2001-06-15", true, 500000.0))
            zoologicoRepository.addZoologico(Zoologico(0, "Amazon Rainforest Zoo", "1998-09-25", false, 750000.0))
            print("Se han creado los Zoologicos")

            // Insertar animales en los zoológicos creados
            val zoo1Id = zoologicoRepository.getAllZoologicos().getOrNull(0)?.id ?: return
            val zoo2Id = zoologicoRepository.getAllZoologicos().getOrNull(1)?.id ?: return

            val animales = listOf(
                Animal(0, "Leo", "León", 8, "2015-04-10", false, 190.0, zoo1Id),
                Animal(0, "Stripes", "Tigre", 6, "2017-08-23", false, 220.5, zoo1Id),
                Animal(0, "Dumbo", "Elefante", 10, "2012-12-01", false, 1200.0, zoo1Id),
                Animal(0, "Gerry", "Jirafa", 5, "2018-06-18", false, 900.0, zoo2Id),
                Animal(0, "Milo", "Mono", 3, "2020-03-15", false, 35.0, zoo2Id),
                Animal(0, "Koko", "Gorila", 7, "2016-09-05", false, 180.0, zoo2Id),
                Animal(0, "Polly", "Loro", 2, "2021-07-20", false, 1.5, zoo2Id),
            )

            // Insertar los animales en la base de datos
            for (animal in animales) {
                animalRepository.addAnimal(animal)
            }

        }
    }
}
