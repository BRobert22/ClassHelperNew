package com.example.robert.classhelpernew.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.robert.classhelpernew.Dao.TurmaDatabase
import com.example.robert.classhelpernew.Model.Turma
import com.example.robert.classhelpernew.Repository.TurmaRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class TurmaViewModel(application: Application): AndroidViewModel(application) {


    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: TurmaRepository
    val allTurmas: LiveData<List<Turma>>

    init {
        val turmaDao = TurmaDatabase.getDatabase(application, scope).turmaDao()
        repository = TurmaRepository(turmaDao)
        allTurmas = repository.allTurmas
    }

    fun insert(turma: Turma) = scope.launch(Dispatchers.IO) {
        repository.insert(turma)
    }

    fun update(turma: Turma) = scope.launch(Dispatchers.IO) {
        repository.update(turma)
    }

    fun delete(turma: Turma) = scope.launch(Dispatchers.IO) {
        repository.delete(turma)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}