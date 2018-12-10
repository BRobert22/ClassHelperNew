package com.example.robert.classhelpernew

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Build
import com.example.robert.classhelpernew.Adapter.TurmaAdapter
import com.example.robert.classhelpernew.Model.Turma
import com.example.robert.classhelpernew.ViewModel.TurmaViewModel

class MainActivity : AppCompatActivity() {
    private val channelId = "rober.com.ClassHelper"
    private lateinit var turmaViewModel: TurmaViewModel
    private val newActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Defini ação do botão flutuande de adicionar
        fbtnAdd
            .setOnClickListener {
                  val intent = Intent(this@MainActivity, TurmaFormActivity::class.java)
            intent.putExtra(TurmaFormActivity.EXTRA_REPLY , 0)
            startActivityForResult(intent, newActivityRequestCode)
        }

        val recyclerView = RecyclerView
        val adapter = TurmaAdapter(this)

        //Define click dos itens do recycleView
        adapter.onItemClick = {it ->
            val intent = Intent(this@MainActivity, TurmaFormActivity::class.java)
            intent.putExtra(TurmaFormActivity.EXTRA_REPLY , it)
            startActivityForResult(intent, newActivityRequestCode)
        }

        //Define o adapter no recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Chama o ViewModel para carregar os valores
        turmaViewModel = ViewModelProviders.of(this).get(TurmaViewModel::class.java)

        //Busca atualizações
        turmaViewModel.allTurmas.observe(this, Observer { turmas ->
            turmas?.let { adapter.setList(it) }
        })
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let { data ->
                try {
                    val turma: Turma?
                    turma = data.getSerializableExtra(TurmaFormActivity.EXTRA_REPLY) as Turma
                    turma.let {
                        //Verifica se é um novo registro
                        if(turma.id > 0){
                            turmaViewModel.update(turma)
                            sendNotification("atualizado")
                        }
                        else {
                            turmaViewModel.insert(turma)
                            sendNotification("cadastrado")
                        }
                    }
                } catch (e: Exception){
                    val turma: Turma?  = data.getSerializableExtra(TurmaFormActivity.EXTRA_DELETE) as Turma
                    turma.let {
                        turmaViewModel.delete(turma!!)
                        sendNotification("removido")
                    }
                }
            }
        }
    }

    //Envia as notificacoes de alteracoes nos cadastros
     fun sendNotification(strTitulo: String) {
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val mNotification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)
        } else {
            Notification.Builder(this)
        }.apply {
            setContentIntent(pendingIntent)
            setSmallIcon(R.drawable.notification_icon_background)
            setAutoCancel(true)
            setContentTitle(strTitulo)
            setContentText("O registro foi " + strTitulo + "!")
        }.build()
        val mNotificationId: Int = 1000
        val nManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nManager.notify(mNotificationId, mNotification)
    }
}
