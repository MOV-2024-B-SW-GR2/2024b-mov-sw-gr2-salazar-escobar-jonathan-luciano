import com.google.gson.Gson
import java.io.File
import java.util.Date

// Clase Zoologico
data class Zoologico(
    var id: Int,
    var nombre: String,
    var fechaInauguracion: Date,
    var cantidadAnimales: Int,
    var requiereReservacion: Boolean,
    var ingresosAnuales: Double
) {
    private var animales = ArrayList<Animal>()


    init {
        animales = Animal.getByZoo(id)
        cantidadAnimales = animales.size
    }

    // CRUD para Zoologico
    // Create
    fun createZoologico(newZoologico: Zoologico): Boolean {
        allZoos.add(newZoologico)
        return saveAll(allZoos)
    }

    fun getZoologicoById(id: Int): Zoologico {
        return allZoos.find { it.id == id } ?: throw NoSuchElementException("Zoológico no encontrado")
    }

    // Read All: Obtener todos los zoológicos
    fun readAllZoos(): ArrayList<Zoologico> {
        return allZoos
    }

    // Update: Actualizar un zoológico
    fun updateZoologico(newZoologico: Zoologico): Boolean {
        allZoos.forEach {
            if (it.id == newZoologico.id) {
                it.nombre = newZoologico.nombre
                it.fechaInauguracion = newZoologico.fechaInauguracion
                it.cantidadAnimales = newZoologico.cantidadAnimales
                it.requiereReservacion = newZoologico.requiereReservacion
                it.ingresosAnuales = newZoologico.ingresosAnuales
                return saveAll(allZoos)
            }
        }
        return false
    }

    // Delete: Eliminar un zoológico
    fun deleteZoologico(id: Int): Boolean {
        return allZoos.remove(getZoologicoById(id)) && saveAll(allZoos)
    }

    // Agregar un animal al zoológico
    fun addAnimal(animal: Animal): Boolean {
        animales.add(animal)
        cantidadAnimales = animales.size
        return updateZoologico(this) && animal.createAnimal(animal)
    }

    // Obtener todos los animales en el zoológico
    fun getAllAnimalsInZoo(): ArrayList<Animal> {
        return animales
    }

    //Eliminar un animal
    fun deleteAnimal(removeAnimal: Animal):Boolean{
        return animales.remove(removeAnimal) && updateZoologico(this)
    }

    // File
    companion object {
        var allZoos: ArrayList<Zoologico> = getAll()

        private fun getAll(): ArrayList<Zoologico> {
            val gson = Gson()
            val file = File("zoologicos.json")
            if (!file.exists()) return arrayListOf()
            val json = file.readText()
            return gson.fromJson(json, Array<Zoologico>::class.java).toList() as ArrayList<Zoologico>
        }

        private fun saveAll(allZoos: ArrayList<Zoologico>): Boolean {
            val gson = Gson()
            val jsonZoos = gson.toJson(allZoos)
            return try {
                File("zoologicos.json").writeText(jsonZoos)
                true
            } catch (e: Exception) {
                println("Error al guardar los datos de zoológicos: ${e.message}")
                false
            }
        }
    }
}
