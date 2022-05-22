package com.zerdasoftware.thebooks.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zerdasoftware.thebooks.model.Books


@Database(entities = arrayOf(Books::class), version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDAO() : BookDAO

    //Singleton

    companion object {

        @Volatile private var instance : BookDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BookDatabase::class.java,"bookdatabase_2").build()
    }
}