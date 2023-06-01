package com.example.proyecto_final_oty_app.fragments

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.adapters.AdapterEquipo
import com.example.proyecto_final_oty_app.entities.Equipo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ListEquipo : Fragment() {

    lateinit var v : View
    lateinit var recyclerEquipos : RecyclerView
    lateinit var adapter : AdapterEquipo
    lateinit var equipos : MutableList<Equipo>
    lateinit var txtTitle : TextView
    lateinit var btnAlta : Button
    lateinit var viewModel: ListEquipoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_equipo, container, false)
        recyclerEquipos = v.findViewById(R.id.ListaEquipos)
        txtTitle = v.findViewById(R.id.titleListaEquipos)
        btnAlta = v.findViewById(R.id.bottonAregarEquipos)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListEquipoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        equipos = mutableListOf()
        adapter = AdapterEquipo(equipos){
                position -> val action = ListEquipoDirections.actionListEquipoToDetalleEquipo(equipos[position])
                findNavController().navigate(action)
        }

        recyclerEquipos.layoutManager = LinearLayoutManager(context)

        viewModel.mostrarColeccion(equipos,recyclerEquipos,adapter)

        btnAlta.setOnClickListener(){
            val action = ListEquipoDirections.actionListEquipoToNewEquipo()
            findNavController().navigate(action)
        }


    }

}





