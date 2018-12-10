package com.example.robert.classhelpernew.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.robert.classhelpernew.Model.Turma
import com.example.robert.classhelpernew.R
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.main_line_view_aluno.view.*

class AlunoAdapter (private val context: Context, private var alunoList: MutableList<String>):
        RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_line_view_aluno, parent, false)
        return AlunoViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val nomeTurma: TextView = itemView.txtNome
//        val horarioTurma: TextView = itemView.txtHorario

    }

    override fun getItemCount() = alunoList.size

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        holder.bindView(alunoList[position])
    }

    class AlunoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewNome = itemView.txtNomeAluno

        fun bindView(aluno: String) {
            textViewNome.text = aluno
        }
    }
}