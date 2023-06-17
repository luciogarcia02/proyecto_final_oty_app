package com.example.proyecto_final_oty_app.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.proyecto_final_oty_app.R
import com.example.proyecto_final_oty_app.entities.Personal
import com.example.proyecto_final_oty_app.entities.PersonalABM
import kotlinx.coroutines.launch

class NewPersonal : Fragment() {
    private lateinit var abm: PersonalABM
    private lateinit var view: View
    private val viewModel: NewPersonalViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_personal, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        abm = PersonalABM()

        val nombreEditText = view.findViewById<EditText>(R.id.nombreEditText)
        val apellidoEditText = view.findViewById<EditText>(R.id.apellidoEditText)
        val dniEditText = view.findViewById<EditText>(R.id.dniEditText)
        val areaEditText = view.findViewById<EditText>(R.id.areaEditText)
        val confirmarCargaButton: Button = view.findViewById(R.id.confirmarCargaButton)

        fun camposValidos(nombre: String, apellido: String, dni: String, area: String): Boolean {
            return nombre.isNotEmpty() && apellido.isNotEmpty() && dni.isNotEmpty() && area.isNotEmpty()
        }

        confirmarCargaButton.setOnClickListener {

            AlertDialog.Builder(context).setTitle("Confirmar nuevo personal")
                .setMessage("¿Desea confirmar el nuevo personal?")
                .setPositiveButton(android.R.string.ok) { _, _ ->

                    val nombre = nombreEditText.text.toString()
                    val apellido = apellidoEditText.text.toString()
                    val dni = dniEditText.text.toString()
                    val area = areaEditText.text.toString()

                    if (camposValidos(nombre, apellido, dni, area)) {
                        val nuevoPersonal = Personal(
                            id = "",
                            dni = dni,
                            nombre = nombre,
                            apellido = apellido,
                            area = area
                        )

                        lifecycleScope.launch {
                            if (!viewModel.existeDni(dni)) {
                                val id = viewModel.agregarPersonal(nuevoPersonal)
                                if (id != null) {
                                    nuevoPersonal.id = id  //SE MODIFICO ACA PARA QUE FUNCIONE
                                }
                                Toast.makeText(
                                    requireContext(),
                                    "Personal agregado correctamente.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                nombreEditText.text.clear()
                                apellidoEditText.text.clear()
                                dniEditText.text.clear()
                                areaEditText.text.clear()

                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Ya existe un registro con ese DNI.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Es obligatorio completar todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }

    }
}