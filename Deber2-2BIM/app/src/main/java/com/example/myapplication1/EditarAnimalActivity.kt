package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.models.Animal
import com.example.myapplication1.repository.AnimalRepository
import com.example.myapplication1.data.DBHelper

class EditarAnimalActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etEspecie: EditText
    private lateinit var etEdad: EditText
    private lateinit var etCumpleanos: EditText
    private lateinit var etPeso: EditText
    private lateinit var btnGuardar: Button
    private lateinit var animalRepository: AnimalRepository

    private var idAnimal: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_animal)

        animalRepository = AnimalRepository(DBHelper(this))

        // Enlazar los elementos del layout
        etNombre = findViewById(R.id.etNombre)
        etEspecie = findViewById(R.id.etEspecie)
        etEdad = findViewById(R.id.etEdad)
        etCumpleanos = findViewById(R.id.etCumpleanos)
        etPeso = findViewById(R.id.etPeso)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Obtener el ID del animal pasado por el Intent
        idAnimal = intent.getIntExtra("idAnimal", -1)

        // Buscar el animal en la base de datos
        val animal = animalRepository.getAnimalesPorZoologico(idAnimal!!).firstOrNull()

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

            // Actualizar el animal en la base de datos
            val animalActualizado = Animal(idAnimal!!, nombre, especie, edad, cumpleanos, false, peso, animal?.zoologicoId ?: 0)
            val result = animalRepository.updateAnimal(animalActualizado)

            if (result > 0) {
                Toast.makeText(this, "Animal actualizado exitosamente", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK, Intent())
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar el animal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
