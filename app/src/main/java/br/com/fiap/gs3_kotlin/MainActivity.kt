package br.com.fiap.gs3_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextNomePraia: EditText
    private lateinit var editTextCidade: EditText
    private lateinit var editTextEstado: EditText
    private lateinit var buttonIncluir: Button
    private lateinit var recyclerViewPraias: RecyclerView
    private lateinit var praiaAdapterActivity: PraiaAdapterActivity
    private val praiaActivities = mutableListOf<PraiaActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNomePraia = findViewById(R.id.etNomePraia)
        editTextCidade = findViewById(R.id.etCidade)
        editTextEstado = findViewById(R.id.etEstado)
        buttonIncluir = findViewById(R.id.btnIncluir)
        recyclerViewPraias = findViewById(R.id.recyclerViewPraias)

        praiaAdapterActivity = PraiaAdapterActivity(praiaActivities) { praia ->
            excluirPraia(praia)
        }

        recyclerViewPraias.adapter = praiaAdapterActivity
        recyclerViewPraias.layoutManager = LinearLayoutManager(this)

        buttonIncluir.setOnClickListener {
            incluirPraia()
        }

        val buttonVerIntegrantes: Button = findViewById(R.id.buttonVerIntegrantes)
        buttonVerIntegrantes.setOnClickListener {
            val intent = Intent(this, IntegrantesActivity::class.java)
            startActivity(intent)
        }

    }



    private fun incluirPraia() {
        val nomePraia = editTextNomePraia.text.toString().trim()
        val cidade = editTextCidade.text.toString().trim()
        val estado = editTextEstado.text.toString().trim()

        if (nomePraia.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val novaPraiaActivity = PraiaActivity(nomePraia, cidade, estado)
        praiaActivities.add(novaPraiaActivity)
        praiaAdapterActivity.notifyItemInserted(praiaActivities.size - 1)

        editTextNomePraia.text.clear()
        editTextCidade.text.clear()
        editTextEstado.text.clear()
    }

    private fun excluirPraia(praiaActivity: PraiaActivity) {
        val position = praiaActivities.indexOf(praiaActivity)
        if (position != -1) {
            praiaActivities.removeAt(position)
            praiaAdapterActivity.notifyItemRemoved(position)
        }
    }
}