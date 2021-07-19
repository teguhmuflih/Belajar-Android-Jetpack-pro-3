package com.bangkit.bajp_3_film.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.bajp_3_film.data.local.entity.MovieEntity
import com.bangkit.bajp_3_film.data.local.entity.TVShowEntity

@Database(entities = [MovieEntity::class, TVShowEntity::class],
    version = 1,
    exportSchema = false)
abstract class DatabaseDAO: RoomDatabase() {
    abstract fun dataDao(): DataDAO

    companion object{

        @Volatile
        private var INSTANCE: DatabaseDAO? = null

        fun getInstance(context: Context): DatabaseDAO =
            INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseDAO::class.java,
                    "database.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}