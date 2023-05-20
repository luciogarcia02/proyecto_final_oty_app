package com.example.proyecto_final_oty_app.fragments

import androidx.lifecycle.ViewModel
import com.example.proyecto_final_oty_app.entities.EquipoABM
import com.example.proyecto_final_oty_app.entities.PersonalABM
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DetalleEquipoViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val equiposCollection = firestore.collection("equipos")


    suspend fun eliminarEquipo(idEquipo: String) = withContext(Dispatchers.IO) {
        val equipoDocument = equiposCollection.document(idEquipo)
        equipoDocument.delete().await()
    }

}