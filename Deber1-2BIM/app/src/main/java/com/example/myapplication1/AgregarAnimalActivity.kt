package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.models.Animal

class AgregarAnimalActivity : AppCompatActivity() {
    private lateinit var etNombre: EditText
    private lateinit var etEspecie: EditText
    private lateinit var etEdad: EditText
    private lateinit var etCumpleanos: EditText
    private lateinit var etPeso: EditText
    private lateinit var btnGuardarAnimal: Button

    private var zoologicoId: Int = 0  // ID del zoológico al que pertenece el animal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_animal)

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
                    id = BBaseDatosMemoria.zoologicos.flatMap { it.animals }.size + 1,
                    name = nombre,
                    specie = especie,
                    age = edad.toInt(),
                    birthDay = cumpleanos,
                    isFeeded = false, // Puedes modificar este valor si es necesario
                    weight = peso.toDouble()
                )

                // Obtener el zoológico y agregar el animal
                val zoologico = BBaseDatosMemoria.zoologicos.find { it.id == zoologicoId }
                zoologico?.animals?.add(nuevoAnimal)

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Animal agregado exitosamente", Toast.LENGTH_SHORT).show()

                // Devolver resultado OK a la actividad anterior
                setResult(RESULT_OK)
                finish()  // Finaliza la actividad
            }
        }
    }
}
