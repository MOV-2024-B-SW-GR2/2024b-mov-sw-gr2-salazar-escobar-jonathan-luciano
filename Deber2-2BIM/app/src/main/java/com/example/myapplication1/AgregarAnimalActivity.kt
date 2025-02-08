package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.data.DBHelper
import com.example.myapplication1.models.Animal
import com.example.myapplication1.repository.AnimalRepository

class AgregarAnimalActivity : AppCompatActivity() {
    private lateinit var etNombre: EditText
    private lateinit var etEspecie: EditText
    private lateinit var etEdad: EditText
    private lateinit var etCumpleanos: EditText
    private lateinit var etPeso: EditText
    private lateinit var btnGuardarAnimal: Button
    private lateinit var animalRepository: AnimalRepository

    private var zoologicoId: Int = 0  // ID del zoológico al que pertenece el animal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_animal)

        animalRepository = AnimalRepository(DBHelper(this))

        // Enlazar los elementos del layout
        etNombre = findViewById(R.id.etNombre)
        etEspecie = findViewById(R.id.etEspecie)
        etEdad = findViewById(R.id.etEdad)
        etCumpleanos = findViewById(R.id.etCumpleanos)
        etPeso = findViewById(R.id.etPeso)
        btnGuardarAnimal = findViewById(R.id.btnGuardarAnimal)

        // Obtener el ID del zoológico desde el intent
        zoologicoId = intent.getIntExtra("idZoologico", 0)

        // Lógica para guardar un nuevo animal
        btnGuardarAnimal.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val especie = etEspecie.text.toString().trim()
            val edad = etEdad.text.toString().trim()
            val cumpleanos = etCumpleanos.text.toString().trim()
            val peso = etPeso.text.toString().trim()

            // Verificar que los campos no estén vacíos
            if (nombre.isEmpty() || especie.isEmpty() || edad.isEmpty() || cumpleanos.isEmpty() || peso.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Crear el nuevo animal
                val nuevoAnimal = Animal(
                    id = 0,  // Se autogenera en la BD
                    name = nombre,
                    specie = especie,
                    age = edad.toInt(),
                    birthDay = cumpleanos,
                    isFeeded = false, // Puedes modificar este valor si es necesario
                    weight = peso.toDouble(),
                    zoologicoId = zoologicoId
                )

                // Insertar en la base de datos
                val insertResult = animalRepository.addAnimal(nuevoAnimal)
                if (insertResult != -1L) {
                    Toast.makeText(this, "Animal agregado exitosamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar el animal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}