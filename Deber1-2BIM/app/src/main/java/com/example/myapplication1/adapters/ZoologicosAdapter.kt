package com.example.myapplication1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.models.Zoologico

class ZoologicosAdapter(
    private val zoologicos: MutableList<Zoologico>,
    private val onClick: (Zoologico) -> Unit,
    private val onEdit: (Zoologico) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ZoologicosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreZoologico)
        val tvFechaInicio: TextView = view.findViewById(R.id.tvFechaInicioZoologico)
        //val tvEstado: TextView = view.findViewById(R.id.tvEstadoZoologico)
        val btnVer: Button = view.findViewById(R.id.btnVer)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_zoologico, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val zoologico = zoologicos[position]
        holder.tvNombre.text = zoologico.name
        holder.tvFechaInicio.text = zoologico.startDate
        //holder.tvEstado.text = if (zoologico.isOpen) "Abierto" else "Cerrado"

        holder.btnVer.setOnClickListener { onClick(zoologico) }
        holder.btnEditar.setOnClickListener { onEdit(zoologico) }
        holder.btnEliminar.setOnClickListener { onDelete(zoologico.id) }
    }

    override fun getItemCount(): Int = zoologicos.size
}
