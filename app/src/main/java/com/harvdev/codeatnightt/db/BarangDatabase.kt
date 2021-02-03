package com.harvdev.codeatnightt.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [BarangDb::class],
    version = 3
)

abstract class BarangDatabase : RoomDatabase(){

    abstract fun getBarangDao() : BarangDao

    companion object{

        @Volatile private var instance : BarangDatabase?= null
        private val LOCK =  Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BarangDatabase::class.java,
            "barangdatabase"
        ).build()

    }


}