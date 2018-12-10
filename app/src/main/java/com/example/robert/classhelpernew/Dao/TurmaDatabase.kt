package com.example.robert.classhelpernew.Dao

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.robert.classhelpernew.Model.Turma
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Turma::class], version = 6)
abstract class TurmaDatabase : RoomDatabase() {

    abstract fun turmaDao(): TurmaDao

    companion object {
        @Volatile
        private var INSTANCE: TurmaDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TurmaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TurmaDatabase::class.java,
                    "turma-database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(TurmaDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class TurmaDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    //populateDatabase(database.turmaDao())
                }
            }
        }

        fun populateDatabase(turmaDao: TurmaDao) {
            turmaDao.deleteAll()

//            turmaDao.insert(Turma("Turma 2", "Segunda a Sexta", "08:00 as 10:00"))
//            turmaDao.insert(Turma("Turma 1", "Segunda a Sexta", "10:00 as 12:00"))
        }
    }
}