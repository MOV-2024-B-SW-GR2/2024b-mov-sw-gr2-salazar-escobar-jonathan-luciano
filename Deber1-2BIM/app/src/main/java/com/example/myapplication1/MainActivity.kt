package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.AnimalesActivity.Companion.EDITAR_ANIMAL_REQUEST
import com.example.myapplication1.adapters.ZoologicosAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarZoologico: Button
    private val zoologicos = BBaseDatosMemoria.zoologicos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewZoologicos)
        btnAgregarZoologico = findViewById(R.id.btnAgregarZoologico)

        // Configuración del adapter para el RecyclerView
        val adapter = ZoologicosAdapter(
            zoologicos,
            onClick = { zoologico ->
                val intent = Intent(this, AnimalesActivity::class.java)
                intent.putExtra("idZoologico", zoologico.id)
                startActivityForResult(intent, EDITAR_ANIMAL_REQUEST)
            },
            onEdit = { zoologico ->
                // Lógica para editar el zoológico
                val intent = Intent(this, EditarZoologicoActivity::class.java)
                intent.putExtra("idZoologico", zoologico.id)
                startActivityForResult(intent, EDITAR_ZOOLOGICO_REQUEST)
            },
            onDelete = { id ->
                // Eliminar zoológico y actualizar el RecyclerView
                zoologicos.removeAll { it.id == id }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        )

        // Configurar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Manejo de la acción de agregar un concesionario
        btnAgregarZoologico.setOnClickListener {
            val intent = Intent(this, AgregarZoologicoActivity::class.java)
            startActivityForResult(intent, AGREGAR_ZOOLOGICO_REQUEST)
        }
    }

    // Este metodo es necesario para recibir el resultado de la actividad de edición
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDITAR_ZOOLOGICO_REQUEST && resultCode == RESULT_OK) {
            // Si la actividad de edición se completó correctamente, actualizar la lista
            recyclerView.adapter?.notifyDataSetChanged()
        } else if (requestCode == AGREGAR_ZOOLOGICO_REQUEST && resultCode == RESULT_OK) {
            // Si la actividad de agregar zoológico se completó correctamente, actualizar la lista
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    companion object {
        const val EDITAR_ZOOLOGICO_REQUEST = 1
        const val AGREGAR_ZOOLOGICO_REQUEST = 2
    }
}
