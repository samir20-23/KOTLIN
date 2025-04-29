package com.example.efmobile2.ViewModel

import androidx.lifecycle.ViewModel
import com.example.efmobile2.model.Formation
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue

class FormationViewModel : ViewModel() {
    private val _formations = mutableStateListOf<Formation>()
    val formation : List<Formation> = _formations

    fun addFormation(formation: Formation){
        _formations.add(formation)
    }   
    
    fun deleteFormation(id: String){
        _formations.removeIf {it.id == id}
    }
    fun updateFormation(updateFormation: Formation){

    }
  fun updateFormation (updateFormation : Formation){
    val index = _formations.indexOfFirst {it.id == updateFormation.id}
    if(index != -1){
        _formations[index] = updateFormation 
    }
  }
    fun updateFormation(updateFormation: Formation){
        val index = _formations.indexOfFirst { it.id == updateFormation.id }
        if (index != -1) {
            _formations[index] = updateFormation
        }
    }
}