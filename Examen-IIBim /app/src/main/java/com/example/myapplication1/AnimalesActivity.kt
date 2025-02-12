package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.adapters.AnimalesAdapter
import com.example.myapplication1.models.Animal
import com.example.myapplication1.repository.AnimalRepository
import com.example.myapplication1.data.DBHelper

class AnimalesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarAnimal: Button
    private lateinit var animalRepository: AnimalRepository
    private val animales = mutableListOf<Animal>()  // Lista de animales del zoológico
    private var zoologicoId: Int = 0  // ID del zoológico al que pertenecen los animales

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animales)

        animalRepository = AnimalRepository(DBHelper(this))

        recyclerView = findViewById(R.id.recyclerViewAnimales)
        btnAgregarAnimal = findViewById(R.id.btnAgregarAnimal)

        // Obtener el ID del zoológico desde el intent
        zoologicoId = intent.getIntExtra("idZoologico", 0)

        // Cargar los animales desde la base de datos
        cargarAnimales()

        // Configuración del adapter para el RecyclerView
        val adapter = AnimalesAdapter(
            animales,
            onEdit = { animal ->
                val intent = Intent(this, EditarAnimalActivity::class.java)
                intent.putExtra("idAnimal", animal.id)
                startActivityForResult(intent, EDITAR_ANIMAL_REQUEST)
            },
            onDelete = { id ->
                // Eliminar el animal de la base de datos y actualizar la lista
                animalRepository.deleteAnimal(id)
                cargarAnimales()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAgregarAnimal.setOnClickListener {
            val intent = Intent(this, AgregarAnimalActivity::class.java)
            intent.putExtra("idZoologico", zoologicoId)
            startActivityForResult(intent, AGREGAR_ANIMAL_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == AGREGAR_ANIMAL_REQUEST || requestCode == EDITAR_ANIMAL_REQUEST) && resultCode == RESULT_OK) {
            cargarAnimales()
        }
    }

    private fun cargarAnimales() {
        animales.clear()
        animales.addAll(animalRepository.getAnimalesPorZoologico(zoologicoId))
        recyclerView.adapter?.notifyDataSetChanged()
    }

    companion object {
        const val AGREGAR_ANIMAL_REQUEST = 1
        const val EDITAR_ANIMAL_REQUEST = 2
    }
}
