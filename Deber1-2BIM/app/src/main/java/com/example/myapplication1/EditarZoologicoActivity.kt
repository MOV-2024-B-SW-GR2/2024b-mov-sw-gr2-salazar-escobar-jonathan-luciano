package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarZoologicoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etFechaInicio: EditText
    private lateinit var etIngresosAnuales: EditText
    private lateinit var cbEstaAbierto: CheckBox
    private lateinit var btnGuardar: Button
    private var zoologicoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_zoologico)

        // Referenciar vistas
        etNombre = findViewById(R.id.etNombreZoologico)
        etFechaInicio = findViewById(R.id.etFechaInicioZoologico)
        etIngresosAnuales = findViewById(R.id.etIngresosAnuales)
        cbEstaAbierto = findViewById(R.id.cbEstaAbierto)
        btnGuardar = findViewById(R.id.btnGuardarZoologico)

        // Obtener ID del zoológico a editar
        zoologicoId = intent.getIntExtra("idZoologico", -1)

        if (zoologicoId != -1) {
            // Si se pasa un ID válido, cargar los datos del zoológico
            val zoologico = BBaseDatosMemoria.zoologicos.find { it.id == zoologicoId }
            zoologico?.let {
                etNombre.setText(it.name)
                etFechaInicio.setText(it.startDate)
                etIngresosAnuales.setText(it.anualIncome.toString())
                cbEstaAbierto.isChecked = it.isOpen
            }
        }

        btnGuardar.setOnClickListener {
            // Actualizar los datos del zoológico
            zoologicoId?.let { id ->
                val zoologico = BBaseDatosMemoria.zoologicos.find { it.id == id }
                zoologico?.apply {
                    name = etNombre.text.toString()
                    startDate = etFechaInicio.text.toString()
                    anualIncome = etIngresosAnuales.text.toString().toDoubleOrNull() ?: 0.0
                    isOpen = cbEstaAbierto.isChecked
                }

                Toast.makeText(this, "Zoológico actualizado", Toast.LENGTH_SHORT).show()

                // Enviar el resultado de vuelta a la actividad anterior
                setResult(RESULT_OK)
                finish()  // Regresar a la pantalla principal
            }
        }
    }
}
