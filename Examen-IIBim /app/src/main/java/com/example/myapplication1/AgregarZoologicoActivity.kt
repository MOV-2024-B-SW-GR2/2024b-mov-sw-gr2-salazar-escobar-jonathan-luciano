package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.data.DBHelper
import com.example.myapplication1.models.Zoologico
import com.example.myapplication1.repository.ZoologicoRepository

class AgregarZoologicoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etFechaInicio: EditText
    private lateinit var etIngresosAnuales: EditText
    private lateinit var cbEstaAbierto: CheckBox
    private lateinit var btnGuardar: Button
    private lateinit var zoologicoRepository: ZoologicoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_zoologico)

        zoologicoRepository = ZoologicoRepository(DBHelper(this))

        // Vincular vistas
        etNombre = findViewById(R.id.etNombre)
        etFechaInicio = findViewById(R.id.etFechaInicio)
        etIngresosAnuales = findViewById(R.id.etIngresosAnuales)
        cbEstaAbierto = findViewById(R.id.cbEstaAbierto)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val fechaInicio = etFechaInicio.text.toString().trim()
            val ingresosAnuales = etIngresosAnuales.text.toString().trim().toDoubleOrNull()
            val estaAbierto = cbEstaAbierto.isChecked

            // Verificar que los campos no estén vacíos
            if (nombre.isEmpty() || fechaInicio.isEmpty() || ingresosAnuales == null) {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Crear el nuevo zoológico
                val nuevoZoologico = Zoologico(
                    id = 0, // Se autogenera en la BD
                    name = nombre,
                    startDate = fechaInicio,
                    isOpen = estaAbierto,
                    anualIncome = ingresosAnuales,
                )

                // Insertar en la base de datos
                val insertResult = zoologicoRepository.addZoologico(nuevoZoologico)
                if (insertResult != -1L) {
                    Toast.makeText(this, "Zoológico agregado exitosamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar el zoológico", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
