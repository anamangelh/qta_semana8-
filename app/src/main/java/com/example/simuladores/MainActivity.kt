package com.example.simuladores

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var editTextValorPropiedad: EditText
    private lateinit var editTextCuantoNecesita: EditText
    private lateinit var editTextPlazo: EditText
    private lateinit var editTextTasa: EditText
    private lateinit var btnSimular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextValorPropiedad = findViewById(R.id.editTextValorPropiedad)
        editTextCuantoNecesita = findViewById(R.id.editTextCuantoNecesita)
        editTextPlazo = findViewById(R.id.editTextPlazo)
        editTextTasa = findViewById(R.id.editTextTasa)
        btnSimular = findViewById(R.id.btnSimular)

        btnSimular.setOnClickListener {
            val valorPropiedadStr = editTextValorPropiedad.text.toString().trim()
            val cuantoNecesitaStr = editTextCuantoNecesita.text.toString().trim()
            val plazoStr = editTextPlazo.text.toString().trim()
            val tasaStr = editTextTasa.text.toString().trim()

            if (TextUtils.isEmpty(valorPropiedadStr) || TextUtils.isEmpty(cuantoNecesitaStr) ||
                TextUtils.isEmpty(plazoStr) || TextUtils.isEmpty(tasaStr)) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val valorPropiedad = valorPropiedadStr.toDouble()
                val cuantoNecesita = cuantoNecesitaStr.toDouble()
                val plazo = plazoStr.toInt()
                val tasa = tasaStr.toDouble() / 100

                if (valorPropiedad < 70000000) {
                    Toast.makeText(this, "El valor de la propiedad debe ser al menos $70,000,000", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (cuantoNecesita <= 50000000 || cuantoNecesita > valorPropiedad * 0.7) {
                    Toast.makeText(this, "El préstamo debe ser mínimo de $50,000,000 y no puede exceder el 70% del valor de la propiedad", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (plazo !in 5..20) {
                    Toast.makeText(this, "El plazo debe estar entre 5 y 20 años", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (tasa !in 0.12..0.249) {
                    Toast.makeText(this, "La tasa de interés debe estar entre 12.0% y 24.9%", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Calcula la cuota mensual
                val numPagos = plazo * 12
                val tasaMensual = tasa / 12
                val cuota = (cuantoNecesita * tasaMensual * (1 + tasaMensual).pow(numPagos)) / ((1 + tasaMensual).pow(numPagos) - 1)

                Toast.makeText(this, "La cuota mensual es: ${"%.2f".format(cuota)}", Toast.LENGTH_LONG).show()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Por favor ingrese valores válidos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}