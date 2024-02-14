package com.example.proyecto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemAnimalBinding
import com.example.proyecto.modelo.Animal

class AnimalAdapter(
    private val onBorrarAnimal: (Animal) -> Unit,
    private val onEditarAnimal: (Animal) -> Unit
) : ListAdapter<Animal, AnimalAdapter.AnimalViewHolder>(AnimalDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = getItem(position)
        holder.bind(animal)
    }

    inner class AnimalViewHolder(private val binding: ItemAnimalBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: Animal) {
            binding.apply {
                txtEdad.text = animal.nombre
                txtNombre.text = animal.edad.toString()
                btnBorrar.setOnClickListener { onBorrarAnimal(animal) }
                btnEditar.setOnClickListener { onEditarAnimal(animal) }
            }
        }
    }

    private class AnimalDiffCallback : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }
}
