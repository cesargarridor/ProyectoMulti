package com.example.myapplication.ui.view

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentAnimalListBinding
import com.example.myapplication.ui.viewModel.AnimalListViewModel
import com.example.proyecto.adapter.AnimalAdapter
import com.example.proyecto.modelo.Animal

class AnimalListFragment : Fragment() {

    private lateinit var binding: FragmentAnimalListBinding
    private lateinit var adapter: AnimalAdapter
    private val viewModel: AnimalListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        adapter = AnimalAdapter(::onBorrarAnimal, ::onEditarAnimal)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.animals.observe(viewLifecycleOwner) { animals ->
            adapter.submitList(animals)
        }

        binding.btnAgregar.setOnClickListener {
            onAgregarAnimal()
        }
    }

    private fun onAgregarAnimal() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Agregar Animal")

        val inputLayout = LinearLayout(requireContext())
        inputLayout.orientation = LinearLayout.VERTICAL

        val inputNombre = EditText(requireContext())
        inputNombre.hint = "Nombre"
        inputLayout.addView(inputNombre)

        val inputEdad = EditText(requireContext())
        inputEdad.hint = "Edad"
        inputEdad.inputType = InputType.TYPE_CLASS_NUMBER
        inputLayout.addView(inputEdad)

        builder.setView(inputLayout)

        builder.setPositiveButton("Añadir") { _, _ ->
            val nuevoNombre = inputNombre.text.toString()
            val nuevaEdad = inputEdad.text.toString().toIntOrNull()

            if (nuevoNombre.isNotEmpty() && nuevaEdad != null) {
                viewModel.addAnimal(Animal(0, nuevoNombre, nuevaEdad))
            } else {
                Toast.makeText(requireContext(), "Datos no válidos, añada datos válidos", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun onBorrarAnimal(animal: Animal) {
        viewModel.deleteAnimal(animal.id)
    }

    private fun onEditarAnimal(animal: Animal) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Editar Animal")

        val inputLayout = LinearLayout(requireContext())
        inputLayout.orientation = LinearLayout.VERTICAL

        val inputNombre = EditText(requireContext())
        inputNombre.hint = "Nombre"
        inputNombre.setText(animal.nombre)
        inputLayout.addView(inputNombre)

        val inputEdad = EditText(requireContext())
        inputEdad.hint = "Edad"
        inputEdad.inputType = InputType.TYPE_CLASS_NUMBER
        inputEdad.setText(animal.edad.toString())
        inputLayout.addView(inputEdad)

        builder.setView(inputLayout)

        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoNombre = inputNombre.text.toString()
            val nuevaEdad = inputEdad.text.toString().toIntOrNull()

            if (nuevoNombre.isNotEmpty() && nuevaEdad != null) {
                val nuevoAnimal = Animal(animal.id, nuevoNombre, nuevaEdad)
                viewModel.updateAnimal(animal.id, nuevoAnimal)
            } else {
                Toast.makeText(requireContext(), "Ingrese datos válidos", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }



}

