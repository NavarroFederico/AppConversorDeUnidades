package com.example.appconversordeunidades

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.appconversordeunidades.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //adaptadores para el spinner
        val unidadesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.unidades,
            android.R.layout.simple_spinner_item
        )
        binding.spinnerUnidades.adapter = unidadesAdapter

        val longitudAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.longitud,
            android.R.layout.simple_spinner_item
        )
        val masaAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.masa,
            android.R.layout.simple_spinner_item
        )
        val tiempoAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.tiempo,
            android.R.layout.simple_spinner_item
        )

        binding.spinnerUnidades.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> setAdapter(longitudAdapter)
                        1 -> setAdapter(masaAdapter)
                        2 -> setAdapter(tiempoAdapter)
                    }
                }


                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        binding.btnConvertir.setOnClickListener {
            val tipoUnidad = binding.spinnerUnidades.selectedItemPosition

            val unidad1 = binding.spinnerValor1.selectedItemPosition
            val valor1 = binding.editTextValor1.text.toString().toFloat()

            val unidad2 = binding.spinnerValor2.selectedItemPosition
            val valor2: Float

            valor2 = when (tipoUnidad) {
                0 -> obtenerLongitud(unidad1, valor1, unidad2)
                1 -> obtenerMasa(unidad1, valor1, unidad2)
                2 -> obtenerTiempo(unidad1, valor1, unidad2)
                else -> {
                    0.0f
                }
            }
            binding.editTextValor2.setText(valor2.toString())
        }
    }


    private fun obtenerTiempo(unidad1: Int, valor1: Float, unidad2: Int): Float {
        val factor = when (unidad1) {
            //De Dia a...
            0 -> {
                when (unidad2) {
                    0 -> 1.0f //a Dia
                    1 -> 24.0f //a horas
                    2 -> 1_440.0f //a minuto
                    3 -> 86_400.0f // a segundo
                    else -> 0.0f
                }
            }
            //De Hora...
            1 -> when (unidad2) {
                0 -> 0.04f//a Dia
                1 -> 1.0f//a horas
                2 -> 60.0f//a minuto
                3 -> 3_600.0f// a segundo
                else -> 0.0f
            }
            //De Minuto..
            2 -> when (unidad2) {
                0 -> 0.000_694_444_4f//a Dia
                1 -> 0.01f//a horas
                2 -> 1.0f//a minuto
                3 -> 10f// a segundo
                else -> 0.0f
            }
            //De Segundo a...
            3 -> when (unidad2) {
                0 -> 0.001_001f//a Dia
                1 -> 0.001f//a horas
                2 -> 0.1f//a minuto
                3 -> 1.0f// a segundo
                else -> 0.0f
            }
            else -> 0.0f
        }
        return factor * valor1
    }

    private fun obtenerMasa(unidad1: Int, valor1: Float, unidad2: Int): Float {
        val factor = when (unidad1) {
            //De Kilogramo a...
            0 -> {
                when (unidad2) {
                    0 -> 1.0f //a kilogramo
                    1 -> 1_000.0f //a Gramo
                    2 -> 1_000_000.0f //a miligramo

                    else -> 0.0f
                }
            }
            //De Gramo a...
            1 -> {
                when (unidad2) {
                    0 -> 0.001f//a kilogramo
                    1 -> 1.0f//a Gramo
                    2 -> 1_000.0f//a miligramo
                    else -> 0.0f
                }
            }

            //De Miligramo a...
            2 -> {
                when (unidad2) {
                    0 -> 0.001_001f//a kilogramo
                    1 -> 0.001f//a Gramo
                    2 -> 1.0f//a miligramo

                    else -> 0.0f
                }
            }
            else -> {
                0.0f
            }
        }
        return factor * valor1
    }

    private fun obtenerLongitud(unidad1: Int, valor1: Float, unidad2: Int): Float {
        val factor = when (unidad1) {
            //De Kilometro a...
            0 -> {
                when (unidad2) {
                    0 -> 1.0f //a kilometro
                    1 -> 1_000.0f //a Metro
                    2 -> 100_000.0f //a centimetro
                    3 -> 1_000_000.0f // a milimetro
                    else -> 0.0f
                }
            }
            //De Metro a...
            1 -> when (unidad2) {
                0 -> 0.001f
                1 -> 1.0f
                2 -> 100.0f
                3 -> 1_000.0f
                else -> 0.0f
            }
            //De Centimetro...
            2 -> when (unidad2) {
                0 -> 0.001_01f
                1 -> 0.01f
                2 -> 1.0f
                3 -> 10f
                else -> 0.0f
            }
            //De Milimetro a...
            3 -> when (unidad2) {
                0 -> 0.001_001f
                1 -> 0.001f
                2 -> 0.1f
                3 -> 1.0f
                else -> 0.0f
            }
            else -> 0.0f
        }
        return factor * valor1
    }

    private fun setAdapter(adapter: ArrayAdapter<CharSequence>) {
        binding.spinnerValor1.adapter = adapter
        binding.spinnerValor2.adapter = adapter
    }
}