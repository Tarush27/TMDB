package com.example.tmdb.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoviesAndTv::class], version = 1, exportSchema = false)
abstract class MovieDb : RoomDatabase() {

    abstract fun roomDao(): RoomDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDb? = null

        fun getDatabase(context: Context): MovieDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDb::class.java,
                    "movieDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}