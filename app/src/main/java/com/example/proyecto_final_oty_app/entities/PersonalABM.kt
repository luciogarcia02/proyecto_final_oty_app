package com.example.proyecto_final_oty_app.entities

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await

class PersonalABM {
    private val firestore = FirebaseFirestore.getInstance()
    private val personalCollection = firestore.collection("personal")

    suspend fun agregarPersonal(personal: Personal): String? {
        val newDocumentRef = personalCollection.document()

        val personalWithId = personal.copy(id = newDocumentRef.id)

        newDocumentRef.set(personalWithId).await()

        return newDocumentRef.id
    }

    suspend fun existeDni(dni: String): Boolean {
        val querySnapshot = personalCollection.whereEqualTo("dni", dni).get().await()
        return !querySnapshot.isEmpty
    }

    suspend fun editarPersonal(personalId: String, personal: Personal) {
        personalCollection.document(personalId).set(personal).await()
    }

    suspend fun eliminarPersonal(personalId: String) {
        personalCollection.document(personalId).delete().await()
    }

    suspend fun listarPersonal(): List<Personal> {
        val snapshot: QuerySnapshot = personalCollection.get().await()
        return snapshot.toObjects(Personal::class.java)
    }

    suspend fun obtenerPersonal(personalId: String): Personal? {
        val documentSnapshot: DocumentSnapshot = personalCollection.document(personalId).get().await()
        return if (documentSnapshot.exists()) {
            documentSnapshot.toObject(Personal::class.java)?.copy(id = documentSnapshot.id)
        } else {
            null
        }
    }

}
