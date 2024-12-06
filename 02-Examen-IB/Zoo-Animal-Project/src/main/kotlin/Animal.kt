import com.google.gson.Gson
import java.io.File
import java.util.Date

data class Animal(
    var id: Int,
    var especie: String,
    var edad: Int,
    var idZoo: Int,
    var fechaNacimiento: Date,
    var estaAlimentado: Boolean,
    var peso: Double
) {
    init {
        allAnimals = getAll()
        this.id = allAnimals.size
    }

    constructor(
        id: Int?,
        especie: String?,
        edad: Int?,
        idZoo: Int?,
        fechaNacimiento: Date? ,
        estaAlimentado: Boolean?,
        peso: Double?
    ) : this(
        if(id == null) allAnimals.size else 1,
        if(especie == null) "Especie no especificada" else especie,
        if(edad == null) 0 else edad,
        if(idZoo == null) 0 else idZoo,
        if (fechaNacimiento == null) Date() else fechaNacimiento,
        if (estaAlimentado == null) false else estaAlimentado,
        if (peso == null) 0.0 else peso,
    )


    //Metodos CRUD
    //Create
    fun createAnimal(newAnimal: Animal): Boolean {
        allAnimals.add(newAnimal)
        return saveAll(allAnimals)
    }

    //Read
    fun getById(id: Int) : Animal {
        return allAnimals.find { it.id == id } ?: throw NoSuchElementException("Animal no encontrado")
    }



    fun readAll(): ArrayList<Animal> {
        return getAll()
    }

    //Update
    fun updateAnimal(newAnimal: Animal):Boolean{
        allAnimals.forEach{
            if(it.id == newAnimal.id){
                it.especie = newAnimal.especie
                it.edad = newAnimal.edad
                it.fechaNacimiento = newAnimal.fechaNacimiento
                it.peso = newAnimal.peso
                it.estaAlimentado = newAnimal.estaAlimentado
                it.idZoo = newAnimal.idZoo
                return saveAll(allAnimals)
            }
        }
        return false
    }

    //Delete
    fun deleteAnimal(removeAnimal: Animal):Boolean{
        return allAnimals.remove(removeAnimal) && saveAll(allAnimals)
    }

    companion object{
        var allAnimals: ArrayList<Animal> = getAll()

        private fun getAll(): ArrayList<Animal> {
            val gson = Gson()
            val file = File("animales.json")
            if (!file.exists()) return arrayListOf()
            val json = file.readText()
            return gson.fromJson(json, Array<Animal>::class.java).toList() as ArrayList<Animal>
        }


        fun saveAll(allAnimals: ArrayList<Animal>): Boolean {
            val gson = Gson()
            val jsonAnimales = gson.toJson(allAnimals)
            return try {
                File("animales.json").writeText(jsonAnimales)
                true
            } catch (e: Exception) {
                println("Error al guardar los datos: ${e.message}")
                false
            }
        }

        fun getByZoo(idZoo: Int): ArrayList<Animal> {
            return allAnimals.filter{ it.idZoo == idZoo } as ArrayList<Animal>
        }
    }
}


