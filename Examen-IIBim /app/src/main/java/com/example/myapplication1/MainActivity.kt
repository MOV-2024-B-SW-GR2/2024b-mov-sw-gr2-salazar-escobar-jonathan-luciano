package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.AnimalesActivity.Companion.EDITAR_ANIMAL_REQUEST
import com.example.myapplication1.adapters.ZoologicosAdapter
import com.example.myapplication1.repository.ZoologicoRepository
import com.example.myapplication1.data.DBHelper
import com.example.myapplication1.data.DatabaseSeeder
import com.example.myapplication1.models.Zoologico

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarZoologico: Button
    private lateinit var zoologicoRepository: ZoologicoRepository
    private lateinit var adapter: ZoologicosAdapter
    private var zoologicos = mutableListOf<Zoologico>()
    private lateinit var btnZoologicoMapa: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ejecutar el seeder
        val seeder = DatabaseSeeder(this)
        seeder.seedData()

        zoologicoRepository = ZoologicoRepository(DBHelper(this))

        recyclerView = findViewById(R.id.recyclerViewZoologicos)
        btnAgregarZoologico = findViewById(R.id.btnAgregarZoologico)
        btnZoologicoMapa = findViewById(R.id.btnZoologicosMap)

        btnAgregarZoologico.setOnClickListener {
            val intent = Intent(this, AgregarZoologicoActivity::class.java)
            startActivityForResult(intent, AGREGAR_ZOOLOGICO_REQUEST)
        }

        btnZoologicoMapa.setOnClickListener{
            irActividad(ZoologicoMap::class.java)
        }

        loadZoologicos()

        val adapter = ZoologicosAdapter(
        zoologicos,
        onClick = { zoologico ->
            val intent = Intent(this, AnimalesActivity::class.java)
            intent.putExtra("idZoologico", zoologico.id)
            startActivityForResult(intent, EDITAR_ZOOLOGICO_REQUEST)
        },
        onEdit = { zoologico ->
            val intent = Intent(this, EditarZoologicoActivity::class.java)
            intent.putExtra("idZoologico", zoologico.id)
            startActivityForResult(intent, EDITAR_ZOOLOGICO_REQUEST)
        },
        onDelete = { id ->
            // Eliminar el zoológico de la base de datos y actualizar la lista
            zoologicoRepository.deleteZoologico(id)
            loadZoologicos()
        }
        )
    }

    private fun loadZoologicos() {
        zoologicos = zoologicoRepository.getAllZoologicos().toMutableList()
        adapter = ZoologicosAdapter(zoologicos, this::onZoologicoSelected, this::onEditZoologico, this::onDeleteZoologico)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun onZoologicoSelected(zoologico: Zoologico) {
        val intent = Intent(this, AnimalesActivity::class.java)
        intent.putExtra("idZoologico", zoologico.id)
        startActivityForResult(intent, EDITAR_ANIMAL_REQUEST)
    }

    private fun onEditZoologico(zoologico: Zoologico) {
        val intent = Intent(this, EditarZoologicoActivity::class.java)
        intent.putExtra("idZoologico", zoologico.id)
        startActivityForResult(intent, EDITAR_ZOOLOGICO_REQUEST)
    }

    private fun onDeleteZoologico(id: Int) {
        zoologicoRepository.deleteZoologico(id)
        zoologicos.removeAll { it.id == id }
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == EDITAR_ZOOLOGICO_REQUEST || requestCode == AGREGAR_ZOOLOGICO_REQUEST) && resultCode == RESULT_OK) {
            loadZoologicos()
        }
    }

    fun irActividad(clase: Class<*>){
        startActivity(Intent(this, clase))
    }

    companion object {
        const val EDITAR_ZOOLOGICO_REQUEST = 1
        const val AGREGAR_ZOOLOGICO_REQUEST = 2
    }
}
