package com.example.robert.classhelpernew

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.robert.classhelpernew.Model.Turma
import kotlinx.android.synthetic.main.activity_turma_form.*
import android.view.inputmethod.InputMethodManager
import com.example.robert.classhelpernew.Adapter.AlunoAdapter

class TurmaFormActivity : AppCompatActivity() {

    lateinit var turma: Turma
    var menu: Menu? = null
    var alunoList: MutableList<String> = arrayListOf()

    private var aAdapter: AlunoAdapter? = null

    companion object {
        const val EXTRA_REPLY = "view.REPLY"
        const val EXTRA_DELETE = "view.Delete"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turma_form)

        btnAddAluno.setOnClickListener {
            if (txtAddAluno.text.isNotEmpty()) {
                if (alunoList.contains(txtAddAluno.text.toString())) {
                    Toast.makeText(applicationContext, "Aluno ja cadastrado", Toast.LENGTH_LONG).show()
                } else
                    AddAluno()
            } else {
                Toast.makeText(applicationContext, "Nome invalido", Toast.LENGTH_LONG).show()
            }
        }

        // botão de voltar ativo no menu superior esquerdo
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        try {
            turma = intent.getSerializableExtra(EXTRA_REPLY) as Turma
            turma.let {
                txtNome.setText(turma.Nome)
                txtHorario.setText(turma.Horario)

                if ("Segunda" in turma.DiasLetivos)
                    cbSegunda.isChecked = true
                if ("Terca" in turma.DiasLetivos)
                    cbTerca.isChecked = true
                if ("Quarta" in turma.DiasLetivos)
                    cbQuarta.isChecked = true
                if ("Quinta" in turma.DiasLetivos)
                    cbQuinta.isChecked = true
                if ("Sexta" in turma.DiasLetivos)
                    cbSexta.isChecked = true
                if ("Sabado" in turma.DiasLetivos)
                    cbSabado.isChecked = true
                if ("Domingo" in turma.DiasLetivos)
                    cbDomingo.isChecked = true

                if (turma.Alunos.isNotEmpty()) {
                    for (item in turma.Alunos.split(","))
                        alunoList.add(item)
                }
            }

            val menuItem = menu?.findItem(R.id.menu_excluir)
            menuItem?.isVisible = true
        } catch (exception: Exception) {

        }

        setAlunoRV()
    }

    fun setAlunoRV(){
        rvAluno.setAdapter(null);
        rvAluno.setLayoutManager(null);
        // Configurando o gerenciador de layout para ser uma lista.
        aAdapter = AlunoAdapter(this, alunoList)

        txtAddAluno.setText("")

        rvAluno.adapter = aAdapter
        rvAluno.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        try {
            turma.let {
                val menuItem = menu?.findItem(R.id.menu_excluir)
                menuItem?.isVisible = true
            }

        } catch (e: Exception) {

        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else if (item?.itemId == R.id.menu_salvar) {
            val replyIntent = Intent()

            var isOk = true

            if (txtNome.text.isNullOrEmpty()) {
                Toast.makeText(this, "Informe o campo nome", Toast.LENGTH_LONG).show()
                txtNome.requestFocus()
                isOk = false
            } else if ((::turma.isInitialized) && (turma.id > 0)) {
                turma.Horario = txtHorario.text.toString()
                turma.DiasLetivos = GetStrDias()
                turma.Alunos = GetStrAlunos()
            } else {
                turma = Turma(
                    Nome = txtNome.text.toString(),
                    Horario = txtHorario.text.toString(),
                    DiasLetivos = GetStrDias(),
                    Alunos = GetStrAlunos()
                )
            }
            if (isOk) {
                replyIntent.putExtra(EXTRA_REPLY, turma)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
                true
            } else
                false
        } else if (item?.itemId == R.id.menu_excluir) {
            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_DELETE, turma)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    fun AddAluno() {

        alunoList.add(txtAddAluno.text.trim().toString())

        Toast.makeText(this, "Aluno adicionado", Toast.LENGTH_LONG).show()

        rvAluno.requestFocus()

        setAlunoRV()

        fechaTeclado()
    }

    fun fechaTeclado() {
        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
    }

    fun GetStrDias(): String {

        var strDias = ""
        if (cbSegunda.isChecked)
            strDias = "Segunda"
        if (cbTerca.isChecked)
            strDias += if (strDias.isNotEmpty()) ",Terca" else "Terca"
        if (cbQuarta.isChecked)
            strDias += if (strDias.isNotEmpty()) ",Quarta" else "Quarta"
        if (cbQuinta.isChecked)
            strDias += if (strDias.isNotEmpty()) ",Quinta" else "Quinta"
        if (cbSexta.isChecked)
            strDias += if (strDias.isNotEmpty()) ",Sexta" else "Sexta"
        if (cbSabado.isChecked)
            strDias += if (strDias.isNotEmpty()) ",Sabado" else "Sabado"
        if (cbDomingo.isChecked)
            strDias += if (strDias.isNotEmpty()) ",Domingo" else "Domingo"

        return strDias;
    }

    fun GetStrAlunos(): String {

        var strAlunos = ""

        strAlunos = alunoList.joinToString()

        return strAlunos;
    }
}

