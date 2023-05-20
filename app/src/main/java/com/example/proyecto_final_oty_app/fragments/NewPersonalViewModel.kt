package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.PersonalABM
import kotlinx.coroutines.launch

class NewPersonalViewModel : ViewModel() {
    private val personalABM = PersonalABM()

    suspend fun agregarPersonal(personal: Personal): String? {
        return personalABM.agregarPersonal(personal)
    }

    suspend fun existeDni(dni: String): Boolean {
        return personalABM.existeDni(dni)
    }

}