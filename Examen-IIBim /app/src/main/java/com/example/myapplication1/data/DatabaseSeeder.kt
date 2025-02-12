package com.example.myapplication1.data

import android.content.Context
import com.example.myapplication1.models.Animal
import com.example.myapplication1.models.Ubicacion
import com.example.myapplication1.models.Zoologico
import com.example.myapplication1.repository.AnimalRepository
import com.example.myapplication1.repository.UbicacionRepository
import com.example.myapplication1.repository.ZoologicoRepository

class DatabaseSeeder(context: Context) {
    private val dbHelper = DBHelper(context)
    private val zoologicoRepository = ZoologicoRepository(dbHelper)
    private val animalRepository = AnimalRepository(dbHelper)
    private val ubicacionRepository = UbicacionRepository(dbHelper)

    fun seedData() {
        if (zoologicoRepository.getAllZoologicos().isEmpty()) {
            println("Seeding Zoos, Animals, and Locations...")

            // Insert Zoos
            val zoo1Id = zoologicoRepository.addZoologico(Zoologico(0, "Safari Park", "2001-06-15", true, 500000.0)).toInt()
            val zoo2Id = zoologicoRepository.addZoologico(Zoologico(0, "Amazon Rainforest Zoo", "1998-09-25", false, 750000.0)).toInt()
            val zoo3Id = zoologicoRepository.addZoologico(Zoologico(0, "Guayllabamba Zoo", "1997-05-12", true, 600000.0)).toInt()

            println("Zoos created successfully.")

            // Insert Animals
            val animals = listOf(
                Animal(0, "Leo", "León", 8, "2015-04-10", false, 190.0, zoo1Id),
                Animal(0, "Stripes", "Tigre", 6, "2017-08-23", false, 220.5, zoo1Id),
                Animal(0, "Dumbo", "Elefante", 10, "2012-12-01", false, 1200.0, zoo1Id),
                Animal(0, "Gerry", "Jirafa", 5, "2018-06-18", false, 900.0, zoo2Id),
                Animal(0, "Milo", "Mono", 3, "2020-03-15", false, 35.0, zoo2Id),
                Animal(0, "Koko", "Gorila", 7, "2016-09-05", false, 180.0, zoo2Id),
                Animal(0, "Polly", "Loro", 2, "2021-07-20", false, 1.5, zoo3Id),
                Animal(0, "Paco", "Tortuga", 80, "1943-09-10", false, 50.0, zoo3Id)
            )

            for (animal in animals) {
                animalRepository.addAnimal(animal)
            }
            println("Animals seeded successfully.")

            // Insert Locations
            val locations = listOf(
                Ubicacion(0, -0.180653, -78.467834, zoo1Id), // Safari Park
                Ubicacion(0, 48.858844, 2.294351, zoo2Id), // Amazon Rainforest Zoo
                Ubicacion(0, -0.1042, -78.3589, zoo3Id) // Guayllabamba Zoo (Quito, Ecuador)
            )

            for (location in locations) {
                ubicacionRepository.addUbicacion(location)
            }
            println("Locations seeded successfully.")

            println("Seeding completed!")
        } else {
            println("Database already has data. No seeding required.")
        }
    }
}
