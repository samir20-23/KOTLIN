
package com.example.efmobile3.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.efmobile3.model.Cours

class CoursViewModel : ViewModel() {
    private var nextId = 1
    private val _coursList = mutableStateListOf<Cours>()
    val coursList: List<Cours> get() = _coursList

    fun addCours(nom: String, statut: String, priorite: String) {
        val cours = Cours(
            id = nextId++,
            nom = nom,
            statut = statut,
            priorite = priorite
        )
        _coursList.add(cours)
    }

    fun updateCours(updated: Cours) {
        val idx = _coursList.indexOfFirst { it.id == updated.id }
        if (idx != -1) _coursList[idx] = updated
    }

    fun deleteCours(cours: Cours) {
        _coursList.remove(cours)
    }
}