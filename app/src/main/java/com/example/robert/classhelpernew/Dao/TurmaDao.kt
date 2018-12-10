package com.example.robert.classhelpernew.Dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.robert.classhelpernew.Model.Turma

@Dao
interface TurmaDao {

    @Insert
    fun insert(turma: Turma)

    @Query("DELETE FROM Turma")
    fun deleteAll()

    @Delete
    fun delete(turma: Turma)

    @Query("SELECT * from Turma ORDER BY Nome ASC")
    fun getAll(): LiveData<List<Turma>>

    @Update
    fun update(turma: Turma)

}