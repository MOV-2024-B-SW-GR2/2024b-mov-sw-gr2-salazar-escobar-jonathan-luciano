package com.example.myapplication1


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.adapters.AnimalesAdapter
import com.example.myapplication1.models.Animal

class AnimalesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregarAnimal: Button
    private val animales = mutableListOf<Animal>()  // Lista de animales del zoológico
    private var zoologicoId: Int = 0  // ID del zoológico al que pertenecen los animales

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animales)

        recyclerView = findViewById(R.id.recyclerViewAnimales)
        btnAgregarAnimal = findViewById(R.id.btnAgregarAnimal)

        // Obtener el ID del zoológico desde el intent
        zoologicoId = intent.getIntExtra("idZoologico", 0)

        // Cargar los vehículos del concesionario
        cargarAnimales()

        // Configuración del adapter para el RecyclerView
        val adapter = AnimalesAdapter(
            animales,
            onEdit = { animal ->
                // Lógica para editar el animal
                val intent = Intent(this, EditarAnimalActivity::class.java)
                intent.putExtra("idAnimal", animal.id)
                startActivityForResult(intent, EDITAR_ANIMAL_REQUEST)
            },
            onDelete = { id ->
                // Eliminar animal y actualizar la lista
                animales.removeAll { it.id == id }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        )

        // Configurar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Manejo de la acción de agregar un vehículo
        btnAgregarAnimal.setOnClickListener {
            val intent = Intent(this, AgregarAnimalActivity::class.java)
            intent.putExtra("idZoologico", zoologicoId)
            startActivityForResult(intent, AGREGAR_ANIMAL_REQUEST)
        }
    }

    // Este metodo es necesario para recibir el resultado de la actividad de agregar/editar animal
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AGREGAR_ANIMAL_REQUEST && resultCode == RESULT_OK) {
            // Si la actividad de agregar animal se completó correctamente, actualizar la lista
            cargarAnimales()
            recyclerView.adapter?.notifyDataSetChanged()
        } else if (requestCode == EDITAR_ANIMAL_REQUEST && resultCode == RESULT_OK) {
            // Si la actividad de editar animal se completó correctamente, actualizar la lista
            cargarAnimales()
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    // Este metodo carga los animales del zoológico seleccionado
    private fun cargarAnimales() {
        // Aquí cargamos la lista de animales del zoológico específico
        val zoologico = BBaseDatosMemoria.zoologicos.find { it.id == zoologicoId }
        animales.clear()
        zoologico?.animals?.let {
            animales.addAll(it)
        }
    }

    companion object {
        const val AGREGAR_ANIMAL_REQUEST = 1
        const val EDITAR_ANIMAL_REQUEST = 2
    }
}