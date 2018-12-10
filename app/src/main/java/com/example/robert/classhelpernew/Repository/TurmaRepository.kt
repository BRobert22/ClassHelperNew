package com.example.robert.classhelpernew.Repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.robert.classhelpernew.Dao.TurmaDao
import com.example.robert.classhelpernew.Model.Turma

class TurmaRepository(private val turmaDao: TurmaDao) {

    val allTurmas: LiveData<List<Turma>> = turmaDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(turma: Turma) {
        turmaDao.insert(turma)
    }

    fun delete(turma: Turma) {
        turmaDao.delete(turma)
    }

    fun update(turma: Turma) {
        turmaDao.update(turma)
    }

}