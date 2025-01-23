package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.models.Animal


class EditarAnimalActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etEspecie: EditText
    private lateinit var etEdad: EditText
    private lateinit var etCumpleanos: EditText
    private lateinit var etPeso: EditText
    private lateinit var btnGuardar: Button

    private var idAnimal: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_animal)

        // Enlazar los elementos del layout
        etNombre = findViewById(R.id.etNombre)
        etEspecie = findViewById(R.id.etEspecie)
        etEdad = findViewById(R.id.etEdad)
        etCumpleanos = findViewById(R.id.etCumpleanos)
        etPeso = findViewById(R.id.etPeso)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Obtener el ID del animal pasado por el Intent
        idAnimal = intent.getIntExtra("idAnimal", -1)

        // Buscar el animal usando el ID
        val animal = buscarAnimalPorId(idAnimal)

        // Cargar los datos del animal en los EditText
        animal?.let {
            etNombre.setText(it.name)
            etEspecie.setText(it.specie)
            etEdad.setText(it.age.toString())
            etCumpleanos.setText(it.birthDay)
            etPeso.setText(it.weight.toString())
        }

        // Guardar los cambios
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val especie = etEspecie.text.toString()
            val edad = etEdad.text.toString().toInt()
            val cumpleanos = etCumpleanos.text.toString()
            val peso = etPeso.text.toString().toDouble()

            // Validar los datos ingresados
            if (nombre.isEmpty() || especie.isEmpty() || cumpleanos.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Actualizar el animal
            val animalActualizado = Animal(idAnimal!!, nombre, especie, edad, cumpleanos, false, peso)
            actualizarAnimal(animalActualizado)

            // Mostrar mensaje de éxito
            Toast.makeText(this, "Animal actualizado exitosamente", Toast.LENGTH_SHORT).show()

            // Devolver el resultado y cerrar la actividad
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    // Buscar un animal por ID en la lista de animales de los zoológicos
    private fun buscarAnimalPorId(id: Int?): Animal? {
        for (zoologico in BBaseDatosMemoria.zoologicos) {
            val animal = zoologico.animals.find { it.id == id }
            if (animal != null) return animal
        }
        return null
    }

    // Actualizar el animal en la base de datos
    private fun actualizarAnimal(animal: Animal) {
        for (zoologico in BBaseDatosMemoria.zoologicos) {
            val index = zoologico.animals.indexOfFirst { it.id == animal.id }
            if (index != -1) {
                zoologico.animals[index] = animal
                break
            }
        }
    }
}
