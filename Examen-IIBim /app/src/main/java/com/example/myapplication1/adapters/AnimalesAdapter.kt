package com.example.myapplication1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.models.Animal

class AnimalesAdapter(
    private val animales: MutableList<Animal>,
    private val onEdit: (Animal) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<AnimalesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreAnimal)
        val tvEspecie: TextView = view.findViewById(R.id.tvEspecieAnimal)
        val btnEditar: Button = view.findViewById(R.id.btnEditarAnimal)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminarAnimal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = animales[position]
        holder.tvNombre.text = animal.name
        holder.tvEspecie.text = animal.specie
        holder.btnEditar.setOnClickListener { onEdit(animal) }
        holder.btnEliminar.setOnClickListener { onDelete(animal.id) }
    }

    override fun getItemCount(): Int = animales.size
}
