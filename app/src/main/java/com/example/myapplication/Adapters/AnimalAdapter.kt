package com.example.proyecto.Adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.proyecto.modelo.Animal

class AnimalAdapter(
    private val animales: List<Animal>,
    private val onBorrarClick: (Int) -> Unit,
    private val onEditarClick: (Int) -> Unit
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animales[position]
        holder.bind(animal)
        holder.itemView.findViewById<Button>(R.id.btnBorrar).setOnClickListener {
            onBorrarClick(position)
        }
        holder.itemView.findViewById<Button>(R.id.btnEditar).setOnClickListener {
            onEditarClick(position)
        }
    }

    override fun getItemCount(): Int {
        return animales.size
    }

    class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(animal: Animal) {
            itemView.findViewById<TextView>(R.id.txtNombre).text = animal.nombre
            itemView.findViewById<TextView>(R.id.txtEdad).text = animal.edad.toString()
        }
    }
}

