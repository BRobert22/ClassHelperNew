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

class TurmaAdapter internal constructor(context: Context) :
        RecyclerView.Adapter<TurmaAdapter.ViewHolder>() {

    var onItemClick: ((Turma) -> Unit)? = null
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var turmas  = emptyList<Turma>() //

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeTurma: TextView = itemView.txtNome
        val alunosTurma: TextView = itemView.txtAlunos

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(turmas[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(holder: ViewGroup, position: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_list, holder, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = turmas[position]
        holder.nomeTurma.text = current.Nome
        holder.alunosTurma.text = if (current.Alunos.isNotEmpty()) (current.Alunos.split(',').count().toString() + " Alunos") else "0 Alunos"
    }

    override fun getItemCount() = turmas.size

    fun setList(frindList: List<Turma>){
        this.turmas = frindList
        notifyDataSetChanged()
    }

}