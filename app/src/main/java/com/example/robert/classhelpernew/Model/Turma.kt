package com.example.robert.classhelpernew.Model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "Turma")
data class Turma (
        @ColumnInfo(name = "Nome")
        @NotNull
        var Nome: String,
        @ColumnInfo(name = "DiasLetivos")
        @NotNull
        var DiasLetivos: String,
        @ColumnInfo(name = "Horario")
        @NotNull
        var Horario: String,
        @ColumnInfo(name = "Alunos")
        @NotNull
        var Alunos: String
): Serializable {
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0
}
