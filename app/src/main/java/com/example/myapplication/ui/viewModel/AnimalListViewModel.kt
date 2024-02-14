package com.example.myapplication.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto.modelo.Animal
import com.example.proyecto.controllers.AnimalController

class AnimalListViewModel : ViewModel() {
    private val _animals = MutableLiveData<List<Animal>>()
    val animals: LiveData<List<Animal>> = _animals

    init {
        _animals.value = AnimalController.getAnimales()
    }

    fun addAnimal(animal: Animal) {
        val currentList = _animals.value.orEmpty().toMutableList()
        currentList.add(animal)
        _animals.value = currentList
    }

    fun deleteAnimal(position: Int) {
        val currentList = _animals.value.orEmpty().toMutableList()
        currentList.removeAt(position)
        _animals.value = currentList
    }

    fun updateAnimal(position: Int, updatedAnimal: Animal) {
        val currentList = _animals.value.orEmpty().toMutableList()
        currentList[position] = updatedAnimal
        _animals.value = currentList
    }
}
