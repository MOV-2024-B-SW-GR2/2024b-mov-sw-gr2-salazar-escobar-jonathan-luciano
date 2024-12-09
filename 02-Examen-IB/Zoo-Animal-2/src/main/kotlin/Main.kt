package org.example

import com.google.gson.Gson
import java.io.File
import java.util.*

fun main() {
    val scanner = java.util.Scanner(System.`in`)
    val pathNameAnimals = "src/main/kotlin/animals.json"
    val fileNameZoos = "src/main/kotlin/zoos.json"

    var allAnimals: ArrayList<Animal> = getAnimals(pathNameAnimals)
    var allZoos: ArrayList<Zoologico> = getZoos(fileNameZoos)

    do {
        println("\n \t--- Menú de Operaciones CRUD ---")
        println("--- Animals ---")
        println("1. Create Animal")
        println("2. Read Animals")
        println("3. Update Animal")
        println("4. Delete Animal")
        println("--- Zoologico ---")
        println("5. Create Zoo")
        println("6. Read Zoo")
        println("7. Update Zoo")
        println("8. Delete Zoo")
        println("--- Animals in Zoo ---")
        println("9. Add Animal to Zoo")
        println("10. Delete Animal from Zoo")
        println("11. Salir")
        print("Selecciona una opción (1-11): ")
        val opc = scanner.nextInt()

        when (opc) {
            1 -> { //Crear Animal
                println("\t --- Create Animal --")
                val id = allAnimals.size //id Autoincremental
                println("Ingresa el nombre del animal:")
                val name = scanner.next()
                println("Ingresa la especie del animal:")
                val specie = scanner.next()
                println("Ingresa la edad del animal:")
                val age = scanner.nextInt()
                val birthDay = Date()
                println("Ingresa el peso del animal:")
                val weight = scanner.nextDouble()
                println("El animal está alimentado (true/false):")
                val isFeeded = scanner.nextBoolean()

                val nuevoAnimal = Animal(id,name, specie, age, birthDay, isFeeded, weight)

                if(allAnimals.add(nuevoAnimal) && saveAnimals(pathNameAnimals, allAnimals)){
                    println("Se ha guardado el nuevo animal")
                }else{
                    println("Error: No se ha podido guardar el animal")
                }
            }

            2 -> { //Read Animals
                println("\t --- Read Animal --")
                allAnimals = getAnimals(pathNameAnimals)
                allAnimals.forEach {
                    println(it.toString())
                    println()
                }
            }
            3 -> { //Update Animal
                println("\t --- Update Animal --")
                println("Ingresa el ID del animal:")
                val id = scanner.nextInt()
                var animalFound = allAnimals.find { it.id == id }
                if (animalFound != null) {
                    do {
                        println("1. Actualizar nombre    \t (Actual: ${animalFound.name}).")
                        println("2. Actualizar especie   \t (Actual: ${animalFound.specie}).")
                        println("3. Actualizar edad      \t (Actual: ${animalFound.age}).")
                        println("4. Actualizar peso      \t (Actual: ${animalFound.weight}).")
                        println("5. Actualizar BirthDay  \t (Actual: ${animalFound.birthDay}).")
                        println("6. Actualizar Alimentado\t (Actual: ${animalFound.isFeeded}).")
                        println("7. Cancelar.")
                        print("Selecciona una opción (1-7): ")
                        val opcUpdate = scanner.nextInt()

                        when(opcUpdate) {
                            1 -> {
                                print("Ingresa el nuevo nombre: ")
                                val newName = scanner.next()
                                animalFound.name = newName
                            }
                            2 ->{
                                print("Ingresa la nueva especie: ")
                                val newSpecie = scanner.next()
                                animalFound.specie = newSpecie
                            }
                            3 -> {
                                print("Ingresa la nueva edad: ")
                                val newAge = scanner.nextInt()
                                animalFound.age = newAge
                            }
                            4 -> {
                                print("Ingresa el nuevo peso: ")
                                val newPeso = scanner.nextDouble()
                                animalFound.weight = newPeso
                            }
                            5 -> {
                                print("Ingresa la nueva fecha de nacimiento: ")
                                val newFecha = scanner.next() //validar el formato de la fecha
                                animalFound.birthDay = Date(newFecha)
                            }
                            6 -> {
                                print("Actualizar si esta alimentado: ")
                                val newFeed = scanner.nextBoolean()
                                animalFound.isFeeded = newFeed
                            }
                        }
                    } while (opcUpdate in 1..6)
                    if(saveAnimals(pathNameAnimals, allAnimals)){
                        println("Se ha actualizado el animal")
                    }else{
                        println("Error: No se ha podido actualizar el animal")
                    }
                }else{
                    println("No se ha encontrado el Animal")
                }
            }
            4 -> {
                println("\t --- Delete Animal --")
                println("Ingresa el ID del animal:")
                val id = scanner.nextInt()
                var animalFound = allAnimals.find { it.id == id }
                if (animalFound != null) {
                    if(allAnimals.remove(animalFound) && saveAnimals(pathNameAnimals, allAnimals)){
                        println("Se ha eliminado el animal")
                    }else{
                        println("Error: No se ha podido eliminar el animal")
                    }
                }else{
                    println("No se ha encontrado el animal")
                }
            }
            //Operaciones del Zoologico
            5 -> {
                println("\t --- Create Zoologico --")
                val id = allZoos.size //id Autoincremental
                println("Ingresa el nombre del Zoo:")
                val name = scanner.next()
                val startDate = Date()
                println("Ingresa la cantidad de animales del Zoo:")
                val animalCount = scanner.nextInt()
                println("El Zoo esta abierto (true/false):")
                val isOpen = scanner.nextBoolean()
                println("Ingrese el ingreso anual")
                val anualIncome = scanner.nextDouble()

                val newZoo = Zoologico(id, name,startDate,animalCount, isOpen, anualIncome)

                if(allZoos.add(newZoo) && saveZoos(fileNameZoos,allZoos)){
                    println("Se ha guardado el nuevo Zoo")
                }else{
                    println("Error: No se ha podido guardar el Zoo")
                }

            }
            6 -> {
                allZoos = getZoos(fileNameZoos)
                println("\t --- Read Zoo --")
                allZoos.forEach {
                    println(it.toString())
                    println()
                }
            }
            7 -> {
                println("\t--- Update Zoo --")
                println("Ingresa el ID del Zoo:")
                val id = scanner.nextInt()
                var zooFound = allZoos.find { it.id == id }
                if (zooFound != null) {
                    do {
                        println("1. Actualizar nombre         \t (Actual: ${zooFound.name}).")
                        println("2. Actualizar Fecha de inicio\t (Actual: ${zooFound.startDate.toString()}).")
                        println("3. Actualizar conteo animales\t (Actual: ${zooFound.animalsCount}).")
                        println("4. Actualizar esta abierto   \t (Actual: ${zooFound.isOpen}).")
                        println("5. Actualizar ingreso anual  \t (Actual: ${zooFound.anualIncome}).")
                        println("6. Cancelar.")
                        val opcUpdate = scanner.nextInt()

                        when(opcUpdate) {
                            1 -> {
                                print("Ingresa el nuevo nombre: ")
                                val newName = scanner.next()
                                zooFound.name = newName
                            }
                            2 ->{
                                print("Ingresa la nueva fecha de inicio: ")
                                val newFecha = scanner.next() //validar el formato de la fecha
                                zooFound.startDate = Date(newFecha)
                            }
                            3 -> {
                                print("Ingresa el conteo de animales del Zoo: ")
                                val newAnimalCount = scanner.nextInt()
                                zooFound.animalsCount = newAnimalCount
                            }
                            4 -> {
                                print("Ingresa el nuevo estado del Zoo: ")
                                val newIsOpen = scanner.nextBoolean()
                                zooFound.isOpen = newIsOpen
                            }
                            5 -> {
                                print("Ingresa el nuevo ingreso anual: ")
                                val newAnualIncome = scanner.nextDouble()
                                zooFound.anualIncome = newAnualIncome
                            }
                        }
                    } while (opcUpdate in 1..5)
                    if(saveAnimals(pathNameAnimals, allAnimals)){
                        println("Se ha actualizado el Zoo")
                    }else{
                        println("Error: No se ha podido actualizar el Zoo")
                    }
                }else{
                    println("No se ha encontrado el Zoo")
                }
            }

            8 -> {
                println("\t --- Delete Zoo --")
                println("Ingresa el ID del Zoo:")
                val id = scanner.nextInt()
                var zooFound = allZoos.find { it.id == id }
                if(zooFound != null) {
                    if (allZoos.remove(zooFound) && saveZoos(fileNameZoos, allZoos)){
                        println("Se ha eliminado el Zoo")
                    }else{
                        println("Error: No se ha podido eliminar el Zoo")
                    }

                }else{
                    println("No se ha encontrado el Zoo")
                }
            }
            9 -> {
                println("\t --- Add Animal to Zoo --")
                println("Ingresa el ID del Zoo:")
                val idZoo = scanner.nextInt()
                var zooFound = allZoos.find { it.id == idZoo }
                println("Ingresa el ID del Animal:")
                val id = scanner.nextInt()
                var animalFound = allAnimals.find { it.id == id }
                if (zooFound != null) {
                    if(animalFound != null) {
                        println("Se ha encontrado el animal ${animalFound.name}")
                        if(zooFound.animals.find { it.id == animalFound.id }?.equals(animalFound) == true){
                            println("Ya se ha agregado el animal al zoo")
                        }else{
                            zooFound.animals.add(animalFound)
                        }

                    }else{
                        println("\t --- Create new Animal --")
                        val id = allAnimals.size //id Autoincremental
                        println("Ingresa el nombre del animal:")
                        val name = scanner.next()
                        println("Ingresa la especie del animal:")
                        val specie = scanner.next()
                        println("Ingresa la edad del animal:")
                        val age = scanner.nextInt()
                        val birthDay = Date()
                        println("Ingresa el peso del animal:")
                        val weight = scanner.nextDouble()
                        println("El animal está alimentado (true/false):")
                        val isFeeded = scanner.nextBoolean()

                        val nuevoAnimal = Animal(id,name, specie, age, birthDay, isFeeded, weight)

                        if(allAnimals.add(nuevoAnimal) && saveAnimals(pathNameAnimals, allAnimals)){
                            println("Se ha guardado el nuevo animal")
                        }else{
                            println("Error: No se ha podido guardar el animal")
                        }



                    }
                }else{
                    println("No se ha encontrado el Zoologico")
                }
            }
            10 -> {
                println("\t --- Delete Animal from Zoo --")
                println("Ingresa el ID del Zoo:")
                val idZoo = scanner.nextInt()
                var zooFound = allZoos.find { it.id == idZoo }
                println("Ingresa el ID del Animal:")
                val id = scanner.nextInt()
                var animalFound = allAnimals.find { it.id == id }
                if (animalFound != null  && zooFound != null) {
                    zooFound.animals.remove(animalFound)
                }
            }
        }
        saveAnimals(pathNameAnimals,allAnimals)
        saveZoos(fileNameZoos,allZoos)
    } while (opc in 1..10)

}
fun getAnimals(fileName: String): ArrayList<Animal> {
    val gson = Gson()
    val file = File(fileName)
    if (!file.exists()) return arrayListOf()
    val json = file.readText()
    println("Se ha leido los animales")
    return gson.fromJson(json, Array<Animal>::class.java).toList() as ArrayList<Animal>
}

fun saveAnimals(fileName: String, allAnimals: ArrayList<Animal>): Boolean {
    val gson = Gson()
    val jsonAnimales = gson.toJson(allAnimals)
    return try {
        File(fileName).writeText("[]")
        File(fileName).writeText(jsonAnimales)
        true
    } catch (e: Exception) {
        println("Error al guardar los datos: ${e.message}")
        false
    }
}

private fun getZoos(fileName: String): ArrayList<Zoologico> {
    val gson = Gson()
    val file = File(fileName)
    if (!file.exists()) return arrayListOf()
    val json = file.readText()
    println("Se ha leido los Zoos")
    return gson.fromJson(json, Array<Zoologico>::class.java).toList() as ArrayList<Zoologico>
}

private fun saveZoos(fileName: String, allZoos: ArrayList<Zoologico>): Boolean {
    val gson = Gson()
    val jsonZoos = gson.toJson(allZoos)
    return try {
        File(fileName).writeText("[]")
        File(fileName).writeText(jsonZoos)
        true
    } catch (e: Exception) {
        println("Error al guardar los datos de zoológicos: ${e.message}")
        false
    }
}