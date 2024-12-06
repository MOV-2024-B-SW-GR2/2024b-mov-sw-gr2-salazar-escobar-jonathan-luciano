package org.example

import Animal
import java.util.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val scanner = java.util.Scanner(System.`in`)

    while (true) {
        println("\n \t--- Menú de Operaciones CRUD ---")
        println("\n--- Animales ---")
        println("1. Crear Animal")
        println("2. Mostrar todos los animales")
        println("3. Actualizar Animal")
        println("4. Eliminar Animal")
        println("\n\n--- Zoologico ---")
        println("5. Nuevo Zoologico")
        println("6. Ver Todos los Zoologicos")
        println("7. Actualizar Zoologico")
        println("8. Eliminar Zoologico")
        println("9. Salir")
        print("Selecciona una opción (1-6): ")

        when (scanner.nextInt()) {
            1 -> { //Crear animal
                println("Ingresa la especie del animal:")
                val especie = scanner.next()
                println("Ingresa la edad del animal:")
                val edad = scanner.nextInt()
                println("Ingresa el ID del Zoologico:")
                val idZoo = scanner.nextInt()
                println("Ingresa el peso del animal:")
                val peso = scanner.nextDouble()
                println("El animal está alimentado (true/false):")
                val estaAlimentado = scanner.nextBoolean()
                val fechaNacimiento = Date() // Usamos la fecha actual como ejemplo
                val nuevoAnimal = Animal(null, especie, edad, idZoo, fechaNacimiento, estaAlimentado, peso)

                // Guardar el animal
                val animal = Animal(null, especie, edad, idZoo, fechaNacimiento, estaAlimentado, peso)
                if (animal.createAnimal(nuevoAnimal)) {
                    println("Animal creado exitosamente.")
                } else {
                    println("Error al crear el animal.")
                }
            }
            2 -> { //Mostrar todos los animales
                println("--- Todos los animales --")
                Animal.allAnimals.toString()
            }
            3 -> {//Actualizar Animal
                println("Ingresa el id del animal que quieres actualizar:")
                val id = scanner.nextInt()
                val animal = Animal(null, null, null, null, null, false,null)
                val animalExistente = animal.getById(id)

                if (animalExistente != null) {
                    println("Nueva especie del animal:")
                    val nuevaEspecie = scanner.next()
                    println("Nueva edad del animal:")
                    val nuevaEdad = scanner.nextInt()
                    println("Nueva Zoologico del animal (Ingrese ID):")
                    val nuevoZoo = scanner.nextInt()
                    println("Nuevo peso del animal:")
                    val nuevoPeso = scanner.nextDouble()
                    println("El animal está alimentado (true/false):")
                    val nuevoAlimentado = scanner.nextBoolean()

                    val updatedAnimal = Animal(id, nuevaEspecie, nuevaEdad, nuevoZoo, Date(), nuevoAlimentado, nuevoPeso)

                    if (animal.updateAnimal(updatedAnimal)) {
                        println("Animal actualizado correctamente.")
                    } else {
                        println("No se pudo actualizar el animal.")
                    }
                } else {
                    println("Animal no encontrado.")
                }
            }
            4 -> {//Eliminar Animal
                println("Ingresa el id del animal a eliminar:")
                val id = scanner.nextInt()

                val animal = Animal(id, null, null, null, null, false,null)

                if (animal.deleteAnimal(animal)) {
                    println("Animal eliminado correctamente.")
                } else {
                    println("No se pudo eliminar el animal.")
                }
            }
            5 -> {
                println("Información del Zoológico: ")
                println("Nombre: ${zoo.nombre}")
                println("Fecha de Inauguración: ${zoo.fechaInauguracion}")
                println("Cantidad de animales: ${zoo.cantidadAnimales}")
                println("Requiere reservación: ${zoo.requiereReservacion}")
                println("Ingresos anuales: ${zoo.ingresosAnuales}")
            }
            6 -> {
                println("Saliendo...")
                break
            }
            else -> println("Opción no válida, por favor ingresa un número entre 1 y 6.")
        }

    }
}