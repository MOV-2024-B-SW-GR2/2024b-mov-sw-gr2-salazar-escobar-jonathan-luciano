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

class EditarZoologicoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etFechaInicio: EditText
    private lateinit var etIngresosAnuales: EditText
    private lateinit var cbEstaAbierto: CheckBox
    private lateinit var btnGuardar: Button
    private lateinit var zoologicoRepository: ZoologicoRepository
    private var zoologicoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_zoologico)

        zoologicoRepository = ZoologicoRepository(DBHelper(this))

        // Referenciar vistas
        etNombre = findViewById(R.id.etNombreZoologico)
        etFechaInicio = findViewById(R.id.etFechaInicioZoologico)
        etIngresosAnuales = findViewById(R.id.etIngresosAnuales)
        cbEstaAbierto = findViewById(R.id.cbEstaAbierto)
        btnGuardar = findViewById(R.id.btnGuardarZoologico)

        // Obtener ID del zoológico a editar
        zoologicoId = intent.getIntExtra("idZoologico", -1)

        if (zoologicoId != -1) {
            val zoologico = zoologicoRepository.getZoologicoById(zoologicoId!!)
            zoologico?.let {
                etNombre.setText(it.name)
                etFechaInicio.setText(it.startDate)
                etIngresosAnuales.setText(it.anualIncome.toString())
                cbEstaAbierto.isChecked = it.isOpen
            }
        }

        btnGuardar.setOnClickListener {
            zoologicoId?.let { id ->
                val updatedZoologico = Zoologico(
                    id = id,
                    name = etNombre.text.toString(),
                    startDate = etFechaInicio.text.toString(),
                    isOpen = cbEstaAbierto.isChecked,
                    anualIncome = etIngresosAnuales.text.toString().toDoubleOrNull() ?: 0.0
                )
                val result = zoologicoRepository.updateZoologico(updatedZoologico)
                if (result > 0) {
                    Toast.makeText(this, "Zoológico actualizado", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar el zoológico", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
