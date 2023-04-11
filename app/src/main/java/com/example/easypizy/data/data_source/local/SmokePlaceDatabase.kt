package com.example.easypizy.data.data_source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.easypizy.data.data_source.local.dao.SmokePlaceDao
import com.example.easypizy.data.data_source.local.entity.SmokePlaceEntity

@Database(entities = [SmokePlaceEntity::class], version = 1, exportSchema = false)
abstract class SmokePlaceDatabase : RoomDatabase() {

    abstract fun smokePlaceDao(): SmokePlaceDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SmokePlaceDatabase? = null

        fun getDatabase(context: Context): SmokePlaceDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmokePlaceDatabase::class.java,
                    "smoke_place_table"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}