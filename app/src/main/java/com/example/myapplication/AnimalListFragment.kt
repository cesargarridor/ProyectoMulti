package com.example.proyecto

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentAnimalListBinding
import com.example.proyecto.Adapters.AnimalAdapter
import com.example.proyecto.controllers.AnimalController
import com.example.proyecto.modelo.Animal

class AnimalListFragment : Fragment() {

    private lateinit var binding: FragmentAnimalListBinding
    private lateinit var animalController: AnimalController
    private lateinit var adapter: AnimalAdapter

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
        val agregarButton = binding.btnAgregar

        animalController = AnimalController()
        adapter = AnimalAdapter(AnimalController.getAnimales(), ::onBorrarAnimal, ::onEditarAnimal)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter



        adapter.notifyDataSetChanged()

        agregarButton.setOnClickListener {
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
                val nuevoAnimal = Animal(AnimalController.getAnimales().size + 1, nuevoNombre, nuevaEdad)
                AnimalController.agregarAnimal(nuevoAnimal)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Datos no válidos, añada datos válidos", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun onBorrarAnimal(posicion: Int) {
        AnimalController.borrarAnimal(posicion)
        adapter.notifyDataSetChanged()
    }

    private fun onEditarAnimal(posicion: Int) {
        val animal = AnimalController.obtenerAnimal(posicion)

        if (animal != null) {
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
                    AnimalController.actualizarAnimal(posicion, nuevoAnimal)
                    adapter.notifyDataSetChanged()
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
}
